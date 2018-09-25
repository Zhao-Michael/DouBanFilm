package imageplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import kotlinx.android.synthetic.main.activity_imageview.*
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import org.jetbrains.anko.toast
import util.OnClick
import util.setIcon
import venerealulcer.BaseActivity
import venerealulcer.R


class ImageViewActivity : BaseActivity() {

    companion object {
        const val ImageUrl: String = "ImageUrl"
        fun ShowImages(context: Context, list: List<String>) {
            val intent = Intent(context, ImageViewActivity::class.java)
            intent.putExtra(ImageUrl, list.toTypedArray())
            context.startActivity(intent)
        }
    }

    override val mLayout: Int = R.layout.activity_imageview
    private val mTextPage by lazy { find<TextView>(R.id.mTextPage) }
    private val mImageViewPager by lazy { find<ImageViewPager>(R.id.mImageViewPager) }
    private val mBtnClose by lazy { find<ImageButton>(R.id.mBtnClose) }
    private val mTextTitle by lazy { find<TextView>(R.id.mTextTitle) }
    private val mCurrIndex get() = mImageViewPager.currentItem

    private val mListUrl = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imageview)

        mListUrl.addAll(intent.getStringArrayExtra(ImageUrl))

        if (mListUrl.isEmpty()) {
            toast("未找到任何图片链接")
            finish()
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

        mBtnMore.setIcon(GoogleMaterial.Icon.gmd_more_vert)
        mBtnClose.setIcon(GoogleMaterial.Icon.gmd_close)
        mBtnClose.OnClick { finish() }
    }
}