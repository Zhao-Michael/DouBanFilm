package douban.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.FilmDetail
import douban.FilmItem
import douban.Work
import douban.subview.IFilmView
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import michaelzhao.FilmDetailActivity
import michaelzhao.R
import org.jetbrains.anko.find
import util.*

//主页电影列表
class FilmListAdapter(context: Context, filmView: IFilmView) : IRecyclerViewAdapter<FilmListAdapter.ViewHolder>(filmView) {
    private val mContext = context
    private var mFilmList = mutableListOf<FilmItem>()
    private var mDetailList = mutableListOf<FilmDetail>()

    private val mIsFilmItem by lazy { mFilmList.isNotEmpty() }


    constructor(context: Context, filmList: List<FilmItem>, filmView: IFilmView) : this(context, filmView) {
        mFilmList.addAll(filmList)
    }

    constructor(context: Context, filmView: IFilmView, filmList: List<FilmDetail>) : this(context, filmView) {
        mDetailList.addAll(filmList)
    }


    fun addFilmList(list: List<FilmItem>) {
        mFilmList.addAll(list)
    }

    fun addWorkList(works: List<Work>) {
        mFilmList.addAll(works.map { it.subject })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mContext.inflate(R.layout.listitem_film_cardview, parent)
        return ViewHolder(view, mIsFilmItem)
    }

    override fun getItemCount(): Int {
        return if (mIsFilmItem) mFilmList.size else mDetailList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val pos = holder.adapterPosition
        if (mIsFilmItem) {
            val film = mFilmList[pos]
            holder.setFilmItem(film)
        } else {
            val film = mDetailList[pos]
            holder.setFilmDetail(film)
        }
        checkToEnd(pos)
    }

    class ViewHolder(mItemView: View, flag: Boolean = true) : RecyclerView.ViewHolder(mItemView) {
        private val mFlag = flag // For FavoriteActivity
        val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        val image by lazy { mItemView.find<ImageView>(R.id.image) }
        val title by lazy { mItemView.find<TextView>(R.id.title) }
        val year by lazy { mItemView.find<TextView>(R.id.year) }
        val actor by lazy { mItemView.find<TextView>(R.id.actor) }
        val director by lazy { mItemView.find<TextView>(R.id.director) }
        val rate by lazy { mItemView.find<TextView>(R.id.rate) }
        val ratingbar by lazy { mItemView.find<MaterialRatingBar>(R.id.ratingbar) }
        val page by lazy { mItemView.find<TextView>(R.id.page) }
        val genres by lazy { mItemView.find<TextView>(R.id.genres) }
        val org_name by lazy { mItemView.find<TextView>(R.id.org_name) }

        val switch_btn by lazy { mItemView.find<ImageView>(R.id.switch_btn) }

        val other_name by lazy { mItemView.find<TextView>(R.id.other_name) }
        val durations by lazy { mItemView.find<TextView>(R.id.durations) }
        val pubdate by lazy { mItemView.find<TextView>(R.id.pubdate) }
        val country by lazy { mItemView.find<TextView>(R.id.country) }
        val language by lazy { mItemView.find<TextView>(R.id.language) }
        val more_layout by lazy { mItemView.find<FrameLayout>(R.id.more_layout) }

        fun setFilmItem(film: FilmItem) {
            title.text = film.title.replaceEmpty()
            year.text = film.year.replaceEmpty()
            rate.text = film.rating.average.toString().replaceEmpty()
            genres.text = film.genres.joinToString("/")
            org_name.text = film.original_title.replaceEmpty()
            director.text = film.directors.joinToString("/") { it.name }.replaceEmpty()
            actor.text = film.casts.joinToString("/") { it.name }.replaceEmpty()
            ratingbar.max = 100
            ratingbar.progress = Math.round(film.rating.average.toFloat() * 10)
            val color = ColorStateList.valueOf(Color.rgb(0xFF, 0xBB, 0x33))
            ratingbar.supportProgressTintList = color
            if (film.rating.stars.toInt() == 0) {
                ratingbar.visibility = View.GONE
                rate.text = "暂无评分"
            }
            page.text = (adapterPosition + 1).toString()
            image.setImageUrl(film.images.small, R.drawable.loading_large)
            cardview.onClick { FilmDetailActivity.showFilmDetail(film.id) }
        }

        fun setFilmDetail(film: FilmDetail) {
            title.text = film.title.replaceEmpty()
            year.text = film.year.replaceEmpty()
            rate.text = film.rating.average.toString().replaceEmpty()
            genres.text = film.genres.joinToString("/").replaceEmpty()
            org_name.text = film.original_title.replaceEmpty()
            director.text = film.directors.joinToString("/") { it.name }.replaceEmpty()
            actor.text = film.casts.joinToString("/") { it.name }.replaceEmpty()
            ratingbar.max = 100
            ratingbar.progress = Math.round(film.rating.average.toFloat() * 10)
            val color = ColorStateList.valueOf(Color.rgb(0xFF, 0xBB, 0x33))
            ratingbar.supportProgressTintList = color
            if (film.rating.stars.toInt() == 0) {
                ratingbar.visibility = View.GONE
                rate.text = "暂无评分"
            }
            image.setImageUrl(film.images.small)
            switch_btn.visibility = View.VISIBLE
            switch_btn.tag = false // IsExpanded
            switch_btn.setIcon(GoogleMaterial.Icon.gmd_keyboard_arrow_down, Color.GRAY, 10)
            switch_btn.onClick { switch_BtnClick(film) }
            if (!mFlag) {
                page.text = (adapterPosition + 1).toString()
            }
            cardview.onClick {
                if (mFlag)
                    switch_btn.callOnClick()
                else
                    FilmDetailActivity.showFilmDetail(film.id)
            }
            other_name.text = film.aka.joinToString("\n").replaceEmpty()
            durations.text = film.durations.joinToString("/").replaceEmpty()
            pubdate.text = film.pubdates.joinToString("\n").replaceEmpty()
            country.text = film.countries.joinToString("/").replaceEmpty()
            language.text = film.languages.joinToString("/").replaceEmpty()
        }

        private fun switch_BtnClick(film: FilmDetail) {
            val isExpand = switch_btn.tag as Boolean
            if (isExpand) {// in collapsed
                switch_btn.setIcon(GoogleMaterial.Icon.gmd_keyboard_arrow_down, Color.GRAY, 10)
                actor.text = film.casts.joinToString("/") { it.name }.replaceEmpty()
                actor.setLines(1)
                title.setLines(1)
                org_name.setLines(1)
            } else { // in expand
                switch_btn.setIcon(GoogleMaterial.Icon.gmd_keyboard_arrow_up, Color.GRAY, 10)
                val too_long = film.casts.joinToString("") { it.name }.length > 15
                actor.text = film.casts.joinToString(if (too_long) "\n" else "/") { it.name }.replaceEmpty()
                actor.maxLines = film.casts.size
                title.maxLines = 10
                org_name.maxLines = 10
            }
            switch_btn.tag = !isExpand
            more_layout.visibility = if (isExpand) View.GONE else View.VISIBLE
        }

    }

}

