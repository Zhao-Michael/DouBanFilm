package imageplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import kotlinx.android.synthetic.main.activity_imageview.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import util.OnClick
import util.Util.HideStatusBar
import util.setIcon
import michaelzhao.BaseActivity
import michaelzhao.R
import util.Hide


class ImageViewActivity : BaseActivity() {

    companion object {
        private const val ImageUrl: String = "ImageUrl"
        private const val ImageStartIndex = "ImageStartIndex"
        fun ShowImages(context: Context, list: List<String>, start: Int = 0) {
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
    private val mTextTitle by lazy { find<TextView>(R.id.mTextTitle) }
    private val mCurrIndex get() = mImageViewPager.currentItem
    private var mStartIndex = 0
    private val mListUrl = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imageview)
        HideStatusBar()
        mListUrl.addAll(intent.getStringArrayExtra(ImageUrl))
        mStartIndex = intent.getIntExtra(ImageStartIndex, 0)
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
                mTextPage.text = "${index + 1} / $cnt"
                mTextTitle.text = mListUrl[index]
            }
        }
        mTextTitle.Hide()
        mImageViewPager.currentItem = mStartIndex
        mBtnMore.setIcon(GoogleMaterial.Icon.gmd_more_vert)
        mBtnClose.setIcon(GoogleMaterial.Icon.gmd_close)
        mBtnClose.OnClick { finish() }
    }
}