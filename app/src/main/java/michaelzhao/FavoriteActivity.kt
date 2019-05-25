package michaelzhao

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.arlib.floatingsearchview.FloatingSearchView
import database.FavoriteDB
import douban.FilmDetail
import douban.adapter.FilmListAdapter
import douban.subview.FilmView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import util.Util

class FavoriteActivity : BaseActivity(), FloatingSearchView.OnQueryChangeListener {

    override val mLayout: Int = R.layout.activity_common_search
    private val mRecyclerView by lazy { find<RecyclerView>(R.id.mRecyclerView) }
    private val mNoneLayout by lazy { find<View>(R.id.layout_none) }
    private val mSearchView by lazy { find<FloatingSearchView>(R.id.floating_search_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNoneLayout()
        mSwipeLayout.setColorSchemeColors(getPrimaryColor())
        mSwipeLayout.setOnRefreshListener {
            updateFavoriteList(mAllFilm)
            mSwipeLayout.HideRefresh()
        }
        mSearchView.setOnQueryChangeListener(this)
        mSearchView.setOnHomeActionClickListener { finish() }
        updateFavoriteList(mAllFilm)
    }

    private val mAllFilm get() = FavoriteDB.Instance.getAllFilmDetail()

    private fun updateFavoriteList(list: List<FilmDetail>) {
        val context = mRecyclerView.context
        mRecyclerView.layoutManager = GridLayoutManager(this, 1)
        mRecyclerView.adapter = FilmListAdapter(context, FilmView(context), list)
        mNoneLayout.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
        if (newQuery == null || newQuery.isEmpty()) {
            updateFavoriteList(mAllFilm)
            Util.HideKeyBoard(this)
        } else {
            updateFavoriteList(mAllFilm.filter {
                it.title.contains(newQuery) ||
                        it.casts.any { it.name.contains(newQuery) } ||
                        it.directors.any { it.name.contains(newQuery) }
            })
        }
    }


}