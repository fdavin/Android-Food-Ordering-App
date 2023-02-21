package com.majika.ui.menu

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.majika.R
import com.majika.api.RetrofitClient
import com.majika.api.cart.CartAdapter
import com.majika.api.menu.MenuAdapter
import com.majika.api.menu.MenuData
import com.majika.api.menu.MenuResponse
import com.majika.databinding.FragmentMenuBinding
import com.majika.ui.keranjang.CartViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuFragment : Fragment() {
    lateinit var rvMenu: RecyclerView
    lateinit var rvMinum: RecyclerView
    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val list = ArrayList<MenuData>()
    private val drinklist = ArrayList<MenuData>()
    private val viewModel: CartViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, CartViewModel.Factory(activity.application))
            .get(CartViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchView: SearchView = requireView().findViewById<SearchView>(R.id.SearchBar) as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return false
            }
        })
    }

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
        RetrofitClient.instance.getFoodMenu().enqueue(object: Callback<MenuResponse>{
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                response.body()?.let { list.addAll(it.data) }
                rvMenu.adapter = MenuAdapter(list,viewModel)
            }
            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
            }
        })
        val rvMinum = binding.rvMinum
        rvMinum.setHasFixedSize(true)
        rvMinum.layoutManager = LinearLayoutManager(context)
        RetrofitClient.instance.getDrinkMenu().enqueue(object: Callback<MenuResponse>{
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                response.body()?.let { drinklist.addAll(it.data) }
                rvMinum.adapter = MenuAdapter(drinklist,viewModel)
            }
            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
            }
        })

        return root
    }
    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<MenuData> = ArrayList()

        // running a for loop to compare elements.
        for (item in list) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        val filteredDrinklist: ArrayList<MenuData> = ArrayList()

        // running a for loop to compare elements.
        for (item in drinklist) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredDrinklist.add(item)
            }
        }
        val rvMenu = binding.rvMenu
        rvMenu.setHasFixedSize(true)
        rvMenu.layoutManager = LinearLayoutManager(context)
        rvMenu.adapter = MenuAdapter(filteredlist,viewModel)
        val rvMinum = binding.rvMinum
        rvMinum.setHasFixedSize(true)
        rvMinum.layoutManager = LinearLayoutManager(context)
        rvMinum.adapter = MenuAdapter(filteredDrinklist,viewModel)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

