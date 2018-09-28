package douban.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import douban.FilmDetail
import douban.subview.IFilmView
import douban.subview.FilmView
import douban.subview.FilmViewCelebrity
import douban.subview.FilmViewSummary

//电影详情页面
class FilmDetailAdapter(context: Context, filmDetail: FilmDetail?) : PagerAdapter() {

    private val mContext = context
    private val mListRecycler = mutableListOf<IFilmView>()
    private val mListTitle = mutableListOf<String>()
    private val mFilmDetail: FilmDetail? = filmDetail

    init {
        mListTitle.add("简介")
        mListTitle.add("影人")
        mListTitle.add("剧照")
        mListTitle.add("短评")
        mListTitle.add("影评")
        mListTitle.add("其他")

        if (mFilmDetail != null) {
            initUI(mFilmDetail)
        } else {
            //Empty Detail Activity
            mListRecycler.clear()
            mListTitle.forEach {
                mListRecycler.add(FilmView(context))
            }
        }
    }

    private fun initUI(filmDetail: FilmDetail) {
        mListRecycler.clear()
        mListRecycler.add(FilmViewSummary(mContext, filmDetail))
        mListRecycler.add(FilmViewCelebrity(mContext, filmDetail))
        mListRecycler.add(FilmViewSummary(mContext, filmDetail))
        mListRecycler.add(FilmViewSummary(mContext, filmDetail))
        mListRecycler.add(FilmViewSummary(mContext, filmDetail))
        mListRecycler.add(FilmViewSummary(mContext, filmDetail))
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = mListRecycler[position]
        container.addView(view.getView())
        return view.getView()
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

    fun getRecyclerView(position: Int): IFilmView {
        return mListRecycler[position]
    }

}