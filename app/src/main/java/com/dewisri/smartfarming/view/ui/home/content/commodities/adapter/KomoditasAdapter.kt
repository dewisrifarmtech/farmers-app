package com.dewisri.smartfarming.view.ui.home.content.commodities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dewisri.smartfarming.R
import com.dewisri.smartfarming.data.model.Kebun
import kotlinx.android.synthetic.main.item_kebun.view.*

class KomoditasAdapter(private var listKebun: ArrayList<Kebun>)
    : RecyclerView.Adapter<KomoditasAdapter.CommoditiesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommoditiesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kebun, parent, false)
        return CommoditiesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommoditiesViewHolder, position: Int) {
        
        holder.bind(listKebun[position])

    }

    override fun getItemCount(): Int {
        return listKebun.size
    }

    inner class CommoditiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var view: View

        fun bind(kebun: Kebun) {
            with(itemView) {
                tv_kebun_name.text = kebun.name
                tv_location.text = kebun.location

                Glide.with(itemView.context)
                        .load(R.drawable.padi)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_error_white_24dp))
                        .error(R.drawable.ic_error_white_24dp)
                        .apply(RequestOptions().override(120, 80))
                        .placeholder(R.drawable.user)
                        .into(img_kebun)
                itemView.setOnClickListener {

                }


            }
        }
    }



}