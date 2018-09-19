package venerealulcer

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.widget.ImageButton
import android.widget.TextView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import org.jetbrains.anko.*
import util.OnClick


class SearchActivity : BaseActivity() {

    override val mLayout = R.layout.activity_search

    private val mSearchView by lazy { find<SearchView>(R.id.search_view) }
    private val mBackImageBtn by lazy { find<ImageButton>(R.id.back_imagebtn) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()

    }


    private fun initUI() {
        mBackImageBtn.image = getDrawableIcon(GoogleMaterial.Icon.gmd_arrow_back)
        mBackImageBtn.OnClick { finish() }
        val txt_search = mSearchView.find<TextView>(R.id.search_src_text)
        txt_search.setTextColor(getColorValue(R.color.md_white_1000))
        mSearchView.setOnCloseListener {
            txt_search.text = ""
            return@setOnCloseListener true
        }
        mSearchView.isFocusable = true
        mSearchView.isIconified = false
        mSearchView.requestFocusFromTouch()
    }


}