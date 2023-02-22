package com.majika

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.*
import com.google.zxing.BarcodeFormat
import com.majika.api.RetrofitClient
import com.majika.api.payment.PaymentResponse
import com.majika.ui.keranjang.CartViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PembayaranActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var model: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        model = ViewModelProvider(this).get(CartViewModel::class.java)
        codeScanner = CodeScanner(this, scannerView)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = listOf(BarcodeFormat.QR_CODE) // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                val statusView = findViewById<TextView>(R.id.status)
                RetrofitClient.instance.checkPayment(it.text).enqueue(object: Callback<PaymentResponse>{
                    override fun onResponse(
                        call: Call<PaymentResponse>,
                        response: Response<PaymentResponse>
                    ) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            statusView.text = responseBody.status
                        } else {
                            statusView.text = "Error"
                        }
                    }
                    override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                        statusView.text = "Error"
                    }
                })
//                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
        val total: TextView = findViewById<TextView>(R.id.total) as TextView
        val totalObserver = Observer<Int> { newTotal ->
            // Update the UI, in this case, a TextView.
            if (newTotal==null){
                total.text = "Total: Rp 0"
            } else {
                total.text = "Total: Rp ${newTotal}"
            }
        }
        model.total.observe(this,totalObserver)

        supportActionBar?.title = "Pembayaran"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}