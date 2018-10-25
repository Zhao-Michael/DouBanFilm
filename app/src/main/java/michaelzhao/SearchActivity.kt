package michaelzhao

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import co.lujun.androidtagview.TagContainerLayout
import co.lujun.androidtagview.TagView
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import douban.DouBanV1
import douban.adapter.FilmBriefAdapter
import douban.adapter.FilmListAdapter
import douban.subview.FilmView
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import util.*


class SearchActivity : BaseActivity(), FloatingSearchView.OnSearchListener, FloatingSearchView.OnQueryChangeListener {

    override val mLayout: Int = R.layout.activity_search
    private val mSearchView by lazy { find<FloatingSearchView>(R.id.floating_search_view) }
    private val mRecyclerView by lazy { find<RecyclerView>(R.id.mRecyclerView) }
    private val mTextClear by lazy { find<TextView>(R.id.text_clear) }
    private val mTagContainer by lazy { find<TagContainerLayout>(R.id.layout_tagcontainer) }
    private val mTextNone by lazy { find<TextView>(R.id.text_none) }
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
        mTextClear.textColor = getPrimaryColor()
        mTextClear.onClick { clearHistory() }
        initTagContainer()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTagContainer() {
        showSearchTag()
        mTagContainer.setOnTouchListener { _: View, motionEvent: MotionEvent ->
            mTagContainer.onTouchEvent(motionEvent)
            return@setOnTouchListener false
        }
        mTagContainer.setOnTagClickListener(object : TagView.OnTagClickListener {
            override fun onTagLongClick(position: Int, text: String?) = Unit

            override fun onTagCrossClick(position: Int) = Unit

            override fun onTagClick(position: Int, text: String?) {
                mSearchView.setSearchFocused(true)
                mSearchView.setSearchText("")
                mSearchView.setSearchText(text)
            }
        })
    }

    private fun clearHistory() {
        mTagContainer.hide()
        mTextNone.show()
        mTagContainer.removeAllTags()
        HawkPut(R.string.preference_search_query, "")
    }

    private fun addSearchTag(query: String) {
        if (mTagContainer.tags.contains(query)) return
        if (mTagContainer.tags.size == 10) mTagContainer.removeTag(0)
        mTagContainer.addTag(query)
        mTagContainer.show()
        mTextNone.hide()
        HawkPut(R.string.preference_search_query, mTagContainer.tags.joinToString("\n"))
    }

    private fun showSearchTag() {
        val list = HawkGet(R.string.preference_search_query, "").split("\n").filter { it.isNotBlank() }
        mTagContainer.tags = list
        if (list.isNotEmpty()) {
            mTagContainer.show()
            mTextNone.hide()
        }
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
                    mRecyclerView.layoutManager = GridLayoutManager(this, 2)
                    mRecyclerView.adapter = FilmBriefAdapter(it, this, filmView)
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
            addSearchTag(query)
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


    override fun onPause() {
        super.onPause()
        Util.HideKeyBoard(this)
    }

}
