package douban.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.Comment
import douban.FilmComment
import michaelzhao.R
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import util.Util
import util.inflate
import util.setImageUrl

class FilmCommentAdapter(context: Context, filmComment: FilmComment) : IRecyclerViewAdapter<FilmCommentAdapter.ViewHolder>() {

    private val mContext = context
    private val mFilmComment = filmComment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmCommentAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_comment_cardview, parent)
        return FilmCommentAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFilmComment.comments.size
    }

    override fun onBindViewHolder(holder: FilmCommentAdapter.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val pos = holder.adapterPosition
        holder.setComment(mFilmComment.comments[pos])
    }

    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        val profile_image by lazy { mItemView.find<ImageView>(R.id.profile_image) }
        val text_name by lazy { mItemView.find<TextView>(R.id.text_name) }
        val text_date by lazy { mItemView.find<TextView>(R.id.text_date) }
        val text_comment by lazy { mItemView.find<TextView>(R.id.text_comment) }
        val text_like by lazy { mItemView.find<TextView>(R.id.text_like) }
        val image_like by lazy { mItemView.find<ImageView>(R.id.image_like) }

        fun setComment(comment: Comment) {
            profile_image.setImageUrl(comment.author.avatar)
            text_name.text = comment.author.name
            text_date.text = comment.created_at
            text_comment.text = comment.content
            text_like.text = comment.useful_count.toString()
            image_like.image = Util.CreateIcon(image_like.context, GoogleMaterial.Icon.gmd_thumb_up, 14)
        }

    }


}