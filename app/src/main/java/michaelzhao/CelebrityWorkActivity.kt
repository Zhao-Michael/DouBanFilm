package michaelzhao

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.orhanobut.hawk.Hawk
import douban.DouBanV2
import douban.adapter.FilmListAdapter
import douban.subview.FilmView
import org.jetbrains.anko.find
import util.Rx
import util.hide
import util.show

class CelebrityWorkActivity : BaseActivity() {

    companion object {
        private val CELE_ID_NAME = CelebrityWorkActivity::javaClass.name + "_CelebrityID"
        private val SORTTYPE_NAME = CelebrityWorkActivity::javaClass.name + "_SortType"
        private val TITLE_NAME = CelebrityWorkActivity::javaClass.name + "_Title"
        private val mCelebrityID get() = Hawk.get<String>(CELE_ID_NAME)
        private var mIsSortByTime
            get() = Hawk.get<Boolean>(SORTTYPE_NAME)
            set(value) {
                Hawk.put(SORTTYPE_NAME, value)
            }

        private var mTitle
            get() = Hawk.get<String>(TITLE_NAME)
            set(value) {
                Hawk.put(TITLE_NAME, value)
            }

        fun showCelebrityWorkList(tag: String, isSortByTime: Boolean, title: String) {
            Hawk.put(CELE_ID_NAME, tag)
            mIsSortByTime = isSortByTime
            mTitle = title
            App.Instance.startActivity(CelebrityWorkActivity::class.java)
        }
    }

    override val mLayout: Int = R.layout.activity_celebritywork

    private val mRecyclerView by lazy { find<RecyclerView>(R.id.mRecyclerView) }
    private val mEmptyLayout by lazy { find<View>(R.id.layout_none) }
    private var mFilmAdapter: FilmListAdapter? = null
    private val mFilmView = FilmView(this, 11)
    private val mStep = 10
    private var mCurrPageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNoneLayout()
        setToolBarIcon(GoogleMaterial.Icon.gmd_arrow_back)
        mToolBar.setNavigationOnClickListener { finish() }
        setToolBarTitle(mTitle + if (mIsSortByTime) " - 按时间排序" else " - 按评分排序")
        mRecyclerView.layoutManager = GridLayoutManager(this, 1)
        setLoadMore()
        updateList(mCelebrityID, 0, true)
    }

    private fun updateList(celeID: String, index: Int, isNew: Boolean = false) {
        mSwipeLayout.ShowRefresh()
        Rx.get {
            DouBanV2.getCelebrityWorkList(celeID, mIsSortByTime, index, mStep)
        }.set {
            if (it.subjects.isNotEmpty()) {
                mEmptyLayout.hide()
                if (mFilmAdapter == null || isNew) {
                    mFilmAdapter = FilmListAdapter(mRecyclerView.context, it.subjects, mFilmView)
                    mFilmAdapter?.enableLoadMore()
                    mRecyclerView.adapter = mFilmAdapter
                } else {
                    val adapter = mFilmAdapter!!
                    val cnt = adapter.itemCount
                    val temp = it.subjects.subtract(adapter.getAllFilmList())
                    if (temp.isEmpty()) {
                        adapter.disableLoadMore()
                    } else {
                        adapter.addFilmList(temp)
                        adapter.notifyItemInserted(cnt)
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
            mFilmAdapter?.loadMoreFinish()
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
            updateList(mCelebrityID, mCurrPageIndex)
        }
    }

}