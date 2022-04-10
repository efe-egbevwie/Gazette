package com.io.gazette.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.io.gazette.R
import com.io.gazette.utils.invisible
import com.io.gazette.utils.visible

class DetailFragment : Fragment() {

    private lateinit var newsContentWebView: WebView
    private lateinit var progressBar: ProgressBar
    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var newsUrl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsContentWebView = view.findViewById(R.id.news_content_webView)
        newsContentWebView.webViewClient = WebViewClient()
        progressBar = view.findViewById(R.id.news_detail_progress_bar)
        newsUrl = args.newsUrl
        newsContentWebView.loadUrl(newsUrl)
    }


    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            progressBar.visible()
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.invisible()
        }
    }


}