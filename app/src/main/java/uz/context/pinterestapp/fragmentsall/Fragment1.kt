package uz.context.pinterestapp.fragmentsall

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import uz.context.pinterestapp.model.ResponseItem
import uz.context.pinterestapp.networking.RetrofitHttp
/////
class Fragment1 : Fragment() {

    var photos = ArrayList<ResponseItem>()
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var progressBar1: HiveProgressView
    private lateinit var retrofitGetAdapter: RetrofitGetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView1)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        progressBar1 = view.findViewById(R.id.progress_bar1)

        apiPosterListRetrofitFragment1()


        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            photos.clear()
            apiPosterListRetrofitFragment1()
            retrofitGetAdapter.notifyDataSetChanged()
        }

        recyclerView.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = layoutManager
        retrofitGetAdapter = RetrofitGetAdapter(requireContext(),photos)
        recyclerView.adapter = retrofitGetAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    apiPosterListRetrofitFragment1()
                }
            }
        })
        retrofitGetAdapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }

    private fun apiPosterListRetrofitFragment1() {
        progressBar1.visibility = View.VISIBLE
        RetrofitHttp.posterService.listPhotos1()
            .enqueue(object : Callback<ArrayList<ResponseItem>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ArrayList<ResponseItem>>,
                    response: Response<ArrayList<ResponseItem>>
                ) {
                    if (response.body() != null)
                    photos.addAll(response.body()!!)
                    else
                        Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
                    swipeRefreshLayout.isRefreshing = false
                    progressBar1.visibility = View.GONE
                    retrofitGetAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                    Log.d("@@@", t.message.toString())
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    progressBar1.visibility = View.GONE
                }
            })
    }
}