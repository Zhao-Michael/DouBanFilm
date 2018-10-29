package imageplayer

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.github.chrisbanes.photoview.PhotoView
import michaelzhao.BaseActivity
import org.jetbrains.anko.find
import util.setImageUrl
import michaelzhao.R


class ImagePagerAdapter(context: Context, listUrl: List<String>) : PagerAdapter() {

    private val mListUrl = listUrl
    private val mContext = context
    private var mCurrentView: View? = null
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val obj = mListUrl[position]
        val view = LayoutInflater.from(mContext).inflate(R.layout.full_image_layout, null)
        view.find<ProgressBar>(R.id.image_progress_bar).indeterminateTintList = ColorStateList.valueOf(BaseActivity.getPrimaryColor())
        val photoView = view.find<PhotoView>(R.id.mPhotoView)
        photoView.setImageUrl(obj)
        container.addView(view)
        return view
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        mCurrentView = obj as? View
    }

    fun getCurrView(): View? {
        return mCurrentView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun getItemPosition(obj: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === p1
    }

    override fun getCount(): Int {
        return mListUrl.size
    }

}