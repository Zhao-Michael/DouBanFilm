package imageplayer

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent


class ImageViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return try {
            super.onInterceptTouchEvent(ev)
        } catch (e: IllegalArgumentException) {
            println(e.message)
            false
        }
    }

    fun setPageChangeListen(pageChange: (index: Int) -> Unit) {
        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) = Unit
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) = Unit
            override fun onPageSelected(p0: Int) {
                pageChange.invoke(p0)
            }
        })
    }

}