package com.majika.api.menu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.majika.R
import com.majika.api.cart.CartItem
import com.majika.ui.keranjang.CartViewModel

class ParentMenuAdapter(
    private val data: ArrayList<ParentData>,
    private val viewModel: CartViewModel,
    private val parentViewContext: Context?,
    private val CartItems: ArrayList<CartItem> = ArrayList(),
    private val viewLifeCyleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<ParentMenuAdapter.ParentMenuViewHolder>() {

    private var keranjang: ArrayList<CartItem>
    private lateinit var adapterMenu: MenuAdapter

    init {
        keranjang = CartItems
    }

    fun updateCart(Cart: ArrayList<CartItem>) {
        keranjang = Cart
        notifyDataSetChanged()
    }

    inner class ParentMenuViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        fun bind(parentMenuData: ParentData) {
            with(itemView) {
                val menuCategory = findViewById<TextView>(R.id.MenuCategory)
                val rvMenu = findViewById<RecyclerView>(R.id.rvMenu)
                menuCategory.text = parentMenuData.name
                rvMenu.layoutManager = LinearLayoutManager(rvMenu.context)
                rvMenu.adapter = MenuAdapter(parentMenuData.items, viewModel, CartItems)
                adapterMenu = rvMenu.adapter as MenuAdapter
                viewModel.keranjang.observe(viewLifeCyleOwner, object : Observer<List<CartItem>> {
                    override fun onChanged(t: List<CartItem>?) {
                        var Set = ArrayList<CartItem>()
                        for (i in t?.indices!!) {
                            val Add = CartItem(
                                t[i].name, t[i].price, t[i].quantity
                            )
                            Set.add(Add)
                        }
                        Log.d("INFO", t.toString())
                        adapterMenu.updateViewModel(Set)
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentMenuViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_parent_menu, parent, false)
        return ParentMenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ParentMenuViewHolder, position: Int) {
        holder.bind(data[position])
    }
}