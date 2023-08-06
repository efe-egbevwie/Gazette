package com.io.gazette

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.io.gazette.common.OneTimeEvent
import com.io.gazette.data.repositories.NytRepository
import com.io.gazette.domain.models.GetDataResult
import com.io.gazette.news.businessNews.BusinessNewsScreenState
import com.io.gazette.news.healthNews.HealthNewsScreenState
import com.io.gazette.news.sportsNews.SportsNewsScreenState
import com.io.gazette.news.worldNews.WorldNewsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NytRepository) : ViewModel() {


    init {
        Timber.i("viewModel created")
    }

    private val _worldNewsState = MutableStateFlow(WorldNewsScreenState())
    val worldNewsState = _worldNewsState.asStateFlow()


    private val _businessNewsScreenState = MutableStateFlow(BusinessNewsScreenState())
    val businessNewsScreenState = _businessNewsScreenState.asStateFlow()

    private val _sportsNewsScreenState = MutableStateFlow(SportsNewsScreenState())
    val sportsNewsScreenState = _sportsNewsScreenState.asStateFlow()

    private val _healthNewsScreenState = MutableStateFlow(HealthNewsScreenState())
    val healthNewsScreenState = _healthNewsScreenState.asStateFlow()

    fun getWorldNews() {
        viewModelScope.launch {

            _worldNewsState.update { currentState ->
                currentState.copy(isLoadingWorldNews = true)
            }

            newsRepository.getWorldNews().let { result ->
                when (result) {
                    is GetDataResult.Success -> {
                        _worldNewsState.update { currentState ->
                            currentState.copy(isLoadingWorldNews = false)
                        }
                        result.data?.collect {
                            Timber.i("the world news: $it")
                            _worldNewsState.update { currentState ->
                                currentState.copy(worldNews = it)
                            }
                        }
                    }

                    is GetDataResult.Failure -> {
                        _worldNewsState.update { currentState ->
                            currentState.copy(
                                isLoadingWorldNews = false,
                                error = OneTimeEvent(result.exception)
                            )
                        }
                    }
                }
            }
        }

    }

    fun getBusinessNews() {
        viewModelScope.launch {

            _businessNewsScreenState.update { currentState ->
                currentState.copy(isLoading = true)
            }

            newsRepository.getBusinessNews().let { result ->
                when (result) {
                    is GetDataResult.Success -> {
                        _businessNewsScreenState.update { currentState ->
                            currentState.copy(isLoading = false)
                        }
                        result.data?.collect { newsFromRepo ->
                            _businessNewsScreenState.update { currentState ->
                                currentState.copy(businessNews = newsFromRepo)
                            }
                        }
                    }

                    is GetDataResult.Failure -> {
                        _businessNewsScreenState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = OneTimeEvent(result.exception)
                            )
                        }
                    }
                }
            }
        }
    }

    fun getSportsNews() {
        viewModelScope.launch {

            _sportsNewsScreenState.update { currentState ->
                currentState.copy(isLoading = true)
            }

            newsRepository.getSportsNews().let { result ->
                when (result) {
                    is GetDataResult.Success -> {
                        _sportsNewsScreenState.update { currentState ->
                            currentState.copy(isLoading = false)
                        }
                        result.data?.collect { newsFromRepo ->
                            _sportsNewsScreenState.update { currentState ->
                                currentState.copy(sportsNews = newsFromRepo)
                            }
                        }
                    }

                    is GetDataResult.Failure -> {
                        _sportsNewsScreenState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = OneTimeEvent(result.exception)
                            )
                        }
                    }
                }
            }
        }
    }

    fun getHealthNews() {
        viewModelScope.launch {

            _healthNewsScreenState.update { currentState ->
                currentState.copy(isLoading = true)
            }

            newsRepository.getHealthNews().let { result ->
                when (result) {
                    is GetDataResult.Success -> {
                        _healthNewsScreenState.update { currentState ->
                            currentState.copy(isLoading = false)
                        }
                        result.data?.collect { newsFromRepo ->
                            _healthNewsScreenState.update { currentState ->
                                currentState.copy(healthNews = newsFromRepo)
                            }
                        }
                    }

                    is GetDataResult.Failure -> {
                        _healthNewsScreenState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = OneTimeEvent(result.exception)
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {

        fun factory(newsRepository: NytRepository): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    MainViewModel(newsRepository)
                }
            }
        }
    }

}