package com.ws.mysololife.contentsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import com.ws.mysololife.R

class ContentShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_show)

        //url 정보 받아온 후 실행
        val getUrl = intent.getStringExtra("url")
        val webView : WebView = findViewById(R.id.webView)
        webView.loadUrl(getUrl.toString())
    }
}