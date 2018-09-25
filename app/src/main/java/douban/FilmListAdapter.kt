package douban

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.hedgehog.ratingbar.RatingBar
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import util.OnClick
import util.inflate
import util.setIcon
import util.setImageUrl
import venerealulcer.FilmDetailActivity
import venerealulcer.R

//电影列表
class FilmListAdapter(listViews: FilmList, context: Context) : RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {
    private val mContext = context
    private var mFilmList: FilmList = listViews

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mContext.inflate(R.layout.listitem_film_layout, parent)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFilmList.subjects.size
    }

    fun getFilmList(): FilmList {
        return mFilmList
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val film = mFilmList.subjects[holder.adapterPosition]
        holder.setFilmItem(film)
    }

    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {

        val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        val image by lazy { mItemView.find<ImageView>(R.id.image) }
        val title by lazy { mItemView.find<TextView>(R.id.title) }
        val year by lazy { mItemView.find<TextView>(R.id.year) }
        val actor by lazy { mItemView.find<TextView>(R.id.actor) }
        val director by lazy { mItemView.find<TextView>(R.id.director) }
        val rate by lazy { mItemView.find<TextView>(R.id.rate) }
        val ratingbar by lazy { mItemView.find<RatingBar>(R.id.ratingbar) }
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
            title.text = film.title
            year.text = film.year
            rate.text = film.rating.average.toString()
            genres.text = film.genres.joinToString("/")
            org_name.text = film.original_title
            director.text = film.directors.joinToString("/") { it.name }
            actor.text = film.casts.joinToString("/") { it.name }
            ratingbar.setStar((film.rating.stars.toInt() / 10.0).toFloat())
            if (film.rating.stars.toInt() == 0) {
                ratingbar.visibility = View.GONE
                rate.text = "暂无评分"
            }
            page.text = (adapterPosition + 1).toString()
            image.setImageUrl(film.images.small)
            cardview.OnClick { FilmDetailActivity.ShowFilmDetail(film.id) }
        }


        fun setFilmDetail(film: FilmDetail) {
            title.text = film.title
            year.text = film.year
            rate.text = film.rating.average.toString()
            genres.text = film.genres.joinToString("/")
            org_name.text = film.original_title
            director.text = film.directors.joinToString("/") { it.name }
            actor.text = film.casts.joinToString("/") { it.name }
            ratingbar.setStar((film.rating.stars.toInt() / 10.0).toFloat())
            if (film.rating.stars.toInt() == 0) {
                ratingbar.visibility = View.GONE
                rate.text = "暂无评分"
            }
            image.setImageUrl(film.images.small)
            switch_btn.visibility = View.VISIBLE
            switch_btn.tag = false // IsExpanded
            switch_btn.setIcon(GoogleMaterial.Icon.gmd_keyboard_arrow_down, Color.GRAY, 10)
            switch_btn.OnClick {
                val isExpand = switch_btn.tag as Boolean
                if (isExpand) {
                    switch_btn.setIcon(GoogleMaterial.Icon.gmd_keyboard_arrow_down, Color.GRAY, 10)
                } else {
                    switch_btn.setIcon(GoogleMaterial.Icon.gmd_keyboard_arrow_up, Color.GRAY, 10)
                }
                switch_btn.tag = !isExpand
                more_layout.visibility = if (isExpand) View.GONE else View.VISIBLE
            }
            other_name.text = film.aka.joinToString("\n")
            durations.text = film.durations.joinToString("/")
            pubdate.text = film.pubdates.joinToString("\n")
            country.text = film.countries.joinToString("/")
            language.text = film.languages.joinToString("/")
        }

    }

}

