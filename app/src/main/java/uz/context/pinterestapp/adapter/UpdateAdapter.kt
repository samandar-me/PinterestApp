package uz.context.pinterestapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView
import okhttp3.internal.notify
import uz.context.pinterestapp.R
import uz.context.pinterestapp.util.RandomColor
import uz.context.pinterestapp.util.GetDetailsInfo
import uz.context.pinterestapp.model.ResponseItem
import uz.context.pinterestapp.modelSearch.Result
import kotlin.collections.ArrayList


class UpdateAdapter(var context: Context, var items: ArrayList<ResponseItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.update_layout, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val home = items[position]

        if (holder is HomeViewHolder) {
            holder.apply {

                Glide.with(context)
                    .load(home.urls!!.small)
                    .placeholder(RandomColor.randomColor())
                    .into(imageView)

                if (home.description.isNullOrEmpty()) {
                    textView.text = context.getString(R.string.lorem)
                } else {
                    textView.text = home.description
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textUpdate)
        val imageView: ShapeableImageView = view.findViewById(R.id.imageUpdate)
    }
}