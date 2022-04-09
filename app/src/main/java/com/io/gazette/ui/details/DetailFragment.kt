package com.io.gazette.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.io.gazette.R

class DetailFragment : Fragment() {

    private lateinit var newsContentWebView:WebView
    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var newsUrl:String

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
        newsUrl = args.newsUrl
        newsContentWebView.loadUrl(newsUrl)
    }


}