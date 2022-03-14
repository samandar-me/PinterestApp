package uz.context.pinterestapp.fragmentsall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import uz.context.pinterestapp.R
import uz.context.pinterestapp.adapter.HomeAdapter

class HomeFragment : Fragment() {

    var viewPager2: ViewPager2? = null
    lateinit var homeAdapter: HomeAdapter
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }
    private fun initViews(view: View) {
        viewPager2 = view.findViewById(R.id.view_pager22)
        tabLayout = view.findViewById(R.id.tabLayout22)
        homeAdapter = HomeAdapter(childFragmentManager, lifecycle)
        viewPager2!!.adapter = homeAdapter

        tabLayout.addTab(tabLayout.newTab().setText("All"))
        tabLayout.addTab(tabLayout.newTab().setText("Wallpapers"))
        tabLayout.addTab(tabLayout.newTab().setText("Tourism"))
        tabLayout.addTab(tabLayout.newTab().setText("Space"))
        tabLayout.addTab(tabLayout.newTab().setText("Horses"))
        tabLayout.addTab(tabLayout.newTab().setText("Animals"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2!!.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewPager2!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}