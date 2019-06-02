package imageplayer

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


class ImageViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return try {
            super.onInterceptTouchEvent(ev)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
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