package imageplayer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import com.github.chrisbanes.photoview.PhotoView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.subview.PhotoCommentView
import eightbitlab.com.blurview.BlurView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import util.Util.HideStatusBar
import michaelzhao.BaseActivity
import michaelzhao.R
import util.*
import util.Util.CopyToClipBoard
import eightbitlab.com.blurview.RenderScriptBlur
import android.view.ViewGroup


class ImageViewActivity : BaseActivity() {

    companion object {
        private const val ImageUrl: String = "ImageUrl"
        private const val ImageStartIndex = "ImageStartIndex"
        fun showImages(context: Context, list: List<String>, start: Int = 0) {
            val intent = Intent(context, ImageViewActivity::class.java)
            intent.putExtra(ImageUrl, list.toTypedArray())
            intent.putExtra(ImageStartIndex, start)
            context.startActivity(intent)
        }
    }

    override val mLayout: Int = R.layout.activity_imageview
    private val mTextPage by lazy { find<TextView>(R.id.mTextPage) }
    private val mImageViewPager by lazy { find<ImageViewPager>(R.id.mImageViewPager) }
    private val mBtnClose by lazy { find<ImageButton>(R.id.mBtnClose) }
    private val mBtnMore by lazy { find<ImageButton>(R.id.mBtnMore) }
    private val mBtnComment by lazy { find<ImageButton>(R.id.mBtnComment) }
    private val mBlurView by lazy { find<BlurView>(R.id.mBlurView) }
    private val mMainLayout by lazy { find<CoordinatorLayout>(R.id.mMainLayout) }

    private val mListLayout by lazy { find<FrameLayout>(R.id.mListLayout) }
    private val mCurrIndex get() = mImageViewPager.currentItem
    private var mStartIndex = 0
    private val mListUrl = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imageview)
        HideStatusBar()

        mListUrl.addAll(intent.getStringArrayExtra(ImageUrl))
        mStartIndex = intent.getIntExtra(ImageStartIndex, 0)

        initUI()
    }

    private fun initUI() {
        if (mListUrl.isEmpty()) {
            toast("未找到任何图片链接")
            finish()
            return
        } else {
            mImageViewPager.adapter = ImagePagerAdapter(this, mListUrl)
            val cnt = mListUrl.size
            if (cnt > 1) {
                mTextPage.text = "${mCurrIndex + 1} / $cnt"
            }
            mImageViewPager.setPageChangeListen { index ->
                mTextPage.text = "${index + 1} / $cnt"
                switchComment()
            }
        }
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

        setUpBlurView()
    }

    private fun setUpBlurView() {
        val radius = 5f
        val decorView = window.decorView
        val rootView = mMainLayout as ViewGroup
        val windowBackground = decorView.background
        mBlurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true)
    }

    private fun showImageSrc() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("图片源")
        val url = mListUrl[mCurrIndex]
        dialog.setMessage(url)
        dialog.setPositiveButton("关闭", null)
        dialog.setNegativeButton("复制") { _, _ ->
            CopyToClipBoard(this, url)
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

    private fun getCurrPhotoID(): String {
        try {
            val url = mListUrl[mCurrIndex]
            return url.split("public/p")[1].split(".")[0]
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return ""
    }

    private fun showComment() {
        if (mListLayout.isShown || getCurrPhotoID().isBlank()) {
            mListLayout.visibility = View.GONE
            mBlurView.setOverlayColor(Color.parseColor("#00FFFFFF"))
            mBlurView.setBlurEnabled(false)
        } else {
            showListComment()
        }
    }

    private fun switchComment() {
        if (mListLayout.isShown) {
            showListComment()
        }
    }

    private fun showListComment() {
        mListLayout.removeAllViews()
        val view = PhotoCommentView(this, getCurrPhotoID()).getView()
        mListLayout.addView(view)
        view.setHeight((BaseActivity.getScreenSize().y * 0.30).toInt())
        mListLayout.visibility = View.VISIBLE
        mBlurView.setOverlayColor(Color.parseColor("#22FFFFFF"))
        mBlurView.setBlurEnabled(true)
    }

}