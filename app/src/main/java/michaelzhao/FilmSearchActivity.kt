package michaelzhao

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import douban.DouBanV1
import douban.adapter.FilmBriefAdapter
import douban.adapter.FilmListAdapter
import douban.subview.FilmView
import org.jetbrains.anko.find
import util.Rx
import util.hide
import util.show


class FilmSearchActivity : BaseActivity(), FloatingSearchView.OnSearchListener, FloatingSearchView.OnQueryChangeListener {

    override val mLayout: Int = R.layout.activity_common_search
    private val mSearchView by lazy { find<FloatingSearchView>(R.id.floating_search_view) }
    private val mRecyclerView by lazy { find<RecyclerView>(R.id.mRecyclerView) }
    private val mNoneLayout by lazy { find<View>(R.id.layout_none) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNoneLayout()
        mSwipeLayout.setColorSchemeColors(getPrimaryColor())
        mSwipeLayout.DisEnable()
        mSearchView.setSearchFocused(true)
        mSearchView.setOnQueryChangeListener(this)
        mSearchView.setOnSearchListener(this)
        mSearchView.setOnHomeActionClickListener { finish() }
    }

    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) = Unit

    //Search Brief
    override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
        if (newQuery == null || newQuery.isBlank()) {
            mRecyclerView.adapter = null
        } else {
            val filmView = FilmView(this)
            Rx.get {
                DouBanV1.getSearchBrief(newQuery)
            }.set {
                if (newQuery == mSearchView.query) {
                    mRecyclerView.layoutManager = GridLayoutManager(this, 3)
                    mRecyclerView.adapter = FilmBriefAdapter(mRecyclerView,it, this, filmView)
                }
            }.err {
                filmView.showErrMsg(it, mRecyclerView)
            }
        }
    }

    override fun onSearchAction(currentQuery: String?) {
        val query = currentQuery.toString()
        if (query.isBlank()) {
            mRecyclerView.adapter = null
        } else {
            mSwipeLayout.ShowRefresh()
            val mStep = 30
            var mCurrPageIndex = 0
            var mAdapter: FilmListAdapter?
            val mFilmView = FilmView(this)
            Rx.get {
                DouBanV1.getSearchFilmList(query, 0, mStep)
            }.set {
                if (it.subjects.isNotEmpty()) {
                    mNoneLayout.hide()
                    mRecyclerView.layoutManager = GridLayoutManager(this, 1)
                    mAdapter = FilmListAdapter(this, it.subjects, mFilmView)
                    mRecyclerView.adapter = mAdapter
                    mFilmView.apply {
                        setLoadMore {
                            mSwipeLayout.ShowRefresh()
                            Rx.get {
                                mCurrPageIndex += mStep
                                DouBanV1.getSearchFilmList(query, mCurrPageIndex, mStep)
                            }.set {
                                val cnt = mAdapter?.itemCount
                                if (cnt != null && it.subjects.isNotEmpty()) {
                                    mAdapter?.addFilmList(it.subjects)
                                    mAdapter?.notifyItemInserted(cnt)
                                } else {
                                    mCurrPageIndex -= mStep
                                    showNoMoreMsg(mRecyclerView)
                                }
                            }.end {
                                mSwipeLayout.DisEnable()
                                mSwipeLayout.HideRefresh()
                                mAdapter?.loadMoreFinish()
                            }.err {
                                showErrMsg(it)
                            }
                        }
                    }
                } else {
                    mNoneLayout.show()
                }
            }.end {
                mSwipeLayout.DisEnable()
                mSwipeLayout.HideRefresh()
            }.err {
                mFilmView.showErrMsg(it)
            }
        }
    }

}
