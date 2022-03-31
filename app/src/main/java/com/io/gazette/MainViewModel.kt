package com.io.gazette

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.io.gazette.domain.useCases.MainViewModelUseCases
import com.io.gazette.ui.home.BusinessNewsScreenState
import com.io.gazette.ui.home.HealthNewsScreenState
import com.io.gazette.ui.home.SportsNewsScreenState
import com.io.gazette.ui.home.WorldNewsScreenState
import kotlinx.coroutines.flow.onEach

class MainViewModel(private val mainViewModelUseCases: MainViewModelUseCases) : ViewModel() {

    private val _worldNewsScreenState = mutableStateOf(WorldNewsScreenState())
    val worldNewsScreenState get() = _worldNewsScreenState

    private val _businessNewsScreenState = mutableStateOf(BusinessNewsScreenState())
    val businessNewsScreenState get() = _businessNewsScreenState

    private val _sportsNewsScreenState = mutableStateOf(SportsNewsScreenState())
    val sportsNewsScreenState get() = _sportsNewsScreenState

    private val _healthNewsScreenState = mutableStateOf(HealthNewsScreenState())
    val healthNewsScreenState get() = _healthNewsScreenState

    fun getWorldNews() {
        mainViewModelUseCases.getWorldNewsUseCase().onEach { result ->
            when (result) {
                is Success -> _worldNewsScreenState.value =
                    WorldNewsScreenState(worldNews = result.data)
                is Failure -> _worldNewsScreenState.value =
                    WorldNewsScreenState(worldNewsErrorMessage = result.errorMessage)
                is Loading -> _worldNewsScreenState.value =
                    WorldNewsScreenState(isLoadingWorldNews = true)
            }

        }
    }

    fun getBusinessNews() {
        mainViewModelUseCases.getBusinessUseCase().onEach { result ->
            when (result) {
                is Success -> _businessNewsScreenState.value =
                    BusinessNewsScreenState(businessNews = result.data)
                is Failure -> _businessNewsScreenState.value =
                    BusinessNewsScreenState(errorMessage = result.errorMessage)
                is Loading -> _businessNewsScreenState.value =
                    BusinessNewsScreenState(isLoading = true)
            }

        }
    }

    fun getSportsNews() {
        mainViewModelUseCases.getSportsNewsUseCase().onEach { result ->
            when (result) {
                is Success -> _sportsNewsScreenState.value =
                    SportsNewsScreenState(sportsNews = result.data)
                is Failure -> _sportsNewsScreenState.value =
                    SportsNewsScreenState(errorMessage = result.errorMessage)
                is Loading -> _sportsNewsScreenState.value = SportsNewsScreenState(isLoading = true)
            }

        }
    }

    fun getHealthNews() {
        mainViewModelUseCases.getHealthNewsUseCase().onEach { result ->
            when (result) {
                is Success -> _healthNewsScreenState.value =
                    HealthNewsScreenState(healthNews = result.data)
                is Failure -> _healthNewsScreenState.value =
                    HealthNewsScreenState(errorMessage = result.errorMessage)
                is Loading -> _healthNewsScreenState.value = HealthNewsScreenState(isLoading = true)
            }

        }
    }

}