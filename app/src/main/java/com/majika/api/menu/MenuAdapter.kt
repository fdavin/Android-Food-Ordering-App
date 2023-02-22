package com.majika.api.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.majika.R
import com.majika.api.cart.CartItem
import com.majika.ui.keranjang.CartViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MenuAdapter(private val data: ArrayList<MenuData>,private val viewModel: CartViewModel) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    inner class MenuViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        fun bind(menuData: MenuData){
            with(itemView){
                val terjual: String = if(menuData.sold > 10000){
                    "10RB+"
                }else if(menuData.sold >= 1000){
                    "${menuData.sold/1000}RB"
                }else{
                    "${menuData.sold}"
                }
                val text = """
                    Rp ${menuData.price}
                    $terjual Terjual
                    
                    ${menuData.description}
                """.trimIndent()
                if (menuData.type=="Food") {
                    val tvMenuName = findViewById<TextView>(R.id.tvMenuName)
                    tvMenuName.text = "${menuData.name}"
                    val tvMenu = findViewById<TextView>(R.id.tvMenu)
                    tvMenu.text = text
                    val btnAddToCart = itemView.findViewById<Button>(R.id.btnAddToCart)
                    btnAddToCart.setOnClickListener {
                        val cartItem = CartItem(menuData.name, menuData.price, 1)
                        viewModel.addItem(cartItem)
                        Toast.makeText(context, "${menuData.name} added to cart", Toast.LENGTH_SHORT).show()
                    }
                } else{
                    val tvMenuName = findViewById<TextView>(R.id.tvMinumName)
                    tvMenuName.text = "${menuData.name}"
                    val tvMenu = findViewById<TextView>(R.id.tvMinum)
                    tvMenu.text = text
                    val btnAddToCart = itemView.findViewById<Button>(R.id.btnAddToCart)
                    btnAddToCart.setOnClickListener {
                        var quantity = 0
                        GlobalScope.launch {
                            val itemcart =  viewModel.getItemByName("${menuData.name}")
                            if (itemcart != null) {
                                quantity = itemcart.quantity
                            }
                        }
                        if (quantity==0){
                            val cartItem = CartItem(menuData.name, menuData.price, 1)
                            viewModel.addItem(cartItem)
                            Toast.makeText(context, "${menuData.name} added to cart", Toast.LENGTH_SHORT).show()
                        } else {
                            val cartItem = CartItem(menuData.name, menuData.price, quantity+1)
                            viewModel.updateItem(cartItem)
                            Toast.makeText(context, "More ${menuData.name} added to cart", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
/*
    inner class MinumViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        fun bind(menuData: MenuData){
            with(itemView){
                val terjual: String = if(menuData.sold > 10000){
                    "10RB+"
                }else if(menuData.sold >= 1000){
                    "${menuData.sold/1000}RB"
                }else{
                    "${menuData.sold}"
                }
                val text = """
                    Rp ${menuData.price}
                    $terjual Terjual
                    
                    ${menuData.description}
                """.trimIndent()
                val tvMenuName = findViewById<TextView>(R.id.tvMinumName)
                tvMenuName.text = "${menuData.name}"
                val tvMenu = findViewById<TextView>(R.id.tvMinum)
                tvMenu.text = text
                val btnAddToCart = itemView.findViewById<Button>(R.id.btnAddToCart)
                btnAddToCart.setOnClickListener {
                    val cartItem = CartItem(menuData.name, menuData.price, 1)
                    viewModel.addItem(cartItem)
                }
            }
        }
    }
*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MenuViewHolder {
        if (viewType == 0){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
            return MenuViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_minum, parent, false)
            return MenuViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        if (data[position].type == "Food"){
            return 0
        } else {
            return 1
        }
    }
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(data[position])
    }
}