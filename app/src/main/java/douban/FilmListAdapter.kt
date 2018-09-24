package douban

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hedgehog.ratingbar.RatingBar
import org.jetbrains.anko.find
import util.*
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
        holder.title.text = film.title
        holder.year.text = film.year
        holder.rate.text = film.rating.average.toString()
        holder.genres.text = film.genres.joinToString("/")
        holder.org_name.text = film.original_title
        holder.director.text = film.directors.joinToString("/") { it.name }
        holder.actor.text = film.casts.joinToString("/") { it.name }
        holder.ratingbar.setStar((film.rating.stars.toInt() / 10.0).toFloat())
        if (film.rating.stars.toInt() == 0) {
            holder.ratingbar.visibility = View.GONE
            holder.rate.text = "暂无评分"
        }
        holder.page.text = (holder.adapterPosition + 1).toString()
        holder.image.setImageUrl(film.images.small)
        holder.cardview.OnClick { FilmDetailActivity.ShowFilmDetail(film.id) }
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
    }

}

