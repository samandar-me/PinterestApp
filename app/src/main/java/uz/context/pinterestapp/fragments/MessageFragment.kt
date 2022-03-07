package uz.context.pinterestapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import uz.context.pinterestapp.R


class MessageFragment : Fragment() {

    private lateinit var viewPager2: ViewPager
    private lateinit var tabLayout: SmartTabLayout
    private lateinit var messageAdapter: FragmentPagerItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_message, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        viewPager2 = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        messageAdapter = FragmentPagerItemAdapter(
            childFragmentManager, FragmentPagerItems.with(requireContext())
                .add("fjkdsl", UpdateFragment::class.java)
                .add("fdsa", MessagesFragment::class.java)
                .create())
        viewPager2.adapter = messageAdapter
        tabLayout.setViewPager(viewPager2)

    }
    private fun fragments(): ArrayList<Fragment> {
        return arrayListOf(
            UpdateFragment(),
            MessagesFragment()
        )
    }
}