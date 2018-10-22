package michaelzhao

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.DouBanV2
import douban.adapter.FilmTagAdapter
import douban.subview.FilmView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import util.Rx
import util.Show

class TagFilmActivity : BaseActivity() {

    companion object {
        private var mTag = ""
        fun ShowTagFilmList(tag: String) {
            mTag = tag
            App.Instance.StartActivity(TagFilmActivity::class.java)
        }
    }

    override val mLayout: Int = R.layout.activity_tagfilm

    private val mRecyclerView by lazy { find<RecyclerView>(R.id.mRecyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNoneLayout()
        setToolBarIcon(GoogleMaterial.Icon.gmd_arrow_back)
        mToolBar.setNavigationOnClickListener { finish() }
        setToolBarTitle("分类：$mTag")
        mRecyclerView.layoutManager = GridLayoutManager(this, 3)
        mSwipeLayout.onRefresh { updateList() }
        updateList()
    }

    private fun updateList() {
        mSwipeLayout.ShowRefresh()
        Rx.get {
            DouBanV2.getTagFilm(mTag)
        }.set {
            if (it.subjects.isNotEmpty())
                mRecyclerView.adapter = FilmTagAdapter(mRecyclerView, it, FilmView(this))
            else {
                find<View>(R.id.layout_none).Show()
            }
        }.end {
            mSwipeLayout.HideRefresh()
        }.err {
            find<View>(R.id.layout_none).Show()
            Snackbar.make(mRecyclerView, "${it.message}", Snackbar.LENGTH_INDEFINITE).show()
            it.printStackTrace()
        }
    }

}