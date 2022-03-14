package uz.context.pinterestapp.fragmentsall

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.comix.overwatch.HiveProgressView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.DetailAdapter
import uz.context.pinterestapp.adapter.RetrofitGetAdapter2
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.modelSearch.Welcome
import uz.context.pinterestapp.networking.RetrofitHttp

class SearchResultFragment : Fragment() {

    private lateinit var editText: EditText
    private lateinit var progressBar: HiveProgressView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DetailAdapter
    private val photos = ArrayList<Result>()
    private lateinit var textView: TextView
    private lateinit var searchText: String
    var count = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_result, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        editText = view.findViewById(R.id.editTextSearch)
        textView = view.findViewById(R.id.textViewCancel)
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = DetailAdapter(requireContext(), photos)
        recyclerView.adapter = adapter

        textView.setOnClickListener {
            findNavController().navigate(R.id.action_searchResultFragment_to_searchFragment)
        }

        editText.setOnEditorActionListener { _, actionId, keyEvent ->
            if ((keyEvent != null && (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                progressBar.isVisible = true
                hideKeyboard(requireActivity(), editText)

                if (editText.text.isNotBlank()) {
                    searchText = editText.text.toString()
                    requestApi()
                }
            }
            false
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    count++
                    requestApi()
                }
            }
        })
        adapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }

    private fun requestApi() {
        RetrofitHttp.posterService.searchPhotos(count, searchText)
            .enqueue(object : Callback<Welcome> {
                override fun onResponse(call: Call<Welcome>, response: Response<Welcome>) {
                    if (response.isSuccessful) {
                        progressBar.isVisible = false
                        photos.addAll(response.body()!!.results!!)
                    }
                }

                override fun onFailure(call: Call<Welcome>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun hideKeyboard(activity: Activity, viewToHide: View) {
        val imm = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewToHide.windowToken, 0)
    }

    private fun refreshAdapter(photos: ArrayList<Result>) {
        adapter = DetailAdapter(requireContext(), photos)
        recyclerView.adapter = adapter

    }
}