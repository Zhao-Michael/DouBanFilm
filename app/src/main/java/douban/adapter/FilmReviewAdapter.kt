package douban.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import douban.FilmReview
import michaelzhao.R
import michaelzhao.WebActivity
import util.OnClick
import util.SetMargins
import util.dip2px
import util.inflate

class FilmReviewAdapter(context: Context, filmReview: FilmReview) : RecyclerView.Adapter<FilmPopReviewAdapter.ViewHolder>() {

    private val mContext = context
    private var mFilmReview = filmReview

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmPopReviewAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_review_cardview, parent)
        return FilmPopReviewAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFilmReview.reviews.size
    }

    override fun onBindViewHolder(holder: FilmPopReviewAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val review = mFilmReview.reviews[pos]
        holder.setReview(review, pos + 1)
        if (pos == itemCount - 1) {
            (holder.cardview.layoutParams as? ViewGroup.MarginLayoutParams)?.SetMargins(5, 5, 5, 5.dip2px())
        }
        holder.cardview.OnClick { WebActivity.ShowWebView(mContext, review.alt, review.author.name + " : " + review.title) }
    }

}