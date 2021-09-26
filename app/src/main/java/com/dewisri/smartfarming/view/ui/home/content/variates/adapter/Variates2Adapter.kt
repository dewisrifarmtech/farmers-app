package com.dewisri.smartfarming.view.ui.home.content.variates.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dewisri.smartfarming.R
import com.dewisri.smartfarming.data.source.local.model.Tanaman
import com.dewisri.smartfarming.view.ui.home.content.ContentCallBack
import com.firebase.ui.database.FirebaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_variates.view.*

class Variates2Adapter (
var variatesInfoList : MutableList<Tanaman>,
private val contentCallback: ContentCallBack
)
: FirebaseRecyclerAdapter<Variates2Adapter.VariatesViewHolder, Tanaman>() {

    inner class VariatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var view: View

        fun bind(tanaman: Tanaman) {
            with(itemView) {
                tv_variates_name.text = tanaman.name

                Glide.with(itemView.context)
                    .load(R.drawable.ic_komoditas2)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_error_white_24dp))
                    .error(R.drawable.ic_error_white_24dp)
                    .apply(RequestOptions().override(120, 80))
                    .placeholder(R.drawable.ic_baseline_replay_circle_filled_24_green)
                    .into(img_variates)
                itemView.setOnClickListener {
                    contentCallback.onItemClicked(tanaman.name)
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Tanaman {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: Tanaman, position: Int, model: VariatesViewHolder) {
        TODO("Not yet implemented")
    }
}