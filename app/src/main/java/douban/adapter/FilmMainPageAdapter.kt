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
import michaelzhao.R
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import util.FilmAdapter
import util.Rx
import util.VerSwipeLayout
import util.inflate

//电影列表 切换
class FilmMainPageAdapter(context: Context) : PagerAdapter() {

    private val mContext = context
    private val mListRecycler = mutableListOf<RecyclerView>()
    private val mListSwipeLayout = mutableListOf<VerSwipeLayout>()
    private val mListTitle = mutableListOf<String>()

    init {
        mListTitle.add("首页")
        mListTitle.add("院线电影")
        mListTitle.add("即将上映")
        mListTitle.add("周榜")
        mListTitle.add("新片榜")
        mListTitle.add("北美榜")
        mListTitle.add("Top 250")
        mListTitle.forEach {
            mListSwipeLayout.add(VerSwipeLayout(mContext, null))
            mListRecycler.add(RecyclerView(mContext))
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = mContext.inflate(R.layout.main_page_item_layout, container)
        mListSwipeLayout[position] = view.find<VerSwipeLayout>(R.id.mSwipeLayout).apply {
            setOnRefreshListener { updatePageFromNet(position) }
        }
        val recycler = view.find<RecyclerView>(R.id.mRecyclerView)
        recycler.layoutManager = GridLayoutManager(mContext, 1)
        mListRecycler[position] = recycler
        if (recycler.adapter == null) {
            if (position == 0) {
                mListSwipeLayout[0].DisEnable()
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
        container.addView(view)
        return view
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

    private fun updatePageFromNet(pos: Int) {
        if (pos == 0) {
            mListSwipeLayout[pos].DisEnable()
            mListRecycler[pos].adapter = FilmHomeAdapter(mListRecycler[pos])
        } else {
            Rx.get {
                mListSwipeLayout[pos].ShowRefresh()
                return@get when (pos) {
                    1 -> DouBanV1.getTheaterFilms("上海")
                    2 -> DouBanV1.getComingFilm()
                    3 -> DouBanV1.getWeeklyRank().convetToFilmList()
                    4 -> DouBanV1.getNewFilmRank()
                    5 -> DouBanV1.getUSFilmRank().convetToFilmList()
                    6 -> DouBanV1.getTop250Film()
                    else -> throw NotImplementedError("updatePageFromNet : index out of range")
                }
            }.set {
                Hawk.put(mListTitle[pos], it)
                mListRecycler[pos].FilmAdapter = it
            }.err {
                mContext.toast(it.message.toString())
            }.end {
                mListSwipeLayout[pos].HideRefresh()
            }
        }
    }

}