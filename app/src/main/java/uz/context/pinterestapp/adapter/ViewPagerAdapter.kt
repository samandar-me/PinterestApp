package uz.context.pinterestapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.context.pinterestapp.fragmentsall.HomeFragment
import uz.context.pinterestapp.fragmentsall.MessageFragment
import uz.context.pinterestapp.fragmentsall.ProfileFragment
import uz.context.pinterestapp.fragmentsall.SearchFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {

            0 -> {
                HomeFragment()
            }
            1 -> {
                SearchFragment()
            }
            2 -> {
                MessageFragment()
            }
            3 -> {
                ProfileFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}
