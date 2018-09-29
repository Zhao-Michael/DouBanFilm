package douban.subview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import org.jetbrains.anko.find
import util.VerticalSwipeRefreshLayout
import venerealulcer.R

abstract class IFilmView(context: Context) {

    protected abstract val mLayout: Int

    protected val mView: View by lazy { LayoutInflater.from(context).inflate(mLayout, null) }

    protected val mContext = context

    protected val mRecyclerView by lazy { mView.find<RecyclerView>(R.id.mRecyclerView) }

    protected val mSwipeLayout by lazy { mView.find<VerticalSwipeRefreshLayout>(R.id.mSwipeLayout) }

    fun getView(): View {
        return if (mLayout == 0)
            View(mContext)
        else
            mView
    }

}

class FilmView(context: Context) : IFilmView(context) {
    override val mLayout: Int = 0
}