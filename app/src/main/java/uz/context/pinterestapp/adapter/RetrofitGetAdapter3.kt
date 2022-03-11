package uz.context.pinterestapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import uz.context.pinterestapp.R
import uz.context.pinterestapp.util.GetDetailsInfo
import uz.context.pinterestapp.modelSearch.Result

class RetrofitGetAdapter3(private val context: Context, private val lists: ArrayList<Result>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     lateinit var itemCLick: ((Result) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item2, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val list = lists[position]

        if (holder is MyViewHolder) {
            holder.apply {
                textView.text = list.description
                Glide.with(context)
                    .load(list.urls?.small)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.img)
                    .into(imageView)
            }
            holder.carView.setOnClickListener {
                GetDetailsInfo.id = list.id
                GetDetailsInfo.title = list.description.toString()
                GetDetailsInfo.links = list.urls!!.small
                itemCLick.invoke(list)
            }
        }
    }


    override fun getItemCount(): Int {
        return lists.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_images2)
        val textView: TextView = view.findViewById(R.id.title2)
        val carView: CardView = view.findViewById(R.id.card_view2)
    }
}