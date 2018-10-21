package douban.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import douban.DouBanV2
import douban.DouBanV2.getTagString
import michaelzhao.BaseActivity
import michaelzhao.R
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import util.*

class FilmHomeAdapter(recycler: RecyclerView) : IRecyclerViewAdapter<FilmHomeAdapter.ViewHolder>() {

    private val mContext = recycler.context
    private val listTag = DouBanV2.TagType.values().toList()

    init {
        setImageHeight(recycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mContext.inflate(R.layout.listitem_home_film_cardview, parent)
        return FilmHomeAdapter.ViewHolder(view, mImageWidth)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val pos = holder.adapterPosition
        holder.setTagItem(mContext, listTag[pos], pos + 1)
    }

    override fun getItemCount(): Int {
        return listTag.size
    }

    class ViewHolder(mItemView: View, wid: Int?) : RecyclerView.ViewHolder(mItemView) {
        private val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        private val title by lazy { mItemView.find<TextView>(R.id.text_title) }
        private val page by lazy { mItemView.find<TextView>(R.id.page) }
        private val recyclerView by lazy { mItemView.find<RecyclerView>(R.id.mRecyclerView) }
        private val swipeLayout by lazy { mItemView.find<VerticalSwipeRefreshLayout>(R.id.mSwipeLayout) }

        init {
            if (wid != null)
                cardview.SetHeight((Math.round(wid / 3 / 0.7f) + 12.dip2px()) * 3 + 38.dip2px())
        }

        fun setTagItem(context: Context, tag: DouBanV2.TagType, index: Int) {
            page.text = index.toString()
            title.textColor = BaseActivity.getPrimaryColor()
            title.text = getTagString(tag)
            if (recyclerView.adapter == null) {
                val manager = GridLayoutManager(context, 3)
                recyclerView.layoutManager = manager
                swipeLayout.Enable()
                swipeLayout.ShowRefresh()
                Rx.get {
                    DouBanV2.getTagFilm(tag)
                }.set {
                    recyclerView.adapter = FilmTagAdapter(recyclerView, it)
                }.end {
                    swipeLayout.DisEnable()
                    swipeLayout.HideRefresh()
                }
            }
        }
    }

}