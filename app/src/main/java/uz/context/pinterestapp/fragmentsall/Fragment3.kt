package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.comix.overwatch.HiveProgressView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.RetrofitGetAdapter2
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.modelSearch.Welcome
import uz.context.pinterestapp.networking.RetrofitHttp

class Fragment3 : Fragment() {

    var count = 1
    var photos = ArrayList<Result>()
    lateinit var recyclerView3: RecyclerView
    lateinit var swipeRefreshLayout3: SwipeRefreshLayout
    private lateinit var searchAdapter: RetrofitGetAdapter2
    lateinit var progressBar3: HiveProgressView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_3, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {

        recyclerView3 = view.findViewById(R.id.recyclerView3)
        swipeRefreshLayout3 = view.findViewById(R.id.swipe_refresh3)
        progressBar3 = view.findViewById(R.id.progress_bar3)

        apiPosterListRetrofitFragment3()

        recyclerView3.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    count++
                    apiPosterListRetrofitFragment3()
                }
            }
        })

        recyclerView3.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView3.layoutManager = layoutManager

    }

    private fun apiPosterListRetrofitFragment3() {
        progressBar3.isVisible = true

//        RetrofitHttp.posterService.searchPhotos("cars").enqueue(object : Callback<ArrayList<ResponseItem>> {
        RetrofitHttp.posterService.searchPhotos(count, "tourism")
            .enqueue(object : Callback<Welcome> {
                override fun onResponse(
                    call: Call<Welcome>,
                    response: Response<Welcome>
                ) {
                    if (response.body() != null) {
                        photos.addAll(response.body()!!.results!!)
                        refreshAdapter(photos)
                        progressBar3.isVisible = false
                    }
                    else
                        Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Welcome>, t: Throwable) {
                    Toast.makeText(requireContext(), "Something error!", Toast.LENGTH_SHORT).show()
                    progressBar3.isVisible = false
                    t.printStackTrace()
                }
            })

        swipeRefreshLayout3.setOnRefreshListener {
            swipeRefreshLayout3.isRefreshing = false
            count++
            photos.clear()
            apiPosterListRetrofitFragment3()
            searchAdapter.notifyDataSetChanged()
        }
    }


    fun refreshAdapter(photos: ArrayList<Result>) {
        searchAdapter = RetrofitGetAdapter2(requireContext(), photos)
        recyclerView3.adapter = searchAdapter
        searchAdapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }
}