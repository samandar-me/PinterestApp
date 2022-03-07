package uz.context.pinterestapp.adapter

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.context.pinterestapp.fragments.UpdateFragment
import uz.context.pinterestapp.fragments.MessageFragment

class MessageAdapter(private val fragments: ArrayList<Fragment>): PagerAdapter() {
    override fun getCount(): Int {
        return fragments.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return true
    }
}