package douban.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import douban.FilmDetail
import douban.PopularReview
import michaelzhao.R
import michaelzhao.WebActivity.Companion.ShowWebView
import org.jetbrains.anko.find
import util.*

class FilmReviewAdapter(context: Context, filmDetail: FilmDetail) : RecyclerView.Adapter<FilmReviewAdapter.ViewHolder>() {

    private val mContext = context
    private val mFilmDetail = filmDetail
    private val mListReview = mFilmDetail.popular_reviews

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmReviewAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_review_cardview, parent)
        return FilmReviewAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListReview.size
    }

    override fun onBindViewHolder(holder: FilmReviewAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val review = mListReview[pos]
        holder.setReview(review, pos + 1)
        if (pos == itemCount - 1) {
            (holder.cardview.layoutParams as? ViewGroup.MarginLayoutParams)?.SetMargins(5, 5, 5, 5.dip2px())
        }
        holder.cardview.OnClick { ShowWebView(mContext, review.alt, review.author.name + " : " + review.title) }
    }

    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        val text_name by lazy { mItemView.find<TextView>(R.id.text_name) }
        val text_title by lazy { mItemView.find<TextView>(R.id.text_title) }
        val text_review by lazy { mItemView.find<TextView>(R.id.text_review) }
        val profile_image by lazy { mItemView.find<ImageView>(R.id.profile_image) }
        val page by lazy { mItemView.find<TextView>(R.id.page) }

        fun setReview(review: PopularReview, pos: Int) {
            profile_image.setImageUrl(review.author.avatar)
            text_review.text = review.summary
            text_name.text = review.author.name
            text_title.text = review.title
            page.text = pos.toString()
        }
    }


}