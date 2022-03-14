package uz.context.pinterestapp.fragmentsall

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.comix.overwatch.HiveProgressView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.RetrofitGetAdapter
import uz.context.pinterestapp.adapter.UpdateAdapter
import uz.context.pinterestapp.model.ResponseItem
import uz.context.pinterestapp.networking.RetrofitHttp


class UpdateFragment : Fragment() {

    var photos = ArrayList<ResponseItem>()
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var updateAdapter: UpdateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        initViews(view)
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewUp)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_up)

        apiRequest()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            photos.clear()
            apiRequest()
            updateAdapter.notifyDataSetChanged()
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        updateAdapter = UpdateAdapter(requireContext(), photos)
        recyclerView.adapter = updateAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    apiRequest()
                }
            }
        })
    }

    private fun apiRequest() {
        RetrofitHttp.posterService.listPhotos1()
            .enqueue(object : Callback<ArrayList<ResponseItem>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ArrayList<ResponseItem>>,
                    response: Response<ArrayList<ResponseItem>>
                ) {
                    if (response.body() != null) {
                        photos.addAll(response.body()!!)
                        updateAdapter.notifyDataSetChanged()
                    }
                    else
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}