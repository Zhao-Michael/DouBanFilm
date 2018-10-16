package douban.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import douban.*
import imageplayer.ImageViewActivity
import michaelzhao.BaseActivity
import michaelzhao.R
import org.jetbrains.anko.find
import util.*

//Film Photo, Film Man Photo, Auto Load More
class FilmPhotoAdapter(context: Context) : RecyclerViewAdapter<FilmPhotoAdapter.ViewHolder>() {

    private val mContext = context
    private val mListPhoto = mutableListOf<Photo>()

    private var mFilmPhoto: FilmPhoto? = null   //所有电影剧照
    private var mManPhoto: FilmManPhoto? = null      //影人所有照片

    private var mImageHeight: Int? = null

    //所有剧照
    constructor(recycler: RecyclerView, filmPhoto: FilmPhoto) : this(recycler.context) {
        mFilmPhoto = filmPhoto
        setImageHeight(recycler)
        mListPhoto.addAll(filmPhoto.photos)
    }

    //所有影人图片
    constructor(recycler: RecyclerView, manPhoto: FilmManPhoto) : this(recycler.context) {
        mListPhoto.addAll(manPhoto.photos)
        mManPhoto = manPhoto
        setImageHeight(recycler)
    }

    private fun setImageHeight(recycler: RecyclerView) {
        val sc = (recycler.layoutManager as? GridLayoutManager)?.spanCount
        if (sc != null) {
            mImageHeight = BaseActivity.getScreenSize().x / sc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmPhotoAdapter.ViewHolder {
        val view = mContext.inflate(R.layout.listitem_photo_cardview, parent)
        return FilmPhotoAdapter.ViewHolder(view, mImageHeight)
    }

    override fun getItemCount(): Int {
        return mListPhoto.size
    }

    override fun onBindViewHolder(holder: FilmPhotoAdapter.ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        holder.setPhoto(mListPhoto[pos])
        holder.itemView.OnClick { ImageViewActivity.ShowImages(mContext, mListPhoto.map { it.image }, pos) }
    }

    class ViewHolder(mItemView: View, hei: Int?) : RecyclerView.ViewHolder(mItemView) {

        private val mImageView by lazy { mItemView.find<ImageView>(R.id.image_photo) }

        init {
            if (hei != null)
                mImageView.SetHeight(hei)
        }

        internal fun setPhoto(photo: Photo) {
            mImageView.setImageUrl(photo.thumb, R.drawable.loading_large)
        }

    }

}