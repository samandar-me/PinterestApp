package uz.context.pinterestapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.context.pinterestapp.R
import uz.context.pinterestapp.database.SaveImage
import uz.context.pinterestapp.util.RandomColor
import java.lang.Exception

class ImageSaveAdapter() :
    RecyclerView.Adapter<ImageSaveAdapter.SavedPhotoVH>() {

    private var photos: ArrayList<SaveImage> = ArrayList()
    lateinit var photoClick: ((SaveImage) -> Unit)

    inner class SavedPhotoVH(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(photo: SaveImage) {
            val imageView: ImageView = view.findViewById(R.id.item_images1)
            val textView: TextView = view.findViewById(R.id.tv_title)

            Glide.with(imageView)
                .load(photo.url)
                .placeholder(RandomColor.randomColor())
                .into(imageView)

            if (photo.title.isNotBlank()) {
                textView.text = photo.title
            }

            imageView.setOnClickListener {
                photoClick.invoke(photo)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPhotoVH {
        return SavedPhotoVH(
            LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SavedPhotoVH, position: Int) {
        holder.bind(getItem(position))

    }

    @SuppressLint("NotifyDataSetChanged")
    fun saveImage(list: List<SaveImage>) {
        photos.addAll(list)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): SaveImage = photos[position]

    override fun getItemCount(): Int = photos.size
}