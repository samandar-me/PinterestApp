package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.RetrofitGetAdapter
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.networking.RetrofitHttp
import uz.context.pinterestapp.util.GetDetailsInfo

class DetailFragment : Fragment() {
    lateinit var textView: TextView
    lateinit var imageView: ImageView
    private lateinit var detailsRecyclerView: RecyclerView
    private lateinit var retrofitGetAdapter: RetrofitGetAdapter
    private val photos = ArrayList<Result>()
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
        detailsRecyclerView = view.findViewById(R.id.detailRecycler)

        detailsRecyclerView.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        detailsRecyclerView.layoutManager = layoutManager

        if (GetDetailsInfo.title.isNullOrEmpty()) {
            textView.text = GetDetailsInfo.title
        } else {
            textView.text = getString(R.string.picture)
        }

        Glide.with(view)
            .load(GetDetailsInfo.links)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)


    }

    private fun apiPosterListRetrofitFragment() {
        RetrofitHttp.posterService.getImagesCategories(GetDetailsInfo.id.toString()).enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful) {
                    photos.addAll(listOf(response.body()!!))
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {

            }
        })
    }


    fun refreshAdapter(photos: ArrayList<Result>) {
        retrofitGetAdapter = RetrofitGetAdapter(requireContext(), photos)
        detailsRecyclerView.adapter = retrofitGetAdapter
//        retrofitGetAdapter.itemCLick = {
//            findNavController().navigate(R.id.detailFragment)
//        }
    }
}