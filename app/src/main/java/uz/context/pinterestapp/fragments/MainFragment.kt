package uz.context.pinterestapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.ViewPagerAdapter

class MainFragment : Fragment() {

    private lateinit var tabMain: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        initViews(view)
        return view
    }

    private fun initViews(view: View) {

        tabMain = view.findViewById(R.id.tab_main)
        viewPager = view.findViewById(R.id.view_pager2)

        tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.home_selected_png)
                    }
                    1 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.search_selected_png)
                    }
                    2 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.chat_selected_png)
                    }
                    3 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.user)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.home_unselected_png)
                    }
                    1 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.search_unselected_png)
                    }
                    2 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.chat_unselected_png)
                    }
                    3 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.use)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        val viewPager2 = viewPager

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        viewPager2.adapter = adapter

        viewPager2.isUserInputEnabled = false
    }

    override fun onResume() {
        super.onResume()
        val tabLayout = tabMain
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            val inflate =
                LayoutInflater.from(context).inflate(R.layout.item_tab_main, null, false)
            tab.customView = inflate
            val imageView: ImageView = inflate.findViewById(R.id.image_tab_main)

            when (position) {
                0 -> {
                    imageView.setImageResource(R.drawable.home_unselected_png)
                }
                1 -> {
                    imageView.setImageResource(R.drawable.search_unselected_png)
                }
                2 -> {
                    imageView.setImageResource(R.drawable.chat_unselected_png)
                }
                3 -> {
                    imageView.setImageResource(R.drawable.use)
                }

            }
        }.attach()
    }
}