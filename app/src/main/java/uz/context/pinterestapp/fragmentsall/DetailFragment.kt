package uz.context.pinterestapp.fragmentsall

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.circularreveal.cardview.CircularRevealCardView
import es.dmoral.toasty.Toasty
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.DetailAdapter
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.modelSearch.Welcome
import uz.context.pinterestapp.networking.RetrofitHttp
import uz.context.pinterestapp.util.GetDetailsInfo
import uz.context.pinterestapp.util.RandomColor
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class DetailFragment : Fragment() {

    var count = 1
    private lateinit var saveCardView: CircularRevealCardView
    lateinit var textView: TextView
    lateinit var imageView: ImageView
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var detailsRecyclerView: RecyclerView
    private lateinit var retrofitGetAdapter: DetailAdapter
    lateinit var backBtn: ImageView
    private var outStream: FileOutputStream? = null


    var photos = ArrayList<Result>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        nestedScrollView = view.findViewById(R.id.nestedScroll)
        textView = view.findViewById(R.id.text_view)
        imageView = view.findViewById(R.id.image_view)
        detailsRecyclerView = view.findViewById(R.id.detailRecycler)
        backBtn = view.findViewById(R.id.back_btn)
        saveCardView = view.findViewById(R.id.saveCard)

        detailsRecyclerView.setHasFixedSize(true)
        detailsRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        detailsRecyclerView.isNestedScrollingEnabled = false

        if (GetDetailsInfo.title.isNullOrEmpty()) {
            textView.text = GetDetailsInfo.title
        } else {
            textView.text = getString(R.string.picture)
        }

        Glide.with(view)
            .load(GetDetailsInfo.links)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(RandomColor.randomColor())
            .into(imageView)

        backBtn.setOnClickListener {
//            findNavController().popBackStack()
            findNavController().navigate(R.id.mainFragment)
        }

        apiPosterListRetrofitFragment()
        refreshAdapter(photos)

        saveCardView.setOnClickListener {
            saveGallery(imageView)
        }

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= (nestedScrollView.height + scrollY)) {
                count++
                apiPosterListRetrofitFragment()
            }
        })
    }

    private fun apiPosterListRetrofitFragment() {
        RetrofitHttp.posterService.searchPhotos(count, "Wallpaper")
            .enqueue(object : Callback<Welcome> {
                override fun onResponse(call: Call<Welcome>, response: Response<Welcome>) {
                    if (response.isSuccessful) {
                        photos.addAll(response.body()!!.results!!)
                        retrofitGetAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(context, "Limit has ended", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Welcome>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }


    private fun refreshAdapter(photos: ArrayList<Result>) {
        retrofitGetAdapter = DetailAdapter(requireContext(), photos)
        detailsRecyclerView.adapter = retrofitGetAdapter
        retrofitGetAdapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }

    private fun saveGallery(iv_image: ImageView) {
        val bitmap = getScreenShotFromView(iv_image)

        if (bitmap != null) {
            saveMediaToStorage(bitmap)
        }
    }

    private fun getScreenShotFromView(v: View): Bitmap? {
        var screenshot: Bitmap? = null

        try {
            screenshot =
                Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        return screenshot
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context?.contentResolver?.also { resolver ->

                val contentValues = ContentValues().apply {

                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toasty.success(requireContext(), "Saved!", Toast.LENGTH_SHORT, true).show();
        }
    }
}