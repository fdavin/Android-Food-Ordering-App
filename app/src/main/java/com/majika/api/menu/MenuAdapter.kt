package com.majika.api.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.majika.R

class MenuAdapter(private val data: ArrayList<MenuData>): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
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
                    ${menuData.name}
                    Rp ${menuData.price}
                    $terjual Terjual
                    
                    ${menuData.description}
                """.trimIndent()

                val tvMenu = findViewById<TextView>(R.id.tvMenu)
                tvMenu.text = text
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