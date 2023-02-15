package com.majika.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.majika.api.RetrofitClient
import com.majika.api.menu.MenuAdapter
import com.majika.api.menu.MenuData
import com.majika.api.menu.MenuResponse
import com.majika.databinding.FragmentMenuBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val list = ArrayList<MenuData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rvMenu = binding.rvMenu
        rvMenu.setHasFixedSize(true)
        rvMenu.layoutManager = LinearLayoutManager(context)

        // Get API
        RetrofitClient.instance.getMenu().enqueue(object: Callback<MenuResponse>{
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                response.body()?.let { list.addAll(it.data) }
                rvMenu.adapter = MenuAdapter(list)
            }

            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {

            }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}