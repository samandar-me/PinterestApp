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
import uz.context.pinterestapp.adapter.RetrofitGetAdapter2
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.modelSearch.Welcome
import uz.context.pinterestapp.networking.RetrofitHttp

class Fragment6 : Fragment() {

    var count = 1
    var photos = ArrayList<Result>()
    lateinit var recyclerView6: RecyclerView
    lateinit var swipeRefreshLayout6: SwipeRefreshLayout
    private lateinit var adapter: RetrofitGetAdapter2
    lateinit var progressBar6: HiveProgressView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_6, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {

        recyclerView6 = view.findViewById(R.id.recyclerView6)
        swipeRefreshLayout6 = view.findViewById(R.id.swipe_refresh6)
        progressBar6 = view.findViewById(R.id.progress_bar6)

        apiPosterListRetrofitFragment6()

        recyclerView6.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    count++
                    apiPosterListRetrofitFragment6()
                }
            }
        })


        recyclerView6.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView6.layoutManager = layoutManager

        swipeRefreshLayout6.setOnRefreshListener {
            count++
            photos.clear()
            swipeRefreshLayout6.isRefreshing = false
            apiPosterListRetrofitFragment6()
            adapter.notifyDataSetChanged()
        }
    }

    private fun apiPosterListRetrofitFragment6() {
        progressBar6.isVisible = true

        RetrofitHttp.posterService.searchPhotos(count,"animals").enqueue(object : Callback<Welcome> {
            override fun onResponse(
                call: Call<Welcome>,
                response: Response<Welcome>
            ) {
                if (response.body() != null) {
                    photos.addAll(response.body()!!.results!!)
                    refreshAdapter(photos)
                    progressBar6.isVisible = false
                }
                else
                    Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Welcome>, t: Throwable) {
                Toast.makeText(requireContext(), "Something error!", Toast.LENGTH_SHORT).show()
                progressBar6.isVisible = false
                t.printStackTrace()
            }
        })
    }


    fun refreshAdapter(photos: ArrayList<Result>) {
        adapter = RetrofitGetAdapter2(requireContext(), photos)
        recyclerView6.adapter = adapter
        adapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }
}