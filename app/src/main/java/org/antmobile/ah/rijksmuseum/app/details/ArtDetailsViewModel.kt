package org.antmobile.ah.rijksmuseum.app.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.antmobile.ah.rijksmuseum.app.converters.ErrorConverter
import org.antmobile.ah.rijksmuseum.domain.models.ArtDetails
import org.antmobile.ah.rijksmuseum.domain.usecases.GetArtDetailsByIdUseCase
import org.antmobile.ah.rijksmuseum.utils.Result

class ArtDetailViewModel(
    private val getArtDetailsByIdUseCase: GetArtDetailsByIdUseCase,
    private val errorConverter: ErrorConverter
) : ViewModel() {

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress
        get() = _showProgress

    private val _uiState = MutableLiveData<ArtDetailUiModel>()
    val uiState: LiveData<ArtDetailUiModel>
        get() = _uiState

    private var loadArtDetailsJob: Job? = null

    fun loadDetails(artId: String) {
        if (loadArtDetailsJob?.isActive == true) {
            return
        }

        loadArtDetailsJob = viewModelScope.launch {
            _showProgress.value = true
            when (val result = getArtDetailsByIdUseCase.execute(artId)) {
                is Result.Success -> _uiState.value = ArtDetailUiModel.ShowContent(result.data)
                is Result.Failure -> _uiState.value = ArtDetailUiModel.ShowError(
                    errorConverter.toErrorMessage(result.exception)
                ) { loadDetails(artId) }
            }
            _showProgress.value = false
        }
    }
}

// region ArtDetailUiModel
///////////////////////////////////////////////////////////////////////////

/**
 * Ui model for [ArtDetailsFragment]
 */
sealed class ArtDetailUiModel {
    data class ShowContent(val artDetails: ArtDetails) : ArtDetailUiModel()
    data class ShowError(
        val errorMessage: String,
        val retryCallback: () -> Unit
    ) :
        ArtDetailUiModel()
}

// endregion
