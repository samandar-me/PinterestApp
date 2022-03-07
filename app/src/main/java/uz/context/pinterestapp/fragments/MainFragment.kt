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

    var tabSelected = 0
    lateinit var tab_main: TabLayout
    lateinit var view_pager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        tab_main = view.findViewById(R.id.tab_main)
        view_pager = view.findViewById(R.id.view_pager)

//        tab_main.tabRippleColor = null

        //graph
        tab_main.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabSelected = tab?.position!!
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
                            ?.setImageResource(R.drawable.ic_launcher_background)
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
                            ?.setImageResource(R.drawable.ic_launcher_background)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        //end graph

        //adapter start

        val viewPager2 = view_pager

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        viewPager2.adapter = adapter

        //ViewPagerni Swipe true fale
        viewPager2.isUserInputEnabled = false

        //adapter end

        return view
    }

    override fun onResume() {
        super.onResume()
        val tabLayout = tab_main
        TabLayoutMediator(tabLayout, view_pager) { tab, position ->

            val inflate =
                LayoutInflater.from(context).inflate(R.layout.item_tab_main, null, false)
            tab.customView = inflate

            when (position) {
                0 -> {
                    inflate.findViewById<ImageView>(R.id.image_tab_main)
                        .setImageResource(R.drawable.home_unselected_png)
                }
                1 -> {
                    inflate.findViewById<ImageView>(R.id.image_tab_main)
                        .setImageResource(R.drawable.search_unselected_png)
                }
                2 -> {
                    inflate.findViewById<ImageView>(R.id.image_tab_main)
                        .setImageResource(R.drawable.chat_unselected_png)
                }
                3 -> {
                    inflate.findViewById<ImageView>(R.id.image_tab_main)
                        .setImageResource(R.drawable.ic_launcher_background)
                }

            }

            if (tabSelected == position) {
                inflate.findViewById<ImageView>(R.id.image_tab_main)
                    .setImageResource(R.drawable.home_selected_png)
            }
        }.attach()
    }

}