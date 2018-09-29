package util

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import venerealulcer.BaseActivity.Companion.getPrimaryColor

class VerticalSwipeRefreshLayout(context: Context, attrs: AttributeSet) : SwipeRefreshLayout(context, attrs) {

    private val mTouchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop

    private var mPrevX: Float = 0.toFloat()

    init {
        setColorSchemeColors(getPrimaryColor())
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> mPrevX = event.x
            MotionEvent.ACTION_MOVE -> {
                val eventX = event.x
                val xDiff = Math.abs(eventX - mPrevX)
                // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
                if (xDiff > mTouchSlop + 120) {
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    val mShowSwipe: (Boolean) -> Unit = { b ->
        if (b)
            ShowRefresh()
        else
            HideRefresh()
    }

    fun ShowRefresh() {
        uiThread { isRefreshing = true }
    }

    fun HideRefresh() {
        uiThread { isRefreshing = false }
    }

    fun DisEnable() {
        uiThread { isEnabled = false }
    }

    fun Enable() {
        uiThread { isEnabled = true }
    }

}