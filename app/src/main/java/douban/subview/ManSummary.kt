package douban.subview

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import douban.DouBanV1
import michaelzhao.R
import org.jetbrains.anko.find
import util.*

class ManSummary(context: Context, filmMan: DouBanV1.CelebrityDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_man_brief_layout
    private val mFilmMan = filmMan
    private val mImageView by lazy { mView.find<ImageView>(R.id.image) }
    private val mTextBrief by lazy { mView.find<TextView>(R.id.mTextBrief) }
    private val brief_cardview by lazy { mView.find<CardView>(R.id.brief_cardview) }
    private val mHeaderLayout by lazy { mView.find<LinearLayout>(R.id.header_layout) }
    private val mImage_layout by lazy { mView.find<LinearLayout>(R.id.image_layout) }

    init {
        initSummary()
        initImageList()
        initBrief()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initBrief() {
        setBrief()
        brief_cardview.onClick { setBrief() }
        mTextBrief.setOnTouchListener { _: View, motionEvent: MotionEvent ->
            brief_cardview.onTouchEvent(motionEvent)
            return@setOnTouchListener false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setBrief() {
        val maxLen = 140
        val len = mTextBrief.text.toString().length
        if (len == 0)
            mTextBrief.text = mFilmMan.summary.take(maxLen) + "..."
        else if (len < maxLen + 4)
            mTextBrief.text = mFilmMan.summary
        else
            mTextBrief.text = mFilmMan.summary.take(maxLen) + "..."
    }

    private fun initSummary() {
        mImageView.setImageUrl(mFilmMan.thumb)
        addInfo(mFilmMan.gender)
        addInfo(mFilmMan.constellation)
        addInfo(mFilmMan.born_date)
        addInfo(mFilmMan.born_place)
        addInfo(mFilmMan.occupation)
        addInfo(mFilmMan.name, true)
        addInfo(mFilmMan.name_en, true)
        addInfo(mFilmMan.family, true)
    }

    private fun addInfo(txt: String, more: Boolean = false) {
        var text = txt

        if (text.isBlank())
            return

        if (more) {
            if (text.contains("/")) {
                text = text.replace("/", "\r\n\t\t")
            }

            if (text.contains(":")) {
                text = text.replace(":", ":\r\n\t\t")
            }
        }

        val textView = TextView(mContext)
        textView.text = text
        textView.textSize = 12f
        mHeaderLayout.addView(textView)
    }

    private fun initImageList() {
        mFilmMan.photos.forEach {
            val image = ImageView(mContext)
            image.scaleType = ImageView.ScaleType.CENTER_CROP
            image.setImageUrl(it)
            image.setPadding(10, 10, 10, 10)
            mImage_layout.addView(image)
            image.setHeight(140.dip2px())
            image.setWidth(140.dip2px())
        }
    }

}