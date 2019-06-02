package douban.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import douban.FilmDetail
import douban.subview.*

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
        //mListTitle.add("下载")

        if (mFilmDetail != null) {
            initUI(mFilmDetail)
        } else {
            //Empty Detail Activity
            mListRecycler.clear()
            mListTitle.forEach {
                mListRecycler.add(FilmView(mContext))
            }
        }
    }

    private fun initUI(filmDetail: FilmDetail) {
        mListRecycler.clear()
        mListRecycler.add(FilmSummaryView(mContext, filmDetail))
        mListRecycler.add(FilmCelebrityView(mContext, filmDetail))
        mListRecycler.add(FilmPhotoView(mContext, filmDetail))
        mListRecycler.add(FilmCommentView(mContext, filmDetail))
        mListRecycler.add(FilmReviewView(mContext, filmDetail))
        //mListRecycler.add(FilmView(mContext))
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