package com.majika.ui.keranjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.majika.api.cart.CartAdapter
import com.majika.databinding.FragmentKeranjangBinding

class KeranjangFragment : Fragment() {

    private var _binding: FragmentKeranjangBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var cartViewModel: CartViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rvKeranjang = binding.rvKeranjang
        rvKeranjang.layoutManager = LinearLayoutManager(context)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        // Set adapter to the RecyclerView
        rvKeranjang.adapter = CartAdapter(cartViewModel)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}