package venerealulcer

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import douban.FilmDetail

class FilmDetailAdapter(context: Context, filmDetail: FilmDetail?, show_loading: (b: Boolean) -> Unit) : PagerAdapter() {

    private val mContext = context
    private val mListRecycler = mutableListOf<View>()
    private val mListTitle = mutableListOf<String>()
    private var mShowLoading: (Boolean) -> Unit = show_loading
    private val mFilmDetail: FilmDetail? = filmDetail

    init {
        mListTitle.add("简介")
        mListTitle.add("影人")
        mListTitle.add("剧照")
        mListTitle.add("评论")
        mListTitle.add("影评")
        mListTitle.add("其他")

        if (mFilmDetail != null) {
            initUI(mFilmDetail)
        } else {
            mListRecycler.clear()
            mListTitle.forEach {
                mListRecycler.add(View(context))
            }
        }
    }

    private fun initUI(filmDetail: FilmDetail) {
        mListRecycler.clear()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = mListRecycler[position]
        container.addView(view)
        return view
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === p1
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun getCount(): Int {
        return mListTitle.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mListTitle[position]
    }

    fun getRecyclerView(position: Int): View {
        return mListRecycler[position]
    }

}