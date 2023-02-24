package com.majika.ui.keranjang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.majika.PembayaranActivity
import com.majika.R
import com.majika.api.cart.CartAdapter
import com.majika.api.cart.CartItem
import com.majika.databinding.FragmentKeranjangBinding


class KeranjangFragment : Fragment() {

    private var _binding: FragmentKeranjangBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    var list = ArrayList<CartItem>()

    private lateinit var model: CartViewModel
    private lateinit var adapter: CartAdapter

    /*private val viewModel: CartViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, CartViewModel.Factory(activity.application))
            .get(CartViewModel::class.java)
    }*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        val root: View = binding.root

        model = ViewModelProvider(this).get(CartViewModel::class.java)
        adapter = CartAdapter(list, model)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.rvKeranjang
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        model.keranjang.observe(viewLifecycleOwner, object : Observer<List<CartItem>> {
            override fun onChanged(t: List<CartItem>?) {
                val set = ArrayList<CartItem>()
                for (i in t?.indices!!) {
                    val add = CartItem(
                        t[i].name, t[i].price, t[i].quantity
                    )
                    set.add(add)
                }
                Log.d("INFO", t.toString())
                adapter.updateCart(set)

            }
        })
        recyclerView.adapter = adapter

        val bayarBtn: MaterialButton = requireView().findViewById(R.id.button_bayar)
        bayarBtn.setOnClickListener {
            val i = Intent(requireContext(), PembayaranActivity::class.java)
            startActivity(i)
        }
        val total: TextView = requireView().findViewById(R.id.Total) as TextView
        val totalObserver = Observer<Int> { newTotal ->
            // Update the UI, in this case, a TextView.
            if (newTotal == null) {
                total.text = "Total: Rp 0"
            } else {
                total.text = "Total: Rp ${newTotal}"
            }
        }
        model.total.observe(viewLifecycleOwner, totalObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        val toolbar: MaterialToolbar = requireActivity().findViewById(R.id.topAppBar)
        toolbar.title = "Keranjang"
        val temp: TextView = requireActivity().findViewById(R.id.temp)
        temp.visibility = TextView.GONE
    }
}