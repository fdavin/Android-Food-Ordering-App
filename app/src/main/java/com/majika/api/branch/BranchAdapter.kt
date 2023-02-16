package com.majika.api.branch

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.majika.R

class BranchAdapter (private val data: ArrayList<BranchData>): RecyclerView.Adapter<BranchAdapter.BranchViewHolder>(){
    inner class BranchViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        fun bind(branchData: BranchData){
            with(itemView){
                val text = "${branchData.name}\n\n"+
                        "${branchData.address}\n" +
                        branchData.phone_number
                val tvBranch = findViewById<TextView>(R.id.tvBranch)
                tvBranch.text = text

                val buttonBranch = findViewById<Button>(R.id.buttonBranch)
                buttonBranch.setOnClickListener{
                    val gmmIntentUri = Uri.parse("geo:${branchData.latitude},${branchData.longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(it.context,mapIntent, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_branch, parent, false)
        return BranchViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.bind(data[position])
    }
}