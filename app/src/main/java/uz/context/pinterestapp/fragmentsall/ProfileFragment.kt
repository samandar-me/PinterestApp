package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.DetailAdapter
import uz.context.pinterestapp.adapter.ImageSaveAdapter
import uz.context.pinterestapp.database.MyDatabase
import uz.context.pinterestapp.database.SaveImage
import uz.context.pinterestapp.model.User
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.networking.RetrofitHttp
import uz.context.pinterestapp.util.GetDetailsInfo
import uz.context.pinterestapp.util.RandomColor

class ProfileFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myDatabase: MyDatabase
    private lateinit var adapter: ImageSaveAdapter
    private lateinit var imageView: ShapeableImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        getUser()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        imageView = view.findViewById(R.id.ivProfile)

        myDatabase = MyDatabase.getInstance(requireContext())
        adapter = ImageSaveAdapter()

        adapter.saveImage(myDatabase.dao().getImages())
        recyclerView.adapter = adapter

        adapter.photoClick = {
            GetDetailsInfo.id = it.imageId
            GetDetailsInfo.links = it.url
            GetDetailsInfo.title = it.url
            findNavController().navigate(R.id.detailFragment)
        }
    }

    private fun getUser() {
        RetrofitHttp.posterService.getUser("samandarcoderdev").enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Glide.with(requireActivity())
                        .load(response.body()!!.profileImage!!.large)
                        .placeholder(RandomColor.randomColor())
                        .into(imageView)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}