package michaelzhao

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
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
        mTextClear.OnClick { clearHistory() }
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
                mSearchView.setSearchText(text)
            }
        })
    }

    private fun clearHistory() {
        mTagContainer.Hide()
        mTextNone.Show()
        mTagContainer.removeAllTags()
        HawkPut(R.string.preference_search_query, "")
    }

    private fun addSearchTag(query: String) {
        if (mTagContainer.tags.contains(query)) return
        if (mTagContainer.tags.size == 10) mTagContainer.removeTag(0)
        mTagContainer.addTag(query)
        mTagContainer.Show()
        mTextNone.Hide()
        HawkPut(R.string.preference_search_query, mTagContainer.tags.joinToString("\n"))
    }

    private fun showSearchTag() {
        val list = HawkGet(R.string.preference_search_query, "").split("\n").filter { it.isNotBlank() }
        mTagContainer.tags = list
        if (list.isNotEmpty()) {
            mTagContainer.Show()
            mTextNone.Hide()
        }
    }

    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) = Unit

    override fun onSearchAction(currentQuery: String?) {
        val query = currentQuery.toString()
        if (query.isBlank()) {
            mRecyclerView.adapter = null
        } else {
            addSearchTag(query)
            mSwipeLayout.Enable()
            mSwipeLayout.ShowRefresh()
            Rx.get {
                DouBanV1.getSearchFilmList(query)
            }.set {
                if (it.subjects.isNotEmpty()) {
                    find<View>(R.id.layout_none).Hide()
                    mRecyclerView.layoutManager = GridLayoutManager(this, 1)
                    mRecyclerView.FilmAdapter = it
                } else {
                    find<View>(R.id.layout_none).Show()
                }
            }.end {
                mSwipeLayout.DisEnable()
                mSwipeLayout.HideRefresh()
            }.err {
                Snackbar.make(mRecyclerView, "${it.message}", Snackbar.LENGTH_INDEFINITE).show()
            }
        }
    }

    override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
        if (newQuery == null || newQuery.isBlank()) {
            mRecyclerView.adapter = null
        } else {
            Rx.get {
                DouBanV1.getSearchBrief(newQuery)
            }.set {
                if (newQuery == mSearchView.query) {
                    mRecyclerView.layoutManager = GridLayoutManager(this, 2)
                    mRecyclerView.BriefAdapter = it
                }
            }.err {
                Snackbar.make(mRecyclerView, "${it.message}", Snackbar.LENGTH_INDEFINITE).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Util.HideKeyBoard(this)
    }

}
