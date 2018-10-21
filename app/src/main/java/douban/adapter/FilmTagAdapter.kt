package douban.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import douban.Subject
import douban.TagFilmList
import michaelzhao.BaseActivity
import michaelzhao.FilmDetailActivity
import michaelzhao.R
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import util.*

class FilmTagAdapter(recycler: RecyclerView, filmTag: TagFilmList) : IRecyclerViewAdapter<FilmTagAdapter.ViewHolder>() {

    private val mFilmTagList = filmTag
    private val mContext = recycler.context

    init {
        setImageHeight(recycler, 13, 3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmTagAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_tag_film_cardview, parent)
        return FilmTagAdapter.ViewHolder(view, mImageWidth)
    }

    override fun onBindViewHolder(holder: FilmTagAdapter.ViewHolder, position: Int) {
        holder.setTagItem(mFilmTagList.subjects[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return mFilmTagList.subjects.size
    }

    class ViewHolder(mItemView: View, wid: Int?) : RecyclerView.ViewHolder(mItemView) {
        private val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        private val title by lazy { mItemView.find<TextView>(R.id.text_title) }
        private val image by lazy { mItemView.find<ImageView>(R.id.image_photo) }
        private val rate by lazy { mItemView.find<TextView>(R.id.text_rate) }

        init {
            if (wid != null) {
                cardview.SetWidth(wid)
                image.SetWidth(wid)
                image.SetHeight(Math.round(wid / 0.7f))
            }
        }

        fun setTagItem(sub: Subject) {
            title.text = sub.title
            rate.text = sub.rate
            rate.textColor = BaseActivity.getPrimaryColor()
            image.setImageUrl(sub.cover, R.drawable.loading_large)
            cardview.OnClick { FilmDetailActivity.ShowFilmDetail(sub.id) }
        }

    }
}