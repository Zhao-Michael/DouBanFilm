package douban.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import douban.FilmReview
import douban.Review
import douban.subview.IFilmView
import michaelzhao.R
import michaelzhao.WebActivity
import org.jetbrains.anko.find
import util.OnClick
import util.inflate
import util.setImageUrl

class FilmReviewAdapter(context: Context, filmReview: FilmReview, filmView: IFilmView) : IRecyclerViewAdapter<FilmReviewAdapter.ViewHolder>(filmView) {

    private val mContext = context
    private var mFilmReview = filmReview

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmReviewAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_review_cardview, parent)
        return FilmReviewAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFilmReview.reviews.size
    }

    override fun onBindViewHolder(holder: FilmReviewAdapter.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val pos = holder.adapterPosition
        val review = mFilmReview.reviews[pos]
        holder.setReview(review, pos + 1)
        holder.cardview.OnClick { WebActivity.ShowWebView(mContext, review.alt, review.author.name + " : " + review.title) }
    }

    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        val text_name by lazy { mItemView.find<TextView>(R.id.text_name) }
        val text_title by lazy { mItemView.find<TextView>(R.id.text_title) }
        val text_review by lazy { mItemView.find<TextView>(R.id.text_review) }
        val profile_image by lazy { mItemView.find<ImageView>(R.id.profile_image) }
        val page by lazy { mItemView.find<TextView>(R.id.page) }

        fun setReview(review: Review, pos: Int) {
            profile_image.setImageUrl(review.author.avatar)
            text_review.text = review.summary
            text_name.text = review.author.name
            text_title.text = review.title
            page.text = pos.toString()
        }

    }

}