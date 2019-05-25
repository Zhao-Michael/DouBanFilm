package douban.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import douban.Subject
import douban.TagFilmList
import douban.subview.IFilmView
import michaelzhao.BaseActivity
import michaelzhao.FilmDetailActivity
import michaelzhao.R
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import util.*

class FilmTagAdapter(
        recycler: RecyclerView,
        filmTag: TagFilmList,
        filmView: IFilmView)
    : IRecyclerViewAdapter<FilmTagAdapter.ViewHolder>(filmView) {

    private var mTagList = mutableListOf<Subject>()
    private val mContext = recycler.context

    init {
        mTagList.addAll(filmTag.subjects)
        setImageHeight(recycler, 13, 3)
        var mInitLoadMore = false
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!mInitLoadMore) {
                    (recyclerView.adapter as? IRecyclerViewAdapter)?.enableLoadMore()
                    mInitLoadMore = true
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmTagAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_tag_film_cardview, parent)
        return FilmTagAdapter.ViewHolder(view, mImageWidth)
    }

    override fun onBindViewHolder(holder: FilmTagAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        holder.setTagItem(mTagList[pos])
        checkToEnd(pos)
    }

    override fun getItemCount(): Int {
        return mTagList.size
    }

    fun addTagList(list: List<Subject>) {
        mTagList.addAll(list)
    }

    class ViewHolder(mItemView: View, wid: Int?) : RecyclerView.ViewHolder(mItemView) {
        private val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        private val title by lazy { mItemView.find<TextView>(R.id.text_title) }
        private val image by lazy { mItemView.find<ImageView>(R.id.image_photo) }
        private val rate by lazy { mItemView.find<TextView>(R.id.text_rate) }
        private val layout_rate by lazy { mItemView.find<View>(R.id.layout_rate) }

        init {
            if (wid != null) {
                cardview.setWidth(wid)
                image.setWidth(wid)
                image.setHeight(Math.round(wid / 0.7f))
            }
        }

        fun setTagItem(sub: Subject) {
            title.text = sub.title
            rate.text = sub.rate
            rate.textColor = BaseActivity.getPrimaryColor()
            if (sub.rate.isBlank())
                layout_rate.hide()
            else
                layout_rate.show()
            image.setImageUrl(sub.cover, R.drawable.loading_large)
            cardview.onClick { FilmDetailActivity.showFilmDetail(sub.id) }
        }

    }
}