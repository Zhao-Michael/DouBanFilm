package michaelzhao

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.github.florent37.picassopalette.PicassoPalette
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.squareup.picasso.Picasso
import douban.DouBanV1
import douban.FilmDetail
import douban.FilmMan
import douban.adapter.FilmDetailAdapter
import douban.adapter.FilmManAdapter
import org.jetbrains.anko.*
import util.*

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

    private val mCollapseLayout by lazy { find<CollapsingToolbarLayout>(R.id.collapse_layout) }
    private val mImageView by lazy { find<ImageView>(R.id.img_activity_info) }
    private val mViewPager by lazy { find<ViewPager>(R.id.mViewPager) }
    private val mTableLayout by lazy { find<TabLayout>(R.id.mTabLayout) }
    private val mPosterBackground by lazy { find<ImageView>(R.id.image_poster_bg) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()

        if (mIsFilmDeatil)
            refreshFilmDetail()
        else
            refreshFilmMan()
        uiThread(100) {
            println(find<View>(R.id.appbar_layout).measuredHeight)
        }
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
        updatePosterImage(film.avatars.large, film.name)
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
        updatePosterImage(film.images.large, film.title)
        setToolBarTitle("电影：" + film.title)
        mViewPager.adapter = FilmDetailAdapter(this, film)
    }

    private fun updatePosterImage(url: String, name: String) {
        Picasso.get()
                .load(url)
                .into(mImageView, PicassoPalette
                        .with(url, mImageView)
                        .use(PicassoPalette.Profile.MUTED)
                        .intoCallBack {
                            val drawable = Util.CreateRepeatDrawable(name, it?.mutedSwatch?.rgb!!, resources)
                            mPosterBackground.image = drawable
                        }
                )

    }

}