package michaelzhao

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.FrameLayout
import com.github.ivbaranov.mfb.MaterialFavoriteButton
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.orhanobut.hawk.Hawk
import database.FavoriteDB
import douban.DouBanV1
import douban.FilmDetail
import douban.FilmMan
import douban.adapter.FilmDetailAdapter
import douban.adapter.FilmManAdapter
import org.apache.commons.lang3.NotImplementedException
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find
import util.*

class FilmDetailActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    companion object {
        private val THE_ID = FilmDetailActivity::javaClass.name + "_THE_ID"
        private val mSpecifiedID get() = Hawk.get(THE_ID, "")

        private val IS_FILM = FilmDetailActivity::javaClass.name + "_IS_FILM"
        private val mIsFilmDeatil get() = Hawk.get(IS_FILM, true)

        fun showFilmDetail(id: String) {
            Hawk.put(THE_ID, id)
            Hawk.put(IS_FILM, true)
            App.Instance.startActivity(FilmDetailActivity::class.java)
        }

        fun showFilmMan(id: String) {
            Hawk.put(IS_FILM, false)
            Hawk.put(THE_ID, id)
            App.Instance.startActivity(FilmDetailActivity::class.java)
        }

    }

    inner class CornerCallBack {
        var mTabSelected: (Int) -> Unit = { _ -> }
        var mCornerClick: () -> Unit = {}
        fun resetCorner() = resetFavoriteButton()
    }

    override val mLayout: Int = R.layout.activity_filmdetail

    private val mViewPager by lazy { find<ViewPager>(R.id.mViewPager) }
    private val mTableLayout by lazy { find<TabLayout>(R.id.mTabLayout) }
    private val mImageFavorite by lazy { find<MaterialFavoriteButton>(R.id.image_favorite) }
    private val mLayoutFavorite by lazy { find<FrameLayout>(R.id.layout_favorite) }
    private var mCurrentFilm: FilmDetail? = null
    private val mCornerCallBack = CornerCallBack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()

        if (mIsFilmDeatil)
            refreshFilmDetail()
        else
            refreshFilmMan()
    }

    private fun initUI() {
        mLayoutFavorite.backgroundColor = getPrimaryColor()
        setToolBarIcon(GoogleMaterial.Icon.gmd_arrow_back)
        setToolBarTitle("Loading...")
        mToolBar.setNavigationOnClickListener { finish() }
        mSwipeLayout.setColorSchemeColors(getPrimaryColor())
        mTableLayout.setupWithViewPager(mViewPager)
        mViewPager.addOnPageChangeListener(this)
        mTableLayout.tabMode = TabLayout.MODE_FIXED
        mTableLayout.setSelectedTabIndicatorColor(getPrimaryColor())
        mTableLayout.setTabTextColors(getColorValue(R.color.divider_color), getPrimaryColor())
        mTableLayout.setTabStyle()
        mViewPager.adapter = if (mIsFilmDeatil) FilmDetailAdapter(this, null) else FilmManAdapter(this, null)
    }

    private fun refreshFilmMan() {
        mSwipeLayout.ShowRefresh()
        Rx.get {
            DouBanV1.getFilmManInfo(mSpecifiedID)
        }.set {
            mCurrentFilm = null
            updateFilmMan(it)
        }.err {
            Snackbar.make(mViewPager, "${it.message}", Snackbar.LENGTH_INDEFINITE).show()
        }.end {
            mSwipeLayout.DisEnable()
        }
    }

    private fun updateFilmMan(film: DouBanV1.CelebrityDetail) {
        setToolBarTitle("影人：" + film.short_name)
        mViewPager.adapter = FilmManAdapter(this, film)
        mImageFavorite.hide()
    }

    private fun refreshFilmDetail() {
        mSwipeLayout.ShowRefresh()
        Rx.get {
            DouBanV1.getFilmDetail(mSpecifiedID)
        }.set {
            mCurrentFilm = it
            updateFilmDetail(it)
        }.err {
            Snackbar.make(mViewPager, "${it.message}", Snackbar.LENGTH_INDEFINITE).show()
        }.end {
            mSwipeLayout.DisEnable()
        }
    }

    private fun updateFilmDetail(film: FilmDetail) {
        mImageFavorite.show()
        val type = when (film.subtype) {
            "tv" -> "电视剧"
            "movie" -> "电影"
            else -> throw NotImplementedException(film.subtype)
        }
        setToolBarTitle("$type：" + film.title)
        mViewPager.adapter = FilmDetailAdapter(this, film)
    }

    fun resetFavoriteButton() {
        if (mCurrentFilm == null) {
            mImageFavorite.hide()
        } else {
            mImageFavorite.show()
            val film = mCurrentFilm!!
            val db = FavoriteDB.Instance
            mImageFavorite.isFavorite = db.existFilmDetail(film.id)
            mImageFavorite.onClick {
                val result = if (db.existFilmDetail(film.id))
                    db.removeFilmDetail(film)
                else
                    db.addFilmDetail(film)
                if (result) {
                    mImageFavorite.isFavorite = !mImageFavorite.isFavorite
                }
            }
        }
    }

    override fun onPageScrollStateChanged(p0: Int) = Unit

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) = Unit

    override fun onPageSelected(p0: Int) {
        mCornerCallBack.mTabSelected(p0)
    }

}