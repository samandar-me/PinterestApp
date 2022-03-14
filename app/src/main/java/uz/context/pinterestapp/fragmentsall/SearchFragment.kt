package uz.context.pinterestapp.fragmentsall

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
    private lateinit var textView: TextView
    private lateinit var linearLayout: LinearLayout

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
        textView = view.findViewById(R.id.textViewSearch)
        progressBarSearch = view.findViewById(R.id.progress_bar_search)
        linearLayout = view.findViewById(R.id.linearlayout)

        linearLayout.setOnClickListener {
            findNavController().navigate(R.id.searchResultFragment)
        }

        apiPosterListRetrofitFragmentSearch()
        refreshAdapter(photos)

        recyclerViewSearch.setHasFixedSize(true)
        recyclerViewSearch.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun apiPosterListRetrofitFragmentSearch() {
        progressBarSearch.isVisible = true

        RetrofitHttp.posterService.getCollections()
            .enqueue(object : Callback<ArrayList<CollectionsModel>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ArrayList<CollectionsModel>>,
                    response: Response<ArrayList<CollectionsModel>>
                ) {
                    if (response.isSuccessful) {
                        photos.addAll(response.body()!!)
                        searchAdapter.notifyDataSetChanged()
                        progressBarSearch.isVisible = false
                    }
                }

                override fun onFailure(call: Call<ArrayList<CollectionsModel>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun refreshAdapter(photos: ArrayList<CollectionsModel>) {
        searchAdapter = CategoriesAdapter(requireContext(), photos)
        recyclerViewSearch.adapter = searchAdapter
    }
}