package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.comix.overwatch.HiveProgressView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.RetrofitGetAdapter3
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.modelSearch.Welcome
import uz.context.pinterestapp.networking.RetrofitHttp

class Fragment5 : Fragment() {

    var count = 1
    var photos = ArrayList<Result>()
    lateinit var recyclerView5: RecyclerView
    lateinit var swipeRefreshLayout5: SwipeRefreshLayout
    private lateinit var adapter: RetrofitGetAdapter3
    lateinit var progressBar5: HiveProgressView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_5, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {

        recyclerView5 = view.findViewById(R.id.recyclerView5)
        swipeRefreshLayout5 = view.findViewById(R.id.swipe_refresh5)
        progressBar5 = view.findViewById(R.id.progress_bar5)

        apiPosterListRetrofitFragment5()

        recyclerView5.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    count++
                    apiPosterListRetrofitFragment5()
                }
            }
        })

        recyclerView5.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView5.layoutManager = layoutManager


        swipeRefreshLayout5.setOnRefreshListener {
            count++
            photos.clear()
            swipeRefreshLayout5.isRefreshing = false
            apiPosterListRetrofitFragment5()
            adapter.notifyDataSetChanged()
        }
    }

    private fun apiPosterListRetrofitFragment5() {
        progressBar5.isVisible = true

        RetrofitHttp.posterService.searchPhotos(count,"horses").enqueue(object : Callback<Welcome> {
            override fun onResponse(
                call: Call<Welcome>,
                response: Response<Welcome>
            ) {
                if (response.body() != null) {
                    photos.addAll(response.body()!!.results!!)
                    refreshAdapter(photos)
                    progressBar5.isVisible = false
                }
                else
                    Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Welcome>, t: Throwable) {
                Toast.makeText(requireContext(), "Something error!", Toast.LENGTH_SHORT).show()
                progressBar5.isVisible = false
                t.printStackTrace()
            }
        })
    }


    fun refreshAdapter(photos: ArrayList<Result>) {
        adapter = RetrofitGetAdapter3(requireContext(), photos)
        recyclerView5.adapter = adapter
        adapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }
}