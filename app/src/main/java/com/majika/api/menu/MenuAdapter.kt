package com.majika.api.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.majika.R
import com.majika.api.cart.CartItem
import com.majika.ui.keranjang.CartViewModel

class MenuAdapter(private val data: ArrayList<MenuData>, private val viewModel: CartViewModel) :
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
                val tvMenuName = findViewById<TextView>(R.id.tvMenuName)
                tvMenuName.text = "${menuData.name}"
                val tvMenu = findViewById<TextView>(R.id.tvMenu)
                tvMenu.text = text
            }
            val btnAddToCart = itemView.findViewById<Button>(R.id.btnAddToCart)
            btnAddToCart.setOnClickListener {
                // Create a new CartItem object from the MenuData object
                val cartItem = CartItem(menuData.name, menuData.price, 1)
                // Call the addItem function in the CartRepository to add the item to the shopping cart
                // Note: You'll need to get the CartRepository instance in the activity/fragment and pass it to the MenuAdapter
                viewModel.addItem(cartItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(data[position])
    }


}