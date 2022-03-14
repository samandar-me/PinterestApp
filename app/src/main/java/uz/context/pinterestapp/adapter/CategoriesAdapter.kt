package uz.context.pinterestapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import uz.context.pinterestapp.R
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.modelSearchFrag.CollectionsModel
import uz.context.pinterestapp.modelSearchFrag.CollectionsModelItem
import uz.context.pinterestapp.util.RandomColor

class CategoriesAdapter(private val context: Context, private val lists: ArrayList<CollectionsModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_collections_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val list = lists[position]

        if (holder is MyViewHolder) {
            holder.apply {
                Glide.with(context)
                    .load(list.links!!.photos)
                    .placeholder(RandomColor.randomColor())
                    .into(imageView)

                textView.text = list.title
            }
        }
    }


    override fun getItemCount(): Int {
        return lists.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ShapeableImageView = view.findViewById(R.id.collections_images)
        val textView: TextView = view.findViewById(R.id.collections_text)
    }
}