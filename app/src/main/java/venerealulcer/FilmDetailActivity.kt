package venerealulcer

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette.Profile.MUTED
import com.github.florent37.glidepalette.GlidePalette
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.Douban
import douban.FilmDetail
import douban.FilmDetailAdapter
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
    private val mPosterLayout by lazy { find<View>(R.id.layout_poster) }

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
        mCollapseLayout.setExpandedTitleColor(getColorValue(R.color.transparent))
        mCollapseLayout.expandedTitleGravity = Gravity.TOP or Gravity.START
        mCollapseLayout.setExpandedTitleTextAppearance(R.style.collapsing_toolbarTitle)
        mCollapseLayout.setExpandedTitleMargin(75.dip2px(), (-20).dip2px(), 0, 0)
        mTableLayout.setupWithViewPager(mViewPager)
        mTableLayout.tabMode = TabLayout.MODE_FIXED
        mTableLayout.setSelectedTabIndicatorColor(getPrimaryColor())
        mTableLayout.setTabTextColors(getColorValue(R.color.divider_color), getPrimaryColor())
        mTableLayout.setTabStyle()
        mViewPager.adapter = FilmDetailAdapter(this, null)
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
            Snackbar.make(mViewPager, "${it.message}", Snackbar.LENGTH_INDEFINITE).show()
        }.com {
            mSwipeLayout.isEnabled = false
        }
    }

    private fun updateFilmDetail(film: FilmDetail) {
        updatePosterImage(film.images.large, film.title)
        setToolBarTitle(film.title)
        mViewPager.adapter = FilmDetailAdapter(this, film)
    }

    private fun updatePosterImage(url: String, title: String) {
        Glide.with(this)
                .load(url)
                .listener(GlidePalette.with(url)
                        .use(MUTED).intoCallBack {
                            mPosterLayout.background = CreateRepeatDrawable(title, it?.mutedSwatch?.rgb!!, resources)
                        }
                        .crossfade(true))
                .into(mImageView)
    }

}