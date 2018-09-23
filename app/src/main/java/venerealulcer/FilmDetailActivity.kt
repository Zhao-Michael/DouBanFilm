package venerealulcer

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.widget.ImageView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.Douban
import douban.FilmDetail
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find
import util.*

class FilmDetailActivity : BaseActivity() {

    companion object {
        private var FILM_ID = ""
        fun ShowFilmDetail(id: String) {
            FILM_ID = id
            App.Instance.startActivity(Intent(App.Instance, FilmDetailActivity::class.java))
        }
    }

    override val mLayout: Int = R.layout.activity_filmdetail

    private val mCollapseLayout by lazy { find<CollapsingToolbarLayout>(R.id.collapse_layout) }
    private val mImageView by lazy { find<ImageView>(R.id.img_activity_info) }
    private val mViewPager by lazy { find<ViewPager>(R.id.mViewPager) }
    private val mTableLayout by lazy { find<TabLayout>(R.id.mTabLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()

        refreshFilmDetail()
    }

    private fun initUI() {
        setToolBarIcon(GoogleMaterial.Icon.gmd_arrow_back)
        setToolBarTitle()
        mToolBar.setNavigationOnClickListener { finish() }
        window.statusBarColor = getColorValue(R.color.transparent)
        mToolBar.backgroundColor = getColorValue(R.color.transparent)
        mSwipeLayout.setColorSchemeColors(getPrimaryColor())
        mCollapseLayout.setContentScrimColor(getPrimaryColor())
        mCollapseLayout.setStatusBarScrimColor(getPrimaryColor())
        mCollapseLayout.setCollapsedTitleTextColor(getColorValue(R.color.accent_white))
        mCollapseLayout.setExpandedTitleColor(getColorValue(R.color.accent_white))
        mTableLayout.setupWithViewPager(mViewPager)
        mTableLayout.tabMode = TabLayout.MODE_FIXED
        mTableLayout.setSelectedTabIndicatorColor(getPrimaryColor())
        mTableLayout.setTabTextColors(getColorValue(R.color.divider_color), getPrimaryColor())
        mTableLayout.setTabStyle()
        mViewPager.adapter = FilmDetailAdapter(this, null) { showRefresh(it) }
    }

    private fun showRefresh(b: Boolean) {
        if (b) {
            mSwipeLayout.ShowRefresh()
        } else {
            mSwipeLayout.HideRefresh()
        }
    }

    private fun refreshFilmDetail() {
        showRefresh(true)
        Rx.get {
            Douban.getFilmDetail(FILM_ID)
        }.set {
            updateFilmDetail(it)
        }.err {
            uiThread { Snackbar.make(mCollapseLayout, "${it.message}", Snackbar.LENGTH_INDEFINITE) }
        }.com {
            showRefresh(false)
        }
    }

    private fun updateFilmDetail(film: FilmDetail) {
        mImageView.setImageUrl(film.images.large)
        setToolBarTitle(film.title)
        mViewPager.adapter = FilmDetailAdapter(this, null) { showRefresh(it) }
    }

}