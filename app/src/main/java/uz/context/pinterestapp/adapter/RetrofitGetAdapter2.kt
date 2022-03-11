package uz.context.pinterestapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
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
import uz.context.pinterestapp.R
import uz.context.pinterestapp.util.GetDetailsInfo
import uz.context.pinterestapp.model.ResponseItem
import java.util.*


class RetrofitGetAdapter2(var context: Context, var items: ArrayList<ResponseItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var itemCLick: ((ResponseItem) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item2, parent, false)
        return HomeViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val home = items[position]

        if (holder is HomeViewHolder) {
            val tvTitle = holder.tvTitle
            val iv_photo = holder.iv_photo
            val cardView = holder.card_view

            Glide.with(context)
                .load(home.urls?.small)
                .placeholder(R.drawable.img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_photo)

            tvTitle.text = home.toString()
            cardView.setOnClickListener {
                GetDetailsInfo.links = home.urls?.small.toString()
                itemCLick.invoke(home)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("CutPasteId")
    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView = view.findViewById(R.id.title2)
        var iv_photo: ShapeableImageView = view.findViewById(R.id.item_images2)
        var card_view: CardView = view.findViewById(R.id.card_view2)

    }
}