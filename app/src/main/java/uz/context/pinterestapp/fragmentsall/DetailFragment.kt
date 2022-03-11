package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import uz.context.pinterestapp.adapter.RetrofitGetAdapter2
import uz.context.pinterestapp.adapter.RetrofitGetAdapter3
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.modelSearch.Urls2
import uz.context.pinterestapp.modelSearch.Welcome
import uz.context.pinterestapp.networking.RetrofitHttp
import uz.context.pinterestapp.util.GetDetailsInfo

class DetailFragment : Fragment() {

    lateinit var textView: TextView
    lateinit var imageView: ImageView
    private lateinit var detailsRecyclerView: RecyclerView
    private lateinit var retrofitGetAdapter: RetrofitGetAdapter2
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

        photos.add(Result("lK3oK4lKUVU","Title", Urls2("","","","https://images.unsplash.com/photo-1644661458938-eb61b5019963?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMDc5MTB8MHwxfGFsbHx8fHx8fHx8fDE2NDY5OTk5Nzc&ixlib=rb-1.2.1&q=80&w=400","","")))
        photos.add(Result("lK3oK4lKUVU","Title", Urls2("","","","https://images.unsplash.com/photo-1644661458938-eb61b5019963?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMDc5MTB8MHwxfGFsbHx8fHx8fHx8fDE2NDY5OTk5Nzc&ixlib=rb-1.2.1&q=80&w=400","","")))

        apiPosterListRetrofitDetail()

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

    private fun apiPosterListRetrofitDetail() {

        RetrofitHttp.posterService.searchPhotos(4, "wallpapers")
            .enqueue(object : Callback<Welcome> {
                override fun onResponse(
                    call: Call<Welcome>,
                    response: Response<Welcome>
                ) {
                    if (response.body() != null) {
                        photos.addAll(response.body()!!.results!!)
                        Toast.makeText(context, "onResponse", Toast.LENGTH_SHORT).show()
                        refreshAdapter(photos)
                        Log.d("@@@@@@",response.body().toString())
                    } else
                        Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Welcome>, t: Throwable) {
                    Toast.makeText(requireContext(), "Something error!", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            })
    }

    fun refreshAdapter(photos: ArrayList<Result>) {
        retrofitGetAdapter = RetrofitGetAdapter2(requireContext(), photos)
        detailsRecyclerView.adapter = retrofitGetAdapter
        retrofitGetAdapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }
}

//    private fun apiPosterListRetrofitFragment() {
//        RetrofitHttp.posterService.getImagesCategories(GetDetailsInfo.id.toString()).enqueue(object : Callback<Result> {
//            override fun onResponse(call: Call<Result>, response: Response<Result>) {
//                if (response.isSuccessful) {
//                    photos.addAll(listOf(response.body()!!))
//                }
//            }
//
//            override fun onFailure(call: Call<Result>, t: Throwable) {
//
//            }
//        })
//    }


//    fun refreshAdapter(photos: ArrayList<Result>) {
//        retrofitGetAdapter = RetrofitGetAdapter(requireContext(), photos)
//        detailsRecyclerView.adapter = retrofitGetAdapter
////        retrofitGetAdapter.itemCLick = {
////            findNavController().navigate(R.id.detailFragment)
////        }
//    }
