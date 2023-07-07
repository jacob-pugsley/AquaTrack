package com.example.aquatrack.ui.views

import TanksRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aquatrack.data.tank.Tank
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class TankListViewModel(tanksRepository: TanksRepository) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val tankListUiState: StateFlow<TankListUiState> = tanksRepository.getAllItemsStream().map{ TankListUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TankListUiState()
    )

}

data class TankListUiState(val itemList: List<Tank> = listOf())
