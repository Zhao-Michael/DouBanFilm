package douban.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import douban.FilmDetail
import douban.Photo
import imageplayer.ImageViewActivity
import michaelzhao.R
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import util.OnClick
import util.inflate

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
        holder.itemView.OnClick { ImageViewActivity.ShowImages(mContext, mListPhoto.map { it.image }, pos) }
    }


    class ViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {

        private val image_photo by lazy { mItemView.find<ImageView>(R.id.image_photo) }

        internal fun setPhoto(photo: Photo, pos: Int) {
            Glide.with(image_photo.context)
                    .load(photo.thumb).listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            e?.printStackTrace()
                            return true
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            image_photo.image = resource
                            return true
                        }
                    })
                    .apply(RequestOptions.placeholderOf(R.drawable.loading_large))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(image_photo)
        }

    }

}