package uz.context.pinterestapp.fragmentsall

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.comix.overwatch.HiveProgressView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.CategoriesAdapter
import uz.context.pinterestapp.modelSearchFrag.CollectionsModelItem
import uz.context.pinterestapp.networking.RetrofitHttp
import uz.context.pinterestapp.util.GetDetailsInfo


class SearchFragment : Fragment() {

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
        refreshAdapter()

        recyclerViewSearch.setHasFixedSize(true)
        recyclerViewSearch.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun apiPosterListRetrofitFragmentSearch() {
        progressBarSearch.isVisible = true

        RetrofitHttp.posterService.getCollections()
            .enqueue(object : Callback<List<CollectionsModelItem>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<List<CollectionsModelItem>>,
                    response: Response<List<CollectionsModelItem>>
                ) {
                    if (response.isSuccessful) {
                        searchAdapter.submitData(response.body()!!)
                        progressBarSearch.isVisible = false
                    }
                }

                override fun onFailure(call: Call<List<CollectionsModelItem>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun refreshAdapter() {
        searchAdapter = CategoriesAdapter(requireContext())
        recyclerViewSearch.adapter = searchAdapter
        searchAdapter.photoClick = {
            GetDetailsInfo.id = it.id
            GetDetailsInfo.links = it.cover_photo.urls.small
            GetDetailsInfo.title = it.title
            findNavController().navigate(R.id.detailFragment)
        }
    }
}