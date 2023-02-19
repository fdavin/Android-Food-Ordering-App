package com.majika.api.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.majika.R
import com.majika.ui.keranjang.CartViewModel

class CartAdapter(private val viewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems = emptyList<CartItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartItems[position]
        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = currentItem.price.toString()
        holder.quantityTextView.text = currentItem.quantity.toString()

        holder.addButton.setOnClickListener {
            currentItem.quantity++
            viewModel.addItem(currentItem)
        }

        holder.removeButton.setOnClickListener {
            if (currentItem.quantity > 1) {
                currentItem.quantity--
                viewModel.addItem(currentItem)
            } else {
                viewModel.removeItem(currentItem)
            }
        }
    }

    override fun getItemCount() = cartItems.size

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvCartItemName)
        val priceTextView: TextView = itemView.findViewById(R.id.tvCartItemPrice)
        val quantityTextView: TextView = itemView.findViewById(R.id.tvCartItemQuantity)
        val addButton: Button = itemView.findViewById(R.id.btnIncreaseQuantity)
        val removeButton: Button = itemView.findViewById(R.id.btnDecreaseQuantity)
    }
}
