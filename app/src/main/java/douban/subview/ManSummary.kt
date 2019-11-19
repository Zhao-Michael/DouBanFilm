package douban.subview

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import douban.CommonInfoList
import douban.DouBanV1
import douban.adapter.CommonInfoAdapter
import michaelzhao.BaseActivity
import michaelzhao.R
import org.jetbrains.anko.find
import util.*
import util.Util.HideParent

class ManSummary(context: Context, filmMan: DouBanV1.CelebrityDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_man_brief_layout
    private val mFilmMan = filmMan
    private val mImageView by lazy { mView.find<ImageView>(R.id.image) }
    private val mTextBrief by lazy { mView.find<TextView>(R.id.mTextBrief) }
    private val mManName_text by lazy { mView.find<TextView>(R.id.manname_text) }
    private val brief_cardview by lazy { mView.find<CardView>(R.id.brief_cardview) }
    private val mHeaderLayout by lazy { mView.find<LinearLayout>(R.id.header_layout) }
    private val mImage_layout by lazy { mView.find<LinearLayout>(R.id.image_layout) }
    private val mAward_layout by lazy { mView.find<LinearLayout>(R.id.award_layout) }
    private val recentwork_layout by lazy { mView.find<LinearLayout>(R.id.recentwork_layout) }
    private val topwork_layout by lazy { mView.find<LinearLayout>(R.id.topwork_layout) }
    private val workmate_layout by lazy { mView.find<LinearLayout>(R.id.workmate_layout) }

    val mImageWidth = BaseActivity.getScreenSize().x / 3 - 16.dip2px() - 1

    init {
        initSummary()
        initImageList()
        initBrief()
        initAward()
        initRecentWork()
        initTopWork()
        initWorkMate()
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
        if (mFilmMan.summary.isBlank()) {
            brief_cardview.hide()
            return
        }

        val maxLen = 140
        mTextBrief.textSize = 13f
        val len = mTextBrief.text.toString().length
        if (len == 0)
            mTextBrief.text = when {
                mFilmMan.summary.length - 3 < maxLen -> mFilmMan.summary
                else -> mFilmMan.summary.take(maxLen) + "..."
            }
        else if (len < maxLen + 4)
            mTextBrief.text = mFilmMan.summary
        else
            mTextBrief.text = mFilmMan.summary.take(maxLen) + "..."
    }

    private fun initSummary() {
        mManName_text.text = mFilmMan.short_name
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
        textView.textSize = 13f
        textView.setPadding(0, 3, 0, 3)
        mHeaderLayout.addView(textView)
    }

    private fun initImageList() {
        if (mFilmMan.photos.isEmpty())
            HideParent<CardView>(mImage_layout)

        mFilmMan.photos.forEach {
            val image = ImageView(mContext)
            image.scaleType = ImageView.ScaleType.CENTER_CROP
            image.setImageUrl(it, R.drawable.loading_large)
            image.setPadding(10, 10, 10, 10)
            mImage_layout.addView(image)
            image.setHeight(140.dip2px())
            image.setWidth(140.dip2px())
        }
    }

    private fun initAward() {
        if (mFilmMan.awards.isEmpty())
            HideParent<CardView>(mAward_layout)

        mFilmMan.awards.forEach {
            val textView = TextView(mContext)
            textView.text = it
            textView.setPadding(5.dip2px(), 5.dip2px(), 5.dip2px(), 5.dip2px())
            textView.textSize = 13f
            mAward_layout.addView(textView)
        }
    }

    private fun initRecentWork() {
        if (mFilmMan.recent_works.isEmpty())
            HideParent<CardView>(recentwork_layout)

        mFilmMan.recent_works.forEach {
            val view = mContext.inflate(R.layout.listitem_tag_film_cardview, recentwork_layout)
            CommonInfoAdapter.ViewHolder(view, mImageWidth).setTagItem(it.toCommonInfo())
            recentwork_layout.addView(view)
        }
    }

    private fun initTopWork() {
        if (mFilmMan.toprate_works.isEmpty())
            HideParent<CardView>(topwork_layout)

        mFilmMan.toprate_works.forEach {
            val view = mContext.inflate(R.layout.listitem_tag_film_cardview, topwork_layout)
            CommonInfoAdapter.ViewHolder(view, mImageWidth).setTagItem(it.toCommonInfo())
            topwork_layout.addView(view)
        }
    }

    private fun initWorkMate() {
        if (mFilmMan.workmates.isEmpty())
            HideParent<CardView>(workmate_layout)

        mFilmMan.workmates.forEach {
            val view = mContext.inflate(R.layout.listitem_tag_film_cardview, workmate_layout)
            CommonInfoAdapter.ViewHolder(view, mImageWidth).setTagItem(it.toCommonInfo())
            workmate_layout.addView(view)
        }
    }

}