package michaelzhao

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import netsearch.BTSearch
import org.jetbrains.anko.find
import util.Util.CopyToClipBoard
import util.uiThread

class BTSearchActivity : BaseActivity() {

    override val mLayout: Int = R.layout.activity_webview

    private val mWebView by lazy { find<WebView>(R.id.web_view) }
    private var mWebUrl = BTSearch.mBaseUrl
    private var mWebTitle = "BT 磁力搜索"

    private var mSnackBar: Snackbar? = null

    private var mOnBTListen: (str: String) -> Unit = { str ->
        mSnackBar = Snackbar.make(mWebView, "是否复制磁力链接？", Snackbar.LENGTH_SHORT)
        mSnackBar?.setAction("复制") {
            CopyToClipBoard(this, str)
        }
        mSnackBar?.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBarIcon(GoogleMaterial.Icon.gmd_arrow_back)
        mToolBar.setNavigationOnClickListener { finish() }
        setToolBarTitle(mWebTitle)
        mWebView.settings.javaScriptEnabled = true
        mWebView.settings.blockNetworkImage = true
        mWebView.webViewClient = WebClient(mWebView, mSwipeLayout, mOnBTListen)
        mWebView.loadUrl(mWebUrl)
    }


    class WebClient(webView: WebView, swipe: SwipeRefreshLayout, listen: (str: String) -> Unit) : WebViewClient() {

        private val mSwipe = swipe
        private val mListen = listen
        private val mWebView = webView

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            mSwipe.isRefreshing = true
            mSwipe.isEnabled = true
            uiThread(1000) { mSwipe.isRefreshing = false }
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageCommitVisible(view: WebView?, url: String?) {
            super.onPageCommitVisible(view, url)
            mSwipe.isRefreshing = false
            mSwipe.isEnabled = false

            mWebView.evaluateJavascript("document.getElementsByTagName(\"html\")[0].innerHTML") { value ->
                val result = value.replace("\\u003C", "<")
                        .replace("\\\"", "\"")
                        .replace("\\n", "\n")
                        .replace("\\t", "\t")

                if (result.contains("magnet:?xt=")) {
                    try {
                        mListen.invoke("magnet:?xt=" + result.split("magnet:?xt=")[1].split("\"")[0])
                    } catch (ex: Exception) {
                        println(ex.message)
                    }
                }
            }

        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request?.url.toString()

            if (url.startsWith("magnet:?xt=")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                App.Instance.startActivity(intent)
                return true
            }

            return super.shouldOverrideUrlLoading(view, request)
        }


    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        mSnackBar?.dismiss()

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

}