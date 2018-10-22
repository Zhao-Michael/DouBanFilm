package douban.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import douban.DouBanV2
import douban.DouBanV2.getTagString
import douban.TagFilmList
import douban.subview.FilmView
import douban.subview.IFilmView
import michaelzhao.BaseActivity
import michaelzhao.R
import michaelzhao.TagFilmActivity.Companion.ShowTagFilmList
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import util.*

class FilmHomeAdapter(
        recycler: RecyclerView,
        filmView: IFilmView)
    : IRecyclerViewAdapter<FilmHomeAdapter.ViewHolder>(filmView) {

    private val mContext = recycler.context
    private val listTag = DouBanV2.TagType.values().toList()

    init {
        recycler.setItemViewCacheSize(listTag.size)
        setImageHeight(recycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mContext.inflate(R.layout.listitem_home_film_cardview, parent)
        return FilmHomeAdapter.ViewHolder(view, mImageWidth)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val pos = holder.adapterPosition
        holder.setTagItem(listTag[pos], pos + 1)
    }

    override fun getItemCount(): Int {
        return listTag.size
    }

    class ViewHolder(mItemView: View, wid: Int?) : RecyclerView.ViewHolder(mItemView) {
        companion object {
            private val mMapTagFilm = mutableMapOf<DouBanV2.TagType, TagFilmList>()
        }

        private val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        private val title by lazy { mItemView.find<TextView>(R.id.text_title) }
        private val page by lazy { mItemView.find<TextView>(R.id.page) }
        private val recyclerView by lazy { mItemView.find<RecyclerView>(R.id.mRecyclerView) }
        private val swipeLayout by lazy { mItemView.find<VerSwipeLayout>(R.id.mSwipeLayout) }


        init {
            if (wid != null) {
                cardview.SetHeight((Math.round(wid / 3 / 0.7f) + 12.dip2px()) * 2 + 40.dip2px())
            }
            val manager = GridLayoutManager(mItemView.context, 2).apply { orientation = RecyclerView.HORIZONTAL }
            recyclerView.layoutManager = manager
            recyclerView.setHasFixedSize(true)
        }

        fun setTagItem(tag: DouBanV2.TagType, index: Int) {
            page.text = index.toString()
            title.textColor = BaseActivity.getPrimaryColor()
            title.text = getTagString(tag)
            swipeLayout.Enable()
            swipeLayout.ShowRefresh()

            if (mMapTagFilm.contains(tag)) {
                recyclerView.adapter = FilmTagAdapter(recyclerView, mMapTagFilm[tag]!!, FilmView(recyclerView.context))
                swipeLayout.HideRefresh()
                swipeLayout.DisEnable()
            } else {
                Rx.get {
                    DouBanV2.getTagFilm(tag)
                }.set {
                    recyclerView.adapter = FilmTagAdapter(recyclerView, it, FilmView(recyclerView.context))
                    mMapTagFilm[DouBanV2.TagType.Action] = it
                }.end {
                    swipeLayout.HideRefresh()
                    swipeLayout.DisEnable()
                }
            }
            cardview.OnClick { ShowTagFilmList(DouBanV2.getTagString(tag)) }
        }
    }

}