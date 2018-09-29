package douban.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import douban.FilmDetail
import douban.Photo
import org.jetbrains.anko.find
import util.inflate
import util.setImageUrl
import venerealulcer.R

class FilmPhotosAdapter(context: Context, filmDetail: FilmDetail) : RecyclerView.Adapter<FilmPhotosAdapter.ViewHolder>() {

    private val mContext = context
    private val mFilmDetail = filmDetail
    private val mListPhoto = mutableListOf<Photo>()

    init {
        mListPhoto.addAll(mFilmDetail.photos)
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
    }


    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {

        private val image_photo by lazy { mItemView.find<ImageView>(R.id.image_photo) }

        internal fun setPhoto(photo: Photo, pos: Int) {
            image_photo.setImageUrl(photo.thumb, R.drawable.loading_large)
        }

    }

}