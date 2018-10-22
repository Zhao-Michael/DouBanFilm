package douban.adapter

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import douban.*
import douban.subview.IFilmView
import imageplayer.ImageViewActivity
import michaelzhao.R
import org.jetbrains.anko.find
import util.*

//Film Photo, Film Man Photo, Auto Load More
class FilmPhotoAdapter(context: Context, filmView: IFilmView) : IRecyclerViewAdapter<FilmPhotoAdapter.ViewHolder>(filmView) {

    private val mContext = context
    private val mListPhoto = mutableListOf<Photo>()

    init {
        setHasStableIds(true)
    }

    //所有剧照
    constructor(recycler: RecyclerView, filmPhoto: FilmPhoto, filmView: IFilmView) : this(recycler.context, filmView) {
        setImageHeight(recycler)
        mListPhoto.addAll(filmPhoto.photos)
    }

    override fun getItemId(position: Int): Long {
        return mListPhoto[position].hashCode().toLong()
    }

    //所有影人图片
    constructor(recycler: RecyclerView, manPhoto: FilmManPhoto, filmView: IFilmView) : this(recycler.context, filmView) {
        mListPhoto.addAll(manPhoto.photos)
        setImageHeight(recycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmPhotoAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_photo_cardview, parent)
        return FilmPhotoAdapter.ViewHolder(view, mImageWidth)
    }

    override fun getItemCount(): Int {
        return mListPhoto.size
    }

    override fun onBindViewHolder(holder: FilmPhotoAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        holder.setPhoto(mListPhoto[pos])
        holder.itemView.OnClick {
            ImageViewActivity.ShowImages(mContext, mListPhoto.map { it.image }, pos)
        }
        checkToEnd(pos)
    }

    fun addListPhotos(list: List<Photo>) {
        mListPhoto.addAll(list)
    }

    fun getListPhotos() = mListPhoto.toList()

    class ViewHolder(mItemView: View, hei: Int?) : RecyclerView.ViewHolder(mItemView) {

        private val mImageView by lazy { mItemView.find<ImageView>(R.id.image_photo) }

        init {
            if (hei != null)
                mImageView.SetWidth(hei)
        }

        internal fun setPhoto(photo: Photo) {
            mImageView.setImageUrl(photo.cover, R.drawable.loading_large)
        }

    }

}