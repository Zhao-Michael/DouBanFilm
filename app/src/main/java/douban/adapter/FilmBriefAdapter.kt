package douban.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import douban.SearchBrief
import douban.subview.IFilmView
import michaelzhao.BaseActivity
import org.jetbrains.anko.find
import michaelzhao.FilmDetailActivity
import michaelzhao.R
import org.jetbrains.anko.textColor
import util.*

//brief search result adapter
class FilmBriefAdapter(recyclerView: RecyclerView, listViews: Array<SearchBrief>,
                       context: Context,
                       filmView: IFilmView) :
        IRecyclerViewAdapter<FilmBriefAdapter.ViewHolder>(filmView) {

    private val mListBrief = listOf(*listViews)
    private val mContext = context
    private var mOnClickListener: View.OnClickListener? = null
    private val mRecyclerView = recyclerView

    init {
        setImageHeight(mRecyclerView, 10)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mContext.inflate(R.layout.listitem_tag_film_cardview, parent)
        view.onClick { mOnClickListener?.onClick(view) }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListBrief.size
    }

    fun getListBrief(): Array<SearchBrief> {
        return mListBrief.toTypedArray()
    }

    @Suppress("SENSELESS_COMPARISON")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val sb = mListBrief[pos]
        mImageWidth?.let { holder.image.setWidth(it) }
        mImageWidth?.let { holder.image.setHeight((it * 1.4).toInt()) }
        holder.image.scaleType = ImageView.ScaleType.CENTER_CROP
        holder.image.setImageUrl(sb.img, R.drawable.loading_large)
        holder.title.text = sb.title
        holder.year.textColor = BaseActivity.getPrimaryColor()
        if (sb.year != null && sb.year.isNotBlank())
            holder.year.text = sb.year
        else
            holder.year.visibility = View.GONE
        holder.view.onClick {
            when (sb.type) {
                "celebrity" -> FilmDetailActivity.showFilmMan(sb.id)
                "movie" -> FilmDetailActivity.showFilmDetail(sb.id)
            }
        }
    }


    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val view = mItemView
        val image by lazy { mItemView.find<ImageView>(R.id.image_photo) }
        val title by lazy { mItemView.find<TextView>(R.id.text_title) }
        val year by lazy { mItemView.find<TextView>(R.id.text_rate) }
        val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
    }

}