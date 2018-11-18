package imageplayer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import com.github.chrisbanes.photoview.PhotoView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.subview.PhotoCommentView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import util.Util.HideStatusBar
import michaelzhao.BaseActivity
import michaelzhao.R
import util.*


class ImageViewActivity : BaseActivity() {

    companion object {
        private const val ImageUrl: String = "ImageUrl"
        private const val ImageStartIndex = "ImageStartIndex"
        private const val ImageID = "ImageID"
        fun showImages(context: Context, list: List<String>, start: Int = 0, id: String? = null) {
            val intent = Intent(context, ImageViewActivity::class.java)
            intent.putExtra(ImageUrl, list.toTypedArray())
            intent.putExtra(ImageStartIndex, start)
            intent.putExtra(ImageID, id)
            context.startActivity(intent)
        }
    }

    override val mLayout: Int = R.layout.activity_imageview
    private val mTextPage by lazy { find<TextView>(R.id.mTextPage) }
    private val mImageViewPager by lazy { find<ImageViewPager>(R.id.mImageViewPager) }
    private val mBtnClose by lazy { find<ImageButton>(R.id.mBtnClose) }
    private val mBtnMore by lazy { find<ImageButton>(R.id.mBtnMore) }
    private val mBtnComment by lazy { find<ImageButton>(R.id.mBtnComment) }

    private val mTextTitle by lazy { find<TextView>(R.id.mTextTitle) }
    private val mListLayout by lazy { find<FrameLayout>(R.id.mListLayout) }
    private val mCurrIndex get() = mImageViewPager.currentItem
    private var mStartIndex = 0
    private val mListUrl = mutableListOf<String>()
    private var mCurrID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imageview)
        HideStatusBar()

        mListUrl.addAll(intent.getStringArrayExtra(ImageUrl))
        mStartIndex = intent.getIntExtra(ImageStartIndex, 0)
        mCurrID = intent.getStringExtra(ImageID)

        if (mListUrl.isEmpty()) {
            toast("未找到任何图片链接")
            finish()
            return
        } else {
            mImageViewPager.adapter = ImagePagerAdapter(this, mListUrl)
            val cnt = mListUrl.size
            if (cnt > 1) {
                mTextPage.text = "${mCurrIndex + 1} / $cnt"
                mTextTitle.text = mListUrl[mCurrIndex]
            }
            mImageViewPager.setPageChangeListen { index ->
                mListLayout.visibility = View.GONE
                mTextPage.text = "${index + 1} / $cnt"
                mTextTitle.text = mListUrl[index]
            }
        }
        mTextTitle.hide()
        mImageViewPager.currentItem = mStartIndex

        mBtnClose.setIcon(GoogleMaterial.Icon.gmd_close, 16)
        mBtnClose.onClick { finish() }

        mBtnComment.setIcon(GoogleMaterial.Icon.gmd_comment)
        mBtnComment.onClick { showComment() }

        mBtnMore.setIcon(GoogleMaterial.Icon.gmd_more_vert)
        mBtnMore.onClick {
            val items = Array<CharSequence>(4, Int::toString)
            items[0] = "图片源"
            items[1] = "下载图片"
            items[2] = "刷新"
            items[3] = "显示评论"
            val builder = AlertDialog.Builder(this)
            builder.setItems(items) { _, which ->
                when (which) {
                    0 -> showImageSrc()
                    1 -> downLoadImage()
                    2 -> reLoadImage()
                    3 -> showComment()
                }
            }
            builder.create().show()
        }
    }

    private fun showImageSrc() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("图片源")
        val url = mListUrl[mCurrIndex]
        dialog.setMessage(url)
        dialog.setPositiveButton("关闭", null)
        dialog.setNegativeButton("复制") { d, _ ->
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.primaryClip = ClipData.newPlainText(null, url)
            toast("复制到剪切板")
            d.dismiss()
        }
        dialog.show()
    }

    private fun downLoadImage() {

    }

    private fun reLoadImage() {
        val view = (mImageViewPager.adapter as? ImagePagerAdapter)?.getCurrView()
        val photoView = view?.find<PhotoView>(R.id.mPhotoView)
        photoView?.setImageUrl(mListUrl[mCurrIndex])
    }

    private fun showComment() {
        if (mListLayout.isShown || mCurrID.isBlank()) {
            mListLayout.visibility = View.GONE
        } else {
            uiThread {
                mListLayout.removeAllViews()
                val iView = PhotoCommentView(this, mCurrID)
                mListLayout.addView(iView.getView())
                mListLayout.visibility = View.VISIBLE
            }
        }
    }

}