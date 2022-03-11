package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import uz.context.pinterestapp.R
import uz.context.pinterestapp.util.GetDetailsInfo

class DetailFragment : Fragment() {
    lateinit var textView: TextView
    lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        initViews(view)
        return view
    }
    private fun initViews(view: View) {

        textView = view.findViewById(R.id.text_view)
        imageView = view.findViewById(R.id.image_view)

        if (GetDetailsInfo.title.isNullOrEmpty()) {
            textView.text = GetDetailsInfo.title
        } else {
            textView.text = "Picture"
        }

        Glide.with(view)
            .load(GetDetailsInfo.links)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}