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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.google.android.material.imageview.ShapeableImageView
import uz.context.pinterestapp.R
import uz.context.pinterestapp.fragmentsall.DetailFragment
import uz.context.pinterestapp.model.GetDetailsInfo1
import uz.context.pinterestapp.model.ResponseItem
import java.util.*
import kotlin.collections.ArrayList


class RetrofitGetAdapter(var context: Context, var items:ArrayList<ResponseItem>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

            Glide.with(context)
                .load(home.urls?.thumb)
                .placeholder(randomColor())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_photo)

            tv_title.text = home.description


            cardView.setOnClickListener {
                GetDetailsInfo1.title = home.description.toString()
                GetDetailsInfo1.links = home.urls?.small.toString()
                itemCLick.invoke(home)
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("CutPasteId")
    class HomeViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tv_title: TextView = view.findViewById(R.id.tv_title)
        var iv_photo: ShapeableImageView
        var card_view: CardView

        init {
            iv_photo = view.findViewById(R.id.item_images1)
            card_view = view.findViewById(R.id.card_view1)
        }
    }

    private fun randomColor(): Int {
        val random = Random()
        val colorList = ArrayList<Int>()

        colorList.add(R.color.random1)
        colorList.add(R.color.random2)
        colorList.add(R.color.random3)
        colorList.add(R.color.random4)

        return colorList[random.nextInt(colorList.size)]
    }
}