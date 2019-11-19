package douban.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import douban.DouBanV2
import douban.DouBanV2.getTagString
import douban.subview.FilmView
import douban.subview.IFilmView
import michaelzhao.BaseActivity
import michaelzhao.R
import michaelzhao.TagFilmActivity.Companion.showTagFilmList
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import util.*

class HomeTVAdapter(
        recycler: RecyclerView,
        filmView: IFilmView)
    : IRecyclerViewAdapter<HomeTVAdapter.ViewHolder>(filmView) {

    private val mContext = recycler.context
    private val listTag = DouBanV2.TVTagType.values().toList()

    init {
        recycler.setItemViewCacheSize(listTag.size)
        setImageHeight(recycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mContext.inflate(R.layout.listitem_home_film_cardview, parent)
        return HomeTVAdapter.ViewHolder(view, mImageWidth)
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

        private val cardview by lazy { mItemView.find<CardView>(R.id.cardview) }
        private val title by lazy { mItemView.find<TextView>(R.id.text_title) }
        private val page by lazy { mItemView.find<TextView>(R.id.page) }
        private val recyclerView by lazy { mItemView.find<RecyclerView>(R.id.mRecyclerView) }
        private val swipeLayout by lazy { mItemView.find<VerSwipeLayout>(R.id.mSwipeLayout) }

        init {
            if (wid != null) {
                cardview.setHeight((Math.round(wid / 3 / 0.7f) + 12.dip2px()) * 2 + 40.dip2px())
            }
            val manager = GridLayoutManager(mItemView.context, 2).apply { orientation = RecyclerView.HORIZONTAL }
            recyclerView.layoutManager = manager
            recyclerView.setHasFixedSize(true)
        }

        fun setTagItem(filmTag: DouBanV2.TVTagType, index: Int) {
            page.text = index.toString()
            title.textColor = BaseActivity.getPrimaryColor()
            title.text = getTagString(filmTag)
            swipeLayout.Enable()
            swipeLayout.ShowRefresh()
            cardview.onClick { showTagFilmList(DouBanV2.getTagString(filmTag), "tv") }

            run {
                val mStep = 30
                var mCurrPageIndex = 0
                var mAdapter: CommonInfoAdapter?
                val mFilmView = FilmView(recyclerView.context)

                Rx.get {
                    DouBanV2.getTagTV(filmTag)
                }.set {
                    mAdapter = CommonInfoAdapter(recyclerView, it, mFilmView)
                    recyclerView.adapter = mAdapter
                    mFilmView.apply {
                        setLoadMore {
                            swipeLayout.ShowRefresh()
                            Rx.get {
                                mCurrPageIndex += mStep
                                DouBanV2.getTagTV(filmTag, mCurrPageIndex, mStep)
                            }.set {
                                val cnt = mAdapter?.itemCount
                                if (cnt != null && it.subjects.isNotEmpty()) {
                                    mAdapter?.addTagList(it.subjects)
                                    mAdapter?.notifyItemInserted(cnt)
                                } else {
                                    mCurrPageIndex -= mStep
                                    showNoMoreMsg(recyclerView)
                                }
                            }.end {
                                swipeLayout.DisEnable()
                                swipeLayout.HideRefresh()
                                mAdapter?.loadMoreFinish()
                            }.err {
                                mFilmView.showErrMsg(it, recyclerView)
                            }
                        }
                    }
                }.end {
                    swipeLayout.HideRefresh()
                    swipeLayout.DisEnable()
                }
            }
        }

    }

}