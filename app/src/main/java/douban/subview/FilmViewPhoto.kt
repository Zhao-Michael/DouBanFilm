package douban.subview

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.widget.TextView
import douban.FilmDetail
import douban.adapter.FilmPhotosAdapter
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import util.OnClick
import venerealulcer.BaseActivity
import venerealulcer.R

class FilmViewPhoto(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_photo_layout
    private val mFilmDetail = filmDetail
    private val mTextNormal by lazy { mView.find<TextView>(R.id.text_normal) }
    private val mTextMore by lazy { mView.find<TextView>(R.id.text_more) }
    private var mIsNormalMode = false
    private val mGrayColor by lazy { ContextCompat.getColor(mContext, R.color.dark_gray) }

    init {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(mContext, 3)
        initSwitchBtn()
    }

    private fun initSwitchBtn() {
        switchState(true)
        mTextNormal.OnClick { switchState(true) }
        mTextMore.OnClick { switchState(false) }
    }

    private fun switchState(isNormal: Boolean) {
        if (isNormal == mIsNormalMode) return
        mIsNormalMode = isNormal
        if (isNormal) {
            mTextNormal.textColor = BaseActivity.getPrimaryColor()
            mTextMore.textColor = mGrayColor
            initRecyclerView(mFilmDetail)
        } else {
            mTextNormal.textColor = mGrayColor
            mTextMore.textColor = BaseActivity.getPrimaryColor()
            initRecyclerView(mFilmDetail)
        }
    }

    private fun initRecyclerView(film: FilmDetail) {
        mRecyclerView.adapter = FilmPhotosAdapter(mContext, film)
    }

}