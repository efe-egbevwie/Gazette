package com.io.gazette

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.io.gazette.domain.useCases.MainViewModelUseCases

class MainViewModelFactory(private val mainViewModelUseCases: MainViewModelUseCases) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mainViewModelUseCases) as T
        } else {
            throw IllegalArgumentException("unknown viewModel class")
        }
    }
}