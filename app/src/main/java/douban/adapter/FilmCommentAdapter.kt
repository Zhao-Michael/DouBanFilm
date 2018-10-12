package douban.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import douban.FilmComment
import michaelzhao.R
import util.SetMargins
import util.dip2px
import util.inflate

class FilmCommentAdapter(context: Context, filmComment: FilmComment) : RecyclerView.Adapter<FilmPopCommentAdapter.ViewHolder>() {

    private val mContext = context
    private val mFilmComment = filmComment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmPopCommentAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_comment_cardview, parent)
        return FilmPopCommentAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFilmComment.comments.size
    }

    override fun onBindViewHolder(holder: FilmPopCommentAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        holder.setComment(mFilmComment.comments[pos])
        if (pos == itemCount - 1) {
            (holder.cardview.layoutParams as? ViewGroup.MarginLayoutParams)?.SetMargins(5, 5, 5, 5.dip2px())
        }
    }

}