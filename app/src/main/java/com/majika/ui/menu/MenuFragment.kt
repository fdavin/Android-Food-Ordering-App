package com.majika.ui.menu

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.majika.R
import com.majika.api.RetrofitClient
import com.majika.api.cart.CartItem
import com.majika.api.menu.MenuData
import com.majika.api.menu.MenuResponse
import com.majika.api.menu.ParentData
import com.majika.api.menu.ParentMenuAdapter
import com.majika.databinding.FragmentMenuBinding
import com.majika.ui.keranjang.CartViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuFragment : Fragment(), SensorEventListener {
    private var _binding: FragmentMenuBinding? = null

    private lateinit var sensorManager: SensorManager
    private var tempSensor: Sensor? = null
    private var temperature: Float = Float.NaN

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val list = ArrayList<ParentData>()
    private lateinit var adapterMenu: ParentMenuAdapter

    var cartlist = ArrayList<CartItem>()
    private val viewModel: CartViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, CartViewModel.Factory(activity.application))
            .get(CartViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (tempSensor == null) {
            Toast.makeText(
                context, "Temperature sensor is not available",
                Toast.LENGTH_LONG
            ).show()
        } else {

        }
    }

    private fun loadAmbientTemperature() {
        sensorManager =
            activity?.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        } else {

        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val temp_text: TextView = requireActivity().findViewById(R.id.temp)
        if (sensorEvent.values.size > 0) {
            temperature = sensorEvent.values[0]
            temp_text.text = "${temperature}°C"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


    override fun onResume() {
        super.onResume()
        val toolbar: MaterialToolbar = requireActivity().findViewById(R.id.topAppBar)
        toolbar.title = "Menu"
        val temp_text: TextView = requireActivity().findViewById(R.id.temp)
        if (tempSensor != null) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL)
            temp_text.visibility = TextView.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        if (tempSensor != null) {
            sensorManager.unregisterListener(this, tempSensor)
            val temp_text: TextView = requireActivity().findViewById(R.id.temp)
            temp_text.visibility = TextView.INVISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchView: SearchView = binding.SearchBar
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                filter(msg, viewLifecycleOwner)
                return false
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*
        val main: MainActivity = MainActivity()
        val toolbar: Toolbar = main.findViewById(R.id.action_bar_layout) as Toolbar
        main.setActionBar(toolbar)*/

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize Category
        list.add(ParentData("Makanan", ArrayList<MenuData>()))
        list.add(ParentData("Minuman", ArrayList<MenuData>()))

        // Get API
        RetrofitClient.instance.getFoodMenu().enqueue(object : Callback<MenuResponse> {
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                list[0].items.clear()
                response.body()?.let { list[0].items.addAll(it.data) }

//                viewModel.keranjang.observe(viewLifecycleOwner, object : Observer<List<CartItem>> {
//                    override fun onChanged(t: List<CartItem>?) {
//                        var Set = ArrayList<CartItem>()
//                        for (i in t?.indices!!){
//                            val Add = CartItem(
//                                t[i].name, t[i].price, t[i].quantity
//                            )
//                            Set.add(Add)
//                        }
//                        Log.d("INFO", t.toString())
//                        adapterFood.updateCart(Set)
//                    }
//                })
            }

            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {}
        })

        RetrofitClient.instance.getDrinkMenu().enqueue(object : Callback<MenuResponse> {
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                list[1].items.clear()
                response.body()?.let { list[1].items.addAll(it.data) }
//                viewModel.keranjang.observe(viewLifecycleOwner, object : Observer<List<CartItem>> {
//                    override fun onChanged(t: List<CartItem>?) {
//                        var Set = ArrayList<CartItem>()
//                        for (i in t?.indices!!){
//                            val Add = CartItem(
//                                t[i].name, t[i].price, t[i].quantity
//                            )
//                            Set.add(Add)
//                        }
//                        Log.d("INFO", t.toString())
//                        adapterDrink.updateCart(Set)
//                    }
//                })
            }

            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {}
        })
        val rvParentMenu = binding.rvParentMenu
        rvParentMenu.setHasFixedSize(true)
        rvParentMenu.layoutManager = LinearLayoutManager(context)
        rvParentMenu.adapter =
            ParentMenuAdapter(list, viewModel, context, cartlist, viewLifecycleOwner)
        adapterMenu = rvParentMenu.adapter as ParentMenuAdapter
        viewModel.keranjang.observe(viewLifecycleOwner, object : Observer<List<CartItem>> {
            override fun onChanged(t: List<CartItem>?) {
                var Set = ArrayList<CartItem>()
                for (i in t?.indices!!) {
                    val Add = CartItem(
                        t[i].name, t[i].price, t[i].quantity
                    )
                    Set.add(Add)
                }
                Log.d("INFO", t.toString())
                adapterMenu.updateCart(Set)
            }
        })

        return root
    }

    private fun filter(text: String, viewLifeCyleOwner: LifecycleOwner) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<ParentData> = ArrayList()
        filteredlist.clear()
        filteredlist.add(ParentData("Makanan", ArrayList<MenuData>()))
        filteredlist.add(ParentData("Minuman", ArrayList<MenuData>()))

        // running a for loop to compare elements.
        for (item in list[0].items) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist[0].items.add(item)
            }
        }

        // running a for loop to compare elements.
        for (item in list[1].items) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist[1].items.add(item)
            }
        }
        val rvParentMenu = binding.rvParentMenu
        rvParentMenu.setHasFixedSize(true)
        rvParentMenu.layoutManager = LinearLayoutManager(context)
        rvParentMenu.adapter =
            ParentMenuAdapter(filteredlist, viewModel, context, cartlist, viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

