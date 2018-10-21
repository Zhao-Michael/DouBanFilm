package douban.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.orhanobut.hawk.Hawk
import douban.DouBanV1
import douban.FilmList
import org.jetbrains.anko.toast
import util.FilmAdapter
import util.Rx
import util.VerticalSwipeRefreshLayout

//电影列表 切换
class FilmMainPageAdapter(context: Context, swipeLayout: VerticalSwipeRefreshLayout) : PagerAdapter() {

    private val mContext = context
    private val mListRecycler = mutableListOf<RecyclerView>()
    private val mListTitle = mutableListOf<String>()
    private val mSwipeLayout = swipeLayout

    init {
        mListTitle.add("首页")
        mListTitle.add("院线电影")
        mListTitle.add("即将上映")
        mListTitle.add("周榜")
        mListTitle.add("新片榜")
        mListTitle.add("北美榜")
        mListTitle.add("Top 250")

        mListTitle.forEach {
            val view = RecyclerView(mContext)
            view.layoutManager = GridLayoutManager(mContext, 1)
            mListRecycler.add(view)
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val recycler = mListRecycler[position]
        if (recycler.adapter == null) {
            if (position == 0) {
                mSwipeLayout.DisEnable()
                recycler.adapter = FilmHomeAdapter(recycler)
            } else {
                val list = Hawk.get<Any?>(mListTitle[position])
                if (list is FilmList) {
                    recycler.FilmAdapter = list
                } else {
                    updatePageFromNet(position)
                }
            }
        }
        container.addView(recycler)
        return recycler
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === p1
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mListTitle[position]
    }

    override fun getCount(): Int {
        return mListTitle.size
    }

    fun updatePageFromNet(position: Int) {
        if (position == 0) {
            mSwipeLayout.DisEnable()
            mListRecycler[0].adapter = FilmHomeAdapter(mListRecycler[0])
            return
        }
        Rx.get {
            mSwipeLayout.Enable()
            mSwipeLayout.ShowRefresh()
            return@get when (position) {
                0 -> DouBanV1.getTheaterFilms("上海")
                1 -> DouBanV1.getComingFilm()
                2 -> DouBanV1.getWeeklyRank().convetToFilmList()
                3 -> DouBanV1.getNewFilmRank()
                4 -> DouBanV1.getUSFilmRank().convetToFilmList()
                5 -> DouBanV1.getTop250Film()
                else -> throw NotImplementedError()
            }
        }.set {
            Hawk.put(mListTitle[position], it)
            mListRecycler[position].FilmAdapter = it
        }.err {
            mContext.toast(it.message.toString())
        }.end {
            mSwipeLayout.HideRefresh()
        }
    }

}