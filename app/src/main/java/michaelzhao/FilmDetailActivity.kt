package michaelzhao

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.DouBanV1
import douban.FilmDetail
import douban.FilmMan
import douban.adapter.FilmDetailAdapter
import douban.adapter.FilmManAdapter
import org.apache.commons.lang3.NotImplementedException
import org.jetbrains.anko.find
import util.Rx
import util.setTabStyle

class FilmDetailActivity : BaseActivity() {

    companion object {
        private var FILM_ID = ""
        private var mIsFilmDeatil = true
        fun ShowFilmDetail(id: String) {
            mIsFilmDeatil = true
            FILM_ID = id
            App.Instance.StartActivity(FilmDetailActivity::class.java)
        }

        fun ShowFilmMan(id: String) {
            mIsFilmDeatil = false
            FILM_ID = id
            App.Instance.StartActivity(FilmDetailActivity::class.java)
        }

    }

    override val mLayout: Int = R.layout.activity_filmdetail

    private val mViewPager by lazy { find<ViewPager>(R.id.mViewPager) }
    private val mTableLayout by lazy { find<TabLayout>(R.id.mTabLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()

        if (mIsFilmDeatil)
            refreshFilmDetail()
        else
            refreshFilmMan()
    }

    private fun initUI() {
        setToolBarIcon(GoogleMaterial.Icon.gmd_arrow_back)
        setToolBarTitle("Loading...")
        mToolBar.setNavigationOnClickListener { finish() }
        mSwipeLayout.setColorSchemeColors(getPrimaryColor())
        mTableLayout.setupWithViewPager(mViewPager)
        mTableLayout.tabMode = TabLayout.MODE_FIXED
        mTableLayout.setSelectedTabIndicatorColor(getPrimaryColor())
        mTableLayout.setTabTextColors(getColorValue(R.color.divider_color), getPrimaryColor())
        mTableLayout.setTabStyle()
        mViewPager.adapter = if (mIsFilmDeatil) FilmDetailAdapter(this, null) else FilmManAdapter(this, null)
    }

    private fun refreshFilmMan() {
        mSwipeLayout.ShowRefresh()
        Rx.get {
            DouBanV1.getFilmManInfo(FILM_ID)
        }.set {
            updateFilmMan(it)
        }.err {
            Snackbar.make(mViewPager, "${it.message}", Snackbar.LENGTH_INDEFINITE).show()
        }.end {
            mSwipeLayout.DisEnable()
        }
    }

    private fun updateFilmMan(film: FilmMan) {
        setToolBarTitle("影人：" + film.name)
        mViewPager.adapter = FilmManAdapter(this, film)
    }

    private fun refreshFilmDetail() {
        mSwipeLayout.ShowRefresh()
        Rx.get {
            DouBanV1.getFilmDetail(FILM_ID)
        }.set {
            updateFilmDetail(it)
        }.err {
            Snackbar.make(mViewPager, "${it.message}", Snackbar.LENGTH_INDEFINITE).show()
        }.end {
            mSwipeLayout.DisEnable()
        }
    }

    private fun updateFilmDetail(film: FilmDetail) {
        val type = when (film.subtype) {
            "tv" -> "电视剧"
            "movie" -> "电影"
            else -> throw NotImplementedException(film.subtype)
        }
        setToolBarTitle("$type：" + film.title)
        mViewPager.adapter = FilmDetailAdapter(this, film)
    }

}