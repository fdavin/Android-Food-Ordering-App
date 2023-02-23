package com.majika.api.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.majika.R
import com.majika.api.cart.CartItem
import com.majika.ui.keranjang.CartViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ParentMenuAdapter(private val data: ArrayList<ParentData>,private val viewModel: CartViewModel, private val parentViewContext: Context?, private val CartItems: ArrayList<CartItem> = ArrayList()) :
    RecyclerView.Adapter<ParentMenuAdapter.ParentMenuViewHolder>() {

    inner class ParentMenuViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        fun bind(parentMenuData: ParentData){
            with(itemView){
                val menuCategory = findViewById<TextView>(R.id.MenuCategory)
                val rvMenu = findViewById<RecyclerView>(R.id.rvMenu)
                menuCategory.text = parentMenuData.name
                rvMenu.layoutManager = LinearLayoutManager(rvMenu.context)
                rvMenu.adapter = MenuAdapter(parentMenuData.items, viewModel, CartItems)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ParentMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parent_menu, parent, false)
        return ParentMenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ParentMenuViewHolder, position: Int) {
        holder.bind(data[position])
    }
}