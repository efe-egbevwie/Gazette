package com.io.gazette.ui.home.healthNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.io.gazette.App
import com.io.gazette.MainViewModel
import com.io.gazette.MainViewModelFactory
import com.io.gazette.R
import com.io.gazette.ui.components.NewsList


class HealthNewsFragment : Fragment() {

    private val mainViewModelUseCases = App.useCasesModule.mainViewModelUseCases

    private val factory: MainViewModelFactory = MainViewModelFactory(mainViewModelUseCases)
    private val viewModel: MainViewModel by activityViewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health_news, container, false).apply {
            findViewById<ComposeView>(R.id.health_news_compose_view).setContent { HealthNewsContent() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.healthNewsScreenState.value.healthNews.isEmpty()) viewModel.getHealthNews()
    }


    @Composable
    fun HealthNewsContent() {
        val state = viewModel.healthNewsScreenState.value

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            if (state.healthNews.isNotEmpty()) NewsList(
                newsItems = state.healthNews,
                onItemClick = {})

        }
    }

}