package douban.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import douban.Celebrity
import douban.FilmDetail
import org.jetbrains.anko.find
import util.inflate
import util.setImageUrl
import venerealulcer.R

class FilmCelebrityAdapter(context: Context, filmDetail: FilmDetail) : RecyclerView.Adapter<FilmCelebrityAdapter.ViewHolder>() {

    private val mContext = context
    private val mFilmDetail = filmDetail
    private val mListCelebrity = mutableListOf<Celebrity>()

    init {
        mListCelebrity.addAll(mFilmDetail.directors.map { it.resetAlt("导演") })
        mListCelebrity.addAll(mFilmDetail.casts.map { it.resetAlt("演员") })
        mListCelebrity.addAll(mFilmDetail.writers.map { it.resetAlt("编剧") })
        mListCelebrity.distinct()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mContext.inflate(R.layout.listitem_celebrity_layout, parent)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mListCelebrity.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        holder.setCelebrity(mListCelebrity[pos], pos)
    }


    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
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