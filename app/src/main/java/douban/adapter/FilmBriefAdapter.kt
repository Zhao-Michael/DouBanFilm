package douban.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import douban.SearchBrief
import org.jetbrains.anko.find
import util.OnClick
import util.inflate
import util.setImageUrl
import michaelzhao.FilmDetailActivity
import michaelzhao.R

//brief search result adapter
class FilmBriefAdapter(listViews: Array<SearchBrief>, context: Context) : IRecyclerViewAdapter<FilmBriefAdapter.ViewHolder>() {

    private val mListBrief = listOf(*listViews)
    private val mContext = context
    private var mOnClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mContext.inflate(R.layout.listitem_search_brief_layout, parent)
        view.OnClick { mOnClickListener?.onClick(view) }
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
        val sb = mListBrief[position]
        holder.image.setImageUrl(sb.img)
        holder.title.text = sb.title
        if (sb.year != null && sb.year.isNotBlank())
            holder.year.text = sb.year
        else
            holder.year.visibility = View.GONE
        holder.page.text = (holder.adapterPosition + 1).toString()
        holder.view.OnClick {
            when (sb.type) {
                "celebrity" -> FilmDetailActivity.ShowFilmMan(sb.id)
                "movie" -> FilmDetailActivity.ShowFilmDetail(sb.id)
            }
        }
    }


    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val view = mItemView
        val image by lazy { mItemView.find<ImageView>(R.id.image) }
        val title by lazy { mItemView.find<TextView>(R.id.title) }
        val year by lazy { mItemView.find<TextView>(R.id.year) }
        val page by lazy { mItemView.find<TextView>(R.id.page) }
    }

}