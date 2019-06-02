package michaelzhao

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.orhanobut.hawk.Hawk
import douban.DouBanV2
import douban.adapter.FilmTagAdapter
import douban.subview.FilmView
import org.jetbrains.anko.find
import util.hide
import util.Rx
import util.show

class TagFilmActivity : BaseActivity(), FloatingSearchView.OnSearchListener {

    companion object {
        private val TAG_NAME = TagFilmActivity::javaClass.name + "_Tag"
        private val TYPE_NAME = TagFilmActivity::javaClass.name + "_Type"
        private val mTag get() = Hawk.get<String>(TAG_NAME)
        private val mType get() = Hawk.get<String>(TYPE_NAME)  //TV or Film
        fun showTagFilmList(tag: String, type: String) {
            Hawk.put(TAG_NAME, tag)
            Hawk.put(TYPE_NAME, type)
            App.Instance.startActivity(TagFilmActivity::class.java)
        }
    }

    override val mLayout: Int = R.layout.activity_common_search

    private val mRecyclerView by lazy { find<RecyclerView>(R.id.mRecyclerView) }
    private val mSearchView by lazy { find<FloatingSearchView>(R.id.floating_search_view) }
    private val mEmptyLayout by lazy { find<View>(R.id.layout_none) }
    private var mFilmTagAdapter: FilmTagAdapter? = null
    private val mFilmView = FilmView(this)
    private val mStep = 30
    private var mCurrPageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSearchView.setSearchText(mTag)
        initNoneLayout()
        setToolBarIcon(GoogleMaterial.Icon.gmd_arrow_back)
        mToolBar.setNavigationOnClickListener { finish() }
        setToolBarTitle("分类：$mTag")
        mSearchView.setOnSearchListener(this)
        mSearchView.setOnHomeActionClickListener { finish() }
        mRecyclerView.layoutManager = GridLayoutManager(this, 3)
        setLoadMore()
        updateList(mTag, 0, true)
    }

    private fun updateList(tag: String, index: Int, isNew: Boolean = false) {
        mSwipeLayout.ShowRefresh()
        Rx.get {
            if (mType == "movie")
                DouBanV2.getTagFilm(tag, index, mStep)
            else
                DouBanV2.getTagTV(tag, index, mStep)
        }.set {
            if (it.subjects.isNotEmpty()) {
                mEmptyLayout.hide()
                if (mFilmTagAdapter == null || isNew) {
                    mFilmTagAdapter = FilmTagAdapter(mRecyclerView, it, mFilmView)
                    mRecyclerView.adapter = mFilmTagAdapter
                } else {
                    val cnt = mFilmTagAdapter?.itemCount
                    if (cnt == null || cnt > 0) {
                        mFilmTagAdapter?.addTagList(it.subjects)
                        mFilmTagAdapter?.notifyItemInserted(cnt!!)
                    }
                }
            } else if (index == 0) {
                mRecyclerView.adapter = null
                mEmptyLayout.show()
            } else {
                mCurrPageIndex -= mStep
                mFilmView.showNoMoreMsg(mRecyclerView)
            }
        }.end {
            mSwipeLayout.HideRefresh()
            mSwipeLayout.DisEnable()
            mFilmTagAdapter?.loadMoreFinish()
        }.err {
            mRecyclerView.adapter = null
            mEmptyLayout.show()
            Snackbar.make(mRecyclerView, "${it.message}", Snackbar.LENGTH_INDEFINITE).show()
            it.printStackTrace()
        }
    }

    private fun setLoadMore() {
        mFilmView.setLoadMore {
            mCurrPageIndex += mStep
            updateList(mTag, mCurrPageIndex)
        }
    }

    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) = Unit

    override fun onSearchAction(currentQuery: String?) {
        if (currentQuery != null) {
            Hawk.put(TAG_NAME, currentQuery)
            updateList(currentQuery, 0, true)
        }
    }

}