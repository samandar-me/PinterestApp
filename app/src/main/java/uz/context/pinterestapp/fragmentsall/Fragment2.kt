package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
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
import uz.context.pinterestapp.adapter.RetrofitGetAdapter
import uz.context.pinterestapp.adapter.RetrofitGetAdapter2
import uz.context.pinterestapp.model.ResponseItem
import uz.context.pinterestapp.networking.RetrofitHttp

class Fragment2 : Fragment() {

    var photos = ArrayList<ResponseItem>()
    lateinit var recyclerView2: RecyclerView
    private lateinit var homeTwoAdapter: RetrofitGetAdapter2
    lateinit var swipeRefreshLayout2: SwipeRefreshLayout
    lateinit var progressBar2: HiveProgressView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_2, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {


        recyclerView2 = view.findViewById(R.id.recyclerView2)
        swipeRefreshLayout2 = view.findViewById(R.id.swipe_refresh2)
        progressBar2 = view.findViewById(R.id.progress_bar2)

        apiPosterListRetrofitFragment2()

        recyclerView2.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView2.layoutManager = layoutManager

        recyclerView2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    apiPosterListRetrofitFragment2()
                }
            }
        })
    }

    fun apiPosterListRetrofitFragment2(){
        progressBar2.visibility = View.VISIBLE
        RetrofitHttp.posterService.listPhotos2().enqueue(object :
            Callback<ArrayList<ResponseItem>> {
            override fun onResponse(call: Call<ArrayList<ResponseItem>>, response: Response<ArrayList<ResponseItem>>) {
                if (response.body() != null)
                    photos.addAll(response.body()!!)
                else
                    Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout2.isRefreshing = false
                progressBar2.visibility = View.GONE
                refreshAdapter(photos)
            }

            override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                progressBar2.visibility = View.GONE
            }

        })

        swipeRefreshLayout2.setOnRefreshListener {
            photos.clear()
            swipeRefreshLayout2.isRefreshing = false
            apiPosterListRetrofitFragment2()
            homeTwoAdapter.notifyDataSetChanged()
        }

    }


    fun refreshAdapter(photos: ArrayList<ResponseItem>){
        homeTwoAdapter = RetrofitGetAdapter2(requireContext(),photos)
        recyclerView2.adapter = homeTwoAdapter

        homeTwoAdapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }

}