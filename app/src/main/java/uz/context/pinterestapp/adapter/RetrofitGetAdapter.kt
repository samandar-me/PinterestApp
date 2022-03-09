package uz.context.pinterestapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent.getIntent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView
import uz.context.pinterestapp.R
import uz.context.pinterestapp.fragmentsall.DetailFragment
import uz.context.pinterestapp.model.GetDetailsInfo1
import uz.context.pinterestapp.model.ResponseItem


class RetrofitGetAdapter(var context: Context, var items:ArrayList<ResponseItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //adapterdan fragmentga intent qilish
//    lateinit var itemCLick: ((ResponseItem) -> Unit)
    lateinit var itemCLick: ((ResponseItem) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item,parent,false)
        return HomeViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val home = items[position]

        if (holder is HomeViewHolder) {
            val tv_title = holder.tv_title
            val iv_photo = holder.iv_photo
            val cardView = holder.card_view

            Glide.with(holder.itemView.context)
                .load(home.urls?.thumb)
//                .load(home.urls?.small)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_photo)

            tv_title.text = home.description


            cardView.setOnClickListener {
                //adapterdan fragmentga intent qilish
                GetDetailsInfo1.title = home.description.toString()
                GetDetailsInfo1.links = home.urls?.full.toString()
                itemCLick.invoke(home)
                Toast.makeText(context, "${home.description} ${home.urls?.thumb}", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("CutPasteId")
    class HomeViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tv_title: TextView
        var iv_photo: ShapeableImageView
        var card_view: CardView

        init {
            tv_title = view.findViewById(R.id.tv_title)
            iv_photo = view.findViewById(R.id.item_images1)
            card_view = view.findViewById(R.id.card_view1)
        }
    }
}