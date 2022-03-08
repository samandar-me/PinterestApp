package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pinterest.Networking.ResponseItem
import com.example.pinterest.Networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.RetrofitGetAdapter

class Fragment1 : Fragment() {

    var photos = ArrayList<ResponseItem>()
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1, container, false)

        recyclerView = view.findViewById(R.id.recyclerView1)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh)

        apiPosterListRetrofit()


        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            apiPosterListRetrofit()
            refreshAdapter(photos)
        }

        return view
    }
    fun apiPosterListRetrofit(){
        RetrofitHttp.posterService.listPhotos().enqueue(object : Callback<ArrayList<ResponseItem>> {
            override fun onResponse(call: Call<ArrayList<ResponseItem>>, response: Response<ArrayList<ResponseItem>>) {
                photos.clear()
                photos.addAll(response.body()!!)
                swipeRefreshLayout.isRefreshing = false
                //progressBar.visibility = View.GONE
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                refreshAdapter(photos)
            }

            override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                Log.d("@@@",t.message.toString())
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

        })
    }

     fun refreshAdapter(photos: ArrayList<ResponseItem>){
         val homeTwoAdapter = RetrofitGetAdapter(photos)
         recyclerView.adapter = homeTwoAdapter
     }
}