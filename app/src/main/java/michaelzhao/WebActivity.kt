package michaelzhao

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import org.jetbrains.anko.find

class WebActivity : BaseActivity() {

    companion object {
        private const val WEB_URL = "web_url"
        private const val WEB_TITLE = "web_title"

        fun ShowWebView(context: Context, url: String, title: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(WEB_URL, url)
            intent.putExtra(WEB_TITLE, title)
            context.startActivity(intent)
        }
    }

    override val mLayout: Int = R.layout.activity_webview

    private val mWebView by lazy { find<WebView>(R.id.web_view) }
    private var mWebUrl = ""
    private var mWebTitle = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBarIcon(GoogleMaterial.Icon.gmd_arrow_back)
        mToolBar.setNavigationOnClickListener { finish() }
        mWebTitle = intent.getStringExtra(WEB_TITLE)
        setToolBarTitle(mWebTitle)
        mWebUrl = intent.getStringExtra(WEB_URL)
        mWebView.settings.javaScriptEnabled = true
        mWebView.webViewClient = WebClient(mSwipeLayout)
        mWebView.loadUrl(mWebUrl)
        mSwipeLayout.ShowRefresh()
    }

    class WebClient(swipe: SwipeRefreshLayout) : WebViewClient() {
        private val mSwipe = swipe
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            mSwipe.isRefreshing = false
            mSwipe.isEnabled = false
        }
    }

}