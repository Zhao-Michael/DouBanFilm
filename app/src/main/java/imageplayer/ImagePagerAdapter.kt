package imageplayer

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.chrisbanes.photoview.PhotoView
import org.jetbrains.anko.find
import util.setImageUrl
import venerealulcer.R


class ImagePagerAdapter(context: Context, listUrl: List<String>) : PagerAdapter() {

    private val mListUrl = listUrl
    private val mContext = context

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val obj = mListUrl[position]
        val view = LayoutInflater.from(mContext).inflate(R.layout.bigimage_layout, null)
        val photoView = view.find<PhotoView>(R.id.mPhotoView)
        photoView.setImageUrl(obj)
        container.addView(view)
        return view
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