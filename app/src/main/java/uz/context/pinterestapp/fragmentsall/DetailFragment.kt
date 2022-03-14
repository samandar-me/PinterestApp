package uz.context.pinterestapp.fragmentsall

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.circularreveal.cardview.CircularRevealCardView
import com.google.android.material.imageview.ShapeableImageView
import es.dmoral.toasty.Toasty
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.DetailAdapter
import uz.context.pinterestapp.adapter.RetrofitGetAdapter
import uz.context.pinterestapp.database.MyDatabase
import uz.context.pinterestapp.database.SaveImage
import uz.context.pinterestapp.model.Links
import uz.context.pinterestapp.model.ResponseItem
import uz.context.pinterestapp.model.Sponsor
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.modelSearch.Welcome
import uz.context.pinterestapp.networking.RetrofitHttp
import uz.context.pinterestapp.util.GetDetailsInfo
import uz.context.pinterestapp.util.RandomColor
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class DetailFragment : Fragment() {

    var count = 1
    private lateinit var saveCardView: CircularRevealCardView
    lateinit var textView: TextView
    private lateinit var viewBtn: CircularRevealCardView
    lateinit var imageView: ImageView
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var detailsRecyclerView: RecyclerView
    private lateinit var retrofitGetAdapter: RetrofitGetAdapter
    lateinit var backBtn: ImageView
    private lateinit var shareImageView: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileSubscriber: TextView
    private lateinit var profileImage: ShapeableImageView
    private lateinit var myDatabase: MyDatabase

    var photos = ArrayList<ResponseItem>()
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
        viewBtn = view.findViewById(R.id.viewCard)
        shareImageView = view.findViewById(R.id.shareImage)
        profileName = view.findViewById(R.id.profileName)
        profileImage = view.findViewById(R.id.profileImage)
        profileSubscriber = view.findViewById(R.id.profileSubscriber)

        myDatabase = MyDatabase.getInstance(requireContext())

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
            findNavController().navigate(R.id.mainFragment)
        }
        viewBtn.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_fullScreenFragment)
        }

        shareImageView.setOnClickListener {
            shareImageView()
        }

        apiPosterListRetrofitFragment()
        refreshAdapter(photos)

        saveCardView.setOnClickListener {
            showBottomSheet()
        }

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= (nestedScrollView.height + scrollY)) {
                count++
                apiPosterListRetrofitFragment()
            }
        })
    }

    private fun apiPosterListRetrofitFragment() {
        RetrofitHttp.posterService.listPhotos1()
            .enqueue(object : Callback<ArrayList<ResponseItem>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ArrayList<ResponseItem>>,
                    response: Response<ArrayList<ResponseItem>>
                ) {
                    photos.addAll(response.body()!!)
                    retrofitGetAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }


    private fun refreshAdapter(photos: ArrayList<ResponseItem>) {
        retrofitGetAdapter = RetrofitGetAdapter(requireContext(), photos)
        detailsRecyclerView.adapter = retrofitGetAdapter
        retrofitGetAdapter.itemCLick = {
            findNavController().navigate(R.id.detailFragment)
        }
    }

    private fun showBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val textGallery: TextView = view.findViewById(R.id.textGallery)
        val textProfile: TextView = view.findViewById(R.id.textProfile)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        textGallery.setOnClickListener {
            saveToGallery(imageView)
            dialog.dismiss()
        }
        textProfile.setOnClickListener {
            saveToDatabase()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun saveToDatabase() {
        if (GetDetailsInfo.title.isNullOrEmpty()) {
            myDatabase.dao()
                .saveImage(
                    SaveImage(
                        GetDetailsInfo.id!!,
                        GetDetailsInfo.links!!,
                        GetDetailsInfo.title!!
                    )
                )
        } else {
            myDatabase.dao()
                .saveImage(
                    SaveImage(
                        GetDetailsInfo.id!!,
                        GetDetailsInfo.links!!,
                        ""
                    )
                )
        }
        toasty("Saved!")
    }

    private fun shareImageView() {
        val bitmapDrawable = imageView.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val bitmapPath = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            bitmap,
            "title",
            null
        )
        val uri = Uri.parse(bitmapPath)
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/jpg"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, GetDetailsInfo.links)
        }
        startActivity(Intent.createChooser(intent, "Share"))
    }

    private fun saveToGallery(iv_image: ImageView) {
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
            e.printStackTrace()
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
            toasty("Saved!")
        }
    }

    private fun toasty(msg: String) {
        Toasty.success(requireContext(), msg, Toasty.LENGTH_LONG, true).show()
    }
}