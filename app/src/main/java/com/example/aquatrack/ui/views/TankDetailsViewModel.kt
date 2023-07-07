package com.example.aquatrack.ui.views

import TanksRepository
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TankDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tanksRepository: TanksRepository
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle["tankid"])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val uiState: StateFlow<ItemDetailsUiState> =
        tanksRepository.getItemStream(itemId)
            .filterNotNull()
            .map {
                ItemDetailsUiState(itemDetails = it.toItemDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemDetailsUiState()
            )
}

/**
 * UI state for ItemDetailsScreen
 */
data class ItemDetailsUiState(
    val itemDetails: ItemDetails = ItemDetails()
)




