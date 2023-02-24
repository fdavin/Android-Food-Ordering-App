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
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MenuAdapter(
    private val data: ArrayList<MenuData>,
    private val viewModel: CartViewModel,
    private val CartItems: ArrayList<CartItem> = ArrayList()
) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var keranjang: ArrayList<CartItem>
    val format: NumberFormat = NumberFormat.getCurrencyInstance()

    init {
        keranjang = CartItems
    }

    fun updateCart(CartItem: CartItem) {
        var inCart = false
        for (items in keranjang) {
            if (CartItem.name == items.name) {
                items.quantity = CartItem.quantity
                inCart = true
            }
        }
        if (!inCart) {
            keranjang.add(CartItem)
        }
    }

    fun updateViewModel(Cart: ArrayList<CartItem>) {
        keranjang = Cart
        notifyDataSetChanged()
    }

    fun getItemQuantity(Name: String): Int {
        var quantity = 0
        for (items in keranjang) {
            if (items.name == Name) {
                quantity = items.quantity
            }
        }
        return quantity
    }

    inner class MenuViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        fun bind(menuData: MenuData) {
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("IDR")
            with(itemView) {
                val terjual: String = if (menuData.sold > 10000) {
                    "10RB+"
                } else if (menuData.sold >= 1000) {
                    "${menuData.sold / 1000}RB+"
                } else {
                    "${menuData.sold}"
                }
                val text = """
                    ${format.format(menuData.price)}
                    $terjual Terjual
                    
                    ${menuData.description}
                """.trimIndent()
                val tvMenuName = findViewById<TextView>(R.id.tvMenuName)
                tvMenuName.text = "${menuData.name}"
                val tvMenu = findViewById<TextView>(R.id.tvMenu)
                tvMenu.text = text
                var quantity = 0
                quantity = getItemQuantity("${menuData.name}")
                val itemQuantity = findViewById<TextView>(R.id.tvQuantity)
                val btnDecrease = itemView.findViewById<Button>(R.id.btnKurang)
                itemQuantity.text = quantity.toString()
                if (quantity != 0 && itemQuantity.visibility != TextView.VISIBLE) {
                    itemQuantity.visibility = TextView.VISIBLE
                    btnDecrease.visibility = Button.VISIBLE
                }
                if (quantity == 0 && itemQuantity.visibility == TextView.VISIBLE) {
                    itemQuantity.visibility = TextView.INVISIBLE
                    btnDecrease.visibility = Button.INVISIBLE
                }
                val btnTambah = itemView.findViewById<Button>(R.id.btnTambah)
                btnTambah.setOnClickListener {
                    if (quantity == 0) {
                        val cartItem = CartItem(menuData.name, menuData.price, 1)
                        viewModel.addItem(cartItem)
                        updateCart(cartItem)
                        Toast.makeText(
                            context,
                            "${menuData.name} added to cart",
                            Toast.LENGTH_SHORT
                        ).show()
                        val itemQuantity = findViewById<TextView>(R.id.tvQuantity)
                        val btnDecrease = itemView.findViewById<Button>(R.id.btnKurang)
                        itemQuantity.visibility = TextView.VISIBLE
                        btnDecrease.visibility = Button.VISIBLE
                    } else {
                        val cartItem = CartItem(menuData.name, menuData.price, quantity + 1)
                        viewModel.updateItem(cartItem)
                        updateCart(cartItem)
                        Toast.makeText(
                            context,
                            "More ${menuData.name} added to cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                val btnReduce = itemView.findViewById<Button>(R.id.btnKurang)
                btnReduce.setOnClickListener {
                    if (quantity == 1) {
                        val cartItem = CartItem(menuData.name, menuData.price, quantity)
                        viewModel.removeItem(cartItem)
                        updateCart(CartItem(menuData.name, menuData.price, 0))
                        Toast.makeText(
                            context,
                            "${menuData.name} removed from cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val cartItem = CartItem(menuData.name, menuData.price, quantity - 1)
                        viewModel.updateItem(cartItem)
                        updateCart(cartItem)
                        Toast.makeText(
                            context,
                            "1 ${menuData.name} removed from cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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

    override fun getItemViewType(position: Int): Int {
        if (data[position].type == "Food") {
            return 0
        } else {
            return 1
        }
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(data[position])
    }
}