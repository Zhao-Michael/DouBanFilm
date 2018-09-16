package douban

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.centerCropTransform
import com.hedgehog.ratingbar.RatingBar
import org.jetbrains.anko.find
import util.OnClick
import util.inflate
import venerealulcer.R

class FilmAdapter(listViews: FilmList, context: Context) : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {
    private val mContext = context
    private var mFilmList: FilmList = listViews
    private var mOnClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mContext.inflate(R.layout.listitem_film_layout, parent)
        view.OnClick { mOnClickListener?.onClick(view) }
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
        holder.rate.text = "评分：" + film.rating.average
        holder.director.text = "导演：" + film.directors.joinToString("/") { it.name }
        holder.actor.text = "演员：" + film.casts.joinToString("/") { it.name }
        holder.ratingbar.setStar((film.rating.stars.toInt() / 10.0).toFloat())
        holder.page.text = (holder.adapterPosition + 1).toString()
        Glide.with(holder.image.context)
                .load(film.images.small)
                .apply(centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.image)
    }

    fun setOnClickListener(list: View.OnClickListener) {
        mOnClickListener = list
    }

    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        val image by lazy { mItemView.find<ImageView>(R.id.image) }
        val title by lazy { mItemView.find<TextView>(R.id.title) }
        val actor by lazy { mItemView.find<TextView>(R.id.actor) }
        val director by lazy { mItemView.find<TextView>(R.id.director) }
        val rate by lazy { mItemView.find<TextView>(R.id.rate) }
        val ratingbar by lazy { mItemView.find<RatingBar>(R.id.ratingbar) }
        val page by lazy { mItemView.find<TextView>(R.id.page) }
    }

}

