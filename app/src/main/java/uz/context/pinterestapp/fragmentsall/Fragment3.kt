package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import uz.context.pinterestapp.model.ResponseItem
import uz.context.pinterestapp.networking.RetrofitHttp

class Fragment3 : Fragment() {

    var photos = ArrayList<ResponseItem>()
    lateinit var recyclerView3: RecyclerView
    lateinit var swipeRefreshLayout3: SwipeRefreshLayout
    lateinit var progressBar3: HiveProgressView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        recyclerView3 = view.findViewById(R.id.recyclerView3)
        swipeRefreshLayout3 = view.findViewById(R.id.swipe_refresh3)
        progressBar3 = view.findViewById(R.id.progress_bar3)

        apiPosterListRetrofitFragment3()

        swipeRefreshLayout3.setOnRefreshListener {
            swipeRefreshLayout3.isRefreshing = false
            photos.clear()
            apiPosterListRetrofitFragment3()
        }

        recyclerView3.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView3.layoutManager = layoutManager

        return view
    }

    fun apiPosterListRetrofitFragment3(){
        progressBar3.visibility = View.VISIBLE
        RetrofitHttp.posterService.searchPhotos("cars").enqueue(object :
            Callback<ArrayList<ResponseItem>> {
            override fun onResponse(call: Call<ArrayList<ResponseItem>>, response: Response<ArrayList<ResponseItem>>) {
                photos.clear()
                if (response.body() != null)
                    photos.addAll(response.body()!!)
                else
                    Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout3.isRefreshing = false
                progressBar3.visibility = View.GONE
                refreshAdapter(photos)
            }

            override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                Log.d("@@@111",t.message.toString())
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                progressBar3.visibility = View.GONE
            }

        })
    }


    fun refreshAdapter(photos: ArrayList<ResponseItem>){
        val homeTwoAdapter = RetrofitGetAdapter2(requireContext(),photos)
        recyclerView3.adapter = homeTwoAdapter

        //adapterdan fragmentga intent qilish
        homeTwoAdapter.itemCLick = {
            Log.d("@@@","XATOLIK")

            findNavController().navigate(R.id.detailFragment)
        }
    }
}