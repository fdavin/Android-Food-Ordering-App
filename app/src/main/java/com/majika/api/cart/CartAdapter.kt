package com.majika.api.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.majika.R
import com.majika.ui.keranjang.CartViewModel
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartAdapter(
    private val CartItems: ArrayList<CartItem> = ArrayList(),
    private val viewModel: CartViewModel
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var keranjang: ArrayList<CartItem>
    val format: NumberFormat = NumberFormat.getCurrencyInstance()

    init {
        keranjang = CartItems
    }

    fun updateCart(Cart: ArrayList<CartItem>) {
        keranjang = Cart
        notifyDataSetChanged()
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CartItem) {
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("IDR")
            with(itemView) {
                val nameTextView: TextView = itemView.findViewById(R.id.tvCartItemName)
                nameTextView.text = item.name
                val priceTextView: TextView = itemView.findViewById(R.id.tvCartItemPrice)
                priceTextView.text = "${format.format(item.price)}"
                val quantityTextView: TextView = itemView.findViewById(R.id.tvCartItemQuantity)
                quantityTextView.text = item.quantity.toString()
                val addButton: Button = itemView.findViewById(R.id.btnIncreaseQuantity)
                val removeButton: Button = itemView.findViewById(R.id.btnDecreaseQuantity)

                addButton.setOnClickListener {
                    item.quantity++
                    viewModel.updateItem(item)
                }

                removeButton.setOnClickListener {
                    if (item.quantity > 1) {
                        item.quantity--
                        viewModel.updateItem(item)
                    } else {
                        viewModel.removeItem(item)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = keranjang[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return keranjang.size
    }

}
