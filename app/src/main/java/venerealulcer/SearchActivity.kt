package venerealulcer

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.Douban
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import util.*

class SearchActivity : BaseActivity(), SearchView.OnQueryTextListener {


    override val mLayout = R.layout.activity_search

    private val mSearchView by lazy { find<SearchView>(R.id.search_view) }
    private val mBackImageBtn by lazy { find<ImageButton>(R.id.back_imagebtn) }
    private val mRecyclerView by lazy { find<RecyclerView>(R.id.mRecyclerView) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }


    private fun initUI() {
        mSwipeLayout.setColorSchemeColors(getPrimaryColor())
        mSwipeLayout.DisEnable()
        mBackImageBtn.image = getDrawableIcon(GoogleMaterial.Icon.gmd_arrow_back)
        mBackImageBtn.OnClick { finish() }
        val txt_search = mSearchView.find<TextView>(R.id.search_src_text)
        txt_search.setTextColor(getColorValue(R.color.md_white_1000))
        mSearchView.isSubmitButtonEnabled = true
        mSearchView.setOnQueryTextListener(this)
        mSearchView.isFocusable = true
        mSearchView.setIconifiedByDefault(false)
        val searchIcon = mSearchView.findViewById(R.id.search_mag_icon) as ImageView
        searchIcon.setImageDrawable(null)
        mSearchView.setOnCloseListener {
            txt_search.text = ""
            return@setOnCloseListener true
        }
        mSearchView.requestFocusFromTouch()
        mRecyclerView.layoutManager = GridLayoutManager(this, 1)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        HideKeyBoard(this)
        if (query == null || query.isBlank()) {
            mRecyclerView.adapter = null
        } else {
            mSwipeLayout.Enable()
            mSwipeLayout.ShowRefresh()
            Rx.get {
                Douban.getSearchFilmList(query)
            }.set {
                mRecyclerView.FilmAdapter = it
                mSwipeLayout.DisEnable()
                mSwipeLayout.HideRefresh()
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null || newText.isBlank()) {
            mRecyclerView.adapter = null
        } else {
            Rx.get {
                Douban.getSearchBrief(newText)
            }.set {
                mRecyclerView.BriefAdapter = it
            }
        }
        return true
    }


}