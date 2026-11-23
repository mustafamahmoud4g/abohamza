package com.abohamza.sales

import android.app.Activity
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import android.view.View
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var web: WebView
    private val appUrl = "https://visionary-empanada-3cbba9.netlify.app/"

    @Suppress("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
             View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(WebView(this), true)

        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.databaseEnabled = true
        web.settings.cacheMode = WebSettings.LOAD_DEFAULT
        web.settings.useWideViewPort = true
        web.settings.loadWithOverviewMode = true
        web.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView?, request: android.webkit.WebResourceRequest?, error: android.webkit.WebResourceError?) {
                if (request?.isForMainFrame == true) {
                    Toast.makeText(this@MainActivity, "تعذر تحميل البرنامج. تحقق من الإنترنت.", Toast.LENGTH_LONG).show()
                }
            }
        }
        web.webChromeClient = WebChromeClient()
        setContentView(web)
        if (savedInstanceState == null) web.loadUrl(appUrl) else web.restoreState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        web.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (web.canGoBack()) web.goBack() else super.onBackPressed()
    }
}
