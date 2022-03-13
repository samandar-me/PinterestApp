package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.comix.overwatch.HiveProgressView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.CategoriesAdapter
import uz.context.pinterestapp.model.ResponseItem
import uz.context.pinterestapp.modelSearchFrag.CollectionsModel
import uz.context.pinterestapp.networking.RetrofitHttp


class SearchFragment : Fragment() {

    var photos = ArrayList<CollectionsModel>()
    lateinit var recyclerViewSearch: RecyclerView
    private lateinit var searchAdapter: CategoriesAdapter
    lateinit var progressBarSearch: HiveProgressView
    lateinit var textViewSearch: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {

        recyclerViewSearch = view.findViewById(R.id.recyclerViewSearch)
        progressBarSearch = view.findViewById(R.id.progress_bar_search)
        textViewSearch = view.findViewById(R.id.textViewSearch)

        apiPosterListRetrofitFragmentSearch()
        refreshAdapter(photos)

        recyclerViewSearch.setHasFixedSize(true)
        recyclerViewSearch.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    }

    private fun apiPosterListRetrofitFragmentSearch() {
        progressBarSearch.visibility = View.VISIBLE
        RetrofitHttp.posterService.getCollections()

            .enqueue(object : Callback<CollectionsModel> {
                override fun onResponse(
                    call: Call<CollectionsModel>,
                    response: Response<CollectionsModel>
                ) {
                    textViewSearch.text = response.body().toString()
                    progressBarSearch.isVisible = false
                }

                override fun onFailure(call: Call<CollectionsModel>, t: Throwable) {

                }


            })
    }

    fun refreshAdapter(photos: ArrayList<CollectionsModel>) {
        searchAdapter = CategoriesAdapter(requireContext(), photos)
        recyclerViewSearch.adapter = searchAdapter
//        searchAdapter.itemCLick = {
//            findNavController().navigate(R.id.detailFragment)
//        }
    }
}