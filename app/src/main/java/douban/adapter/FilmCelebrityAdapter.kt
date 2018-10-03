package douban.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import douban.Celebrity
import douban.FilmDetail
import org.jetbrains.anko.find
import util.SetMargins
import util.dip2px
import util.inflate
import util.setImageUrl
import michaelzhao.R

class FilmCelebrityAdapter(context: Context, filmDetail: FilmDetail) : RecyclerView.Adapter<FilmCelebrityAdapter.ViewHolder>() {

    private val mContext = context
    private val mFilmDetail = filmDetail
    private val mListCelebrity = mutableListOf<Celebrity>()

    init {
        mListCelebrity.clear()
        mListCelebrity.addAll(parseCelebrity(mFilmDetail.directors, "导演"))
        mListCelebrity.forEach {
            val t_id = it.id
            if (mFilmDetail.writers.find { it.id == t_id } != null) {
                it.set_Alt("导演 / 编剧")
            }
        }
        mListCelebrity.addAll(parseCelebrity(mFilmDetail.writers, "编剧"))
        mListCelebrity.addAll(parseCelebrity(mFilmDetail.casts, "演员"))
        val temp = mListCelebrity.filter { it.isNotNULL() }.distinctBy { it.id }
        mListCelebrity.clear()
        mListCelebrity.addAll(temp)
    }

    private fun parseCelebrity(list: List<Celebrity>, alt: String): List<Celebrity> {
        return list.filter { it.isNotNULL() }.onEach { it.set_Alt(alt) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mContext.inflate(R.layout.listitem_celebrity_cardview, parent)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListCelebrity.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        if (pos == itemCount - 1) {
            (holder.cardview.layoutParams as? ViewGroup.MarginLayoutParams)?.SetMargins(5, 5, 5, 10.dip2px())
        }
        holder.setCelebrity(mListCelebrity[pos], pos)
    }


    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        val image_celebrity by lazy { mItemView.find<ImageView>(R.id.image_celebrity) }
        val text_name by lazy { mItemView.find<TextView>(R.id.text_name) }
        val text_name_en by lazy { mItemView.find<TextView>(R.id.text_name_en) }
        val text_alt by lazy { mItemView.find<TextView>(R.id.text_alt) }
        val page by lazy { mItemView.find<TextView>(R.id.page) }

        fun setCelebrity(cele: Celebrity, pos: Int) {
            image_celebrity.setImageUrl(cele.avatars.medium)
            text_name.text = cele.name
            text_name_en.text = cele.name_en
            text_alt.text = cele.alt
            page.text = (pos + 1).toString()
        }

    }

}