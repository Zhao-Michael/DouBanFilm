package douban.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import douban.PhotoComment
import douban.subview.IFilmView
import michaelzhao.R
import org.jetbrains.anko.find
import util.Util.CopyToClipBoard
import util.inflate
import util.onLongClick
import util.setImageUrl

class PhotoCommentAdapter(context: Context, photoComment: List<PhotoComment>, filmView: IFilmView) : IRecyclerViewAdapter<PhotoCommentAdapter.ViewHolder>(filmView) {

    private val mContext = context
    private val mListComment = mutableListOf<PhotoComment>()

    init {
        mListComment.addAll(photoComment)
    }

    fun addListComment(list: List<PhotoComment>) {
        mListComment.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoCommentAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_photo_comment_layout, parent)
        return PhotoCommentAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListComment.size
    }

    override fun onBindViewHolder(holder: PhotoCommentAdapter.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val pos = holder.adapterPosition
        holder.setComment(mListComment[pos], pos + 1)
        checkToEnd(pos)
    }


    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val mView = mItemView
        val main_layout by lazy { mItemView.find<FrameLayout>(R.id.mMainLayout) }
        val profile_image by lazy { mItemView.find<ImageView>(R.id.profile_image) }
        val text_name by lazy { mItemView.find<TextView>(R.id.text_name) }
        val text_date by lazy { mItemView.find<TextView>(R.id.text_date) }
        val text_comment by lazy { mItemView.find<TextView>(R.id.text_comment) }
        val text_page by lazy { mItemView.find<TextView>(R.id.page) }

        fun setComment(comment: PhotoComment, page: Int) {
            main_layout.onLongClick { CopyToClipBoard(mView.context, comment.content) }
            profile_image.setImageUrl(comment.thumb)
            text_name.text = comment.author.trim()
            text_date.text = comment.time
            text_comment.text = Html.fromHtml(comment.content.trim())
            text_page.text = page.toString()
        }

    }

}