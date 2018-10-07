package douban.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import douban.FilmMan
import douban.subview.FilmCelebritySummary
import douban.subview.FilmView
import douban.subview.IFilmView

class FilmManAdapter(context: Context, filmMan: FilmMan?) : PagerAdapter() {

    private val mContext = context
    private val mListRecycler = mutableListOf<IFilmView>()
    private val mListTitle = mutableListOf<String>()
    private val mFilmMan: FilmMan? = filmMan

    init {
        mListTitle.add("影人简介")
        mListTitle.add("影人图片")
        mListTitle.add("影人作品")

        if (mFilmMan != null) {
            initUI(mFilmMan)
        } else {
            //Empty Film Man Activity
            mListRecycler.clear()
            mListTitle.forEach {
                mListRecycler.add(FilmView(context))
            }
        }

    }

    private fun initUI(filmMan: FilmMan) {
        mListRecycler.clear()
        mListRecycler.add(FilmCelebritySummary(mContext, filmMan))
        mListRecycler.add(FilmView(mContext))
        mListRecycler.add(FilmView(mContext))
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

}