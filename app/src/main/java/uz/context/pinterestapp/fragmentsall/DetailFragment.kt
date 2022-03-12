package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.DetailAdapter
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.modelSearch.Welcome
import uz.context.pinterestapp.networking.RetrofitHttp
import uz.context.pinterestapp.util.GetDetailsInfo
import uz.context.pinterestapp.util.RandomColor

class DetailFragment : Fragment() {

    lateinit var textView: TextView
    lateinit var imageView: ImageView
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var detailsRecyclerView: RecyclerView
    private lateinit var retrofitGetAdapter: DetailAdapter
    lateinit var backBtn: ImageView


    var photos = ArrayList<Result>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        nestedScrollView = view.findViewById(R.id.nestedScroll)
        textView = view.findViewById(R.id.text_view)
        imageView = view.findViewById(R.id.image_view)
        detailsRecyclerView = view.findViewById(R.id.detailRecycler)
        backBtn = view.findViewById(R.id.back_btn)

        detailsRecyclerView.setHasFixedSize(true)
        detailsRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        detailsRecyclerView.isNestedScrollingEnabled = false

        if (GetDetailsInfo.title.isNullOrEmpty()) {
            textView.text = GetDetailsInfo.title
        } else {
            textView.text = getString(R.string.picture)
        }

        Glide.with(view)
            .load(GetDetailsInfo.links)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(RandomColor.randomColor())
            .into(imageView)

        backBtn.setOnClickListener {
//            findNavController().popBackStack()
            findNavController().navigate(R.id.mainFragment)
        }

        apiPosterListRetrofitFragment()

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v.getChildAt(0).bottom <= (nestedScrollView.height + scrollY)) {
                apiPosterListRetrofitFragment()
            }
        })

    }

    private fun apiPosterListRetrofitFragment() {
        RetrofitHttp.posterService.searchPhotos(1, "Wallpaper").enqueue(object : Callback<Welcome> {
            override fun onResponse(call: Call<Welcome>, response: Response<Welcome>) {
                if (response.isSuccessful) {
                    photos.addAll(response.body()!!.results!!)
                    refreshAdapter(photos)
                } else {
                    Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Welcome>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    fun refreshAdapter(photos: ArrayList<Result>) {
        retrofitGetAdapter = DetailAdapter(requireContext(), photos)
        detailsRecyclerView.adapter = retrofitGetAdapter
        retrofitGetAdapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }
}