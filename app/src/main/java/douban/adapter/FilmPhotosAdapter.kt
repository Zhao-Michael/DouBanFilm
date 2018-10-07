package douban.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import douban.FilmDetail
import douban.FilmPhoto
import douban.Photo
import imageplayer.ImageViewActivity
import michaelzhao.R
import org.jetbrains.anko.find
import util.*

class FilmPhotosAdapter(context: Context) : RecyclerView.Adapter<FilmPhotosAdapter.ViewHolder>() {

    private val mContext = context
    private val mListPhoto = mutableListOf<Photo>()

    private var mFilmPhoto: FilmPhoto? = null
    private var mFilmDetail: FilmDetail? = null

    constructor(context: Context, filmPhoto: FilmPhoto) : this(context) {
        mFilmPhoto = filmPhoto
        mListPhoto.addAll(filmPhoto.photos)
    }

    constructor(context: Context, filmDetail: FilmDetail) : this(context) {
        mListPhoto.addAll(filmDetail.photos)
        mFilmDetail = filmDetail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmPhotosAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_photo_cardview, parent)
        return FilmPhotosAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListPhoto.size
    }

    override fun onBindViewHolder(holder: FilmPhotosAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        holder.setPhoto(mListPhoto[pos], pos)
        if (pos == itemCount - 1) {
            (holder.itemView.layoutParams as? ViewGroup.MarginLayoutParams)?.SetMargins(3, 3, 3, 3.dip2px())
        }
        holder.itemView.OnClick { ImageViewActivity.ShowImages(mContext, mListPhoto.map { it.image }, pos) }
    }


    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {

        private val mImageView by lazy { mItemView.find<ImageView>(R.id.image_photo) }

        internal fun setPhoto(photo: Photo, pos: Int) {
            mImageView.setImageUrl(photo.thumb, R.drawable.loading_large)
        }

    }

}