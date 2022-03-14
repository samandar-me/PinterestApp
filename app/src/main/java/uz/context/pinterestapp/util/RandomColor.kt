package uz.context.pinterestapp.util

import uz.context.pinterestapp.R
import java.util.*
import kotlin.collections.ArrayList

object RandomColor {

    fun randomColor(): Int {
        val random = Random()
        val colorList = ArrayList<Int>()

        colorList.add(R.color.random1)
        colorList.add(R.color.random2)
        colorList.add(R.color.random3)
        colorList.add(R.color.random4)
        colorList.add(R.color.random5)
        colorList.add(R.color.random6)
        colorList.add(R.color.random7)

        return colorList[random.nextInt(colorList.size)]
    }
}