package uz.context.pinterestapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest.Networking.ResponseItem
import com.google.android.material.imageview.ShapeableImageView
import uz.context.pinterestapp.R

class RetrofitGetAdapter(var items:ArrayList<ResponseItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item,parent,false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val home = items[position]

        if (holder is HomeViewHolder) {
            val tv_title = holder.tv_title
            val iv_photo = holder.iv_photo

            tv_title.text = home.description
            Glide.with(holder.itemView.context)
                .load(home.urls?.thumb)
                .into(iv_photo)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("CutPasteId")
    class HomeViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tv_title: TextView
        var iv_photo: ShapeableImageView

        init {
            tv_title = view.findViewById(R.id.tv_title)
            iv_photo = view.findViewById(R.id.item_images1)
        }
    }
}