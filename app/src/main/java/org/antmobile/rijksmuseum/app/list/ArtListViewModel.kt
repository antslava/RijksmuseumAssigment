package org.antmobile.rijksmuseum.app.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.antmobile.rijksmuseum.app.converters.ErrorConverter
import org.antmobile.rijksmuseum.domain.usecases.GetListOfArtsUseCase
import org.antmobile.rijksmuseum.utils.Result

class ArtListViewModel(
    private val getListOfArtsUseCase: GetListOfArtsUseCase,
    private val errorConverter: ErrorConverter,
    private val artListUiItemMerger: ArtListUiItemMerger
) : ViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _artListUiItems = MutableLiveData<List<ArtListUiItem>>()
    val artListUiItems: LiveData<List<ArtListUiItem>>
        get() = _artListUiItems

    private val _artListLoadingError = MutableLiveData<ArtListLoadingError?>()
    val artListLoadingError: LiveData<ArtListLoadingError?>
        get() = _artListLoadingError

    private var loadingArtsJob: Job? = null

    private var nextPage = 0

    init {
        refresh()
    }

    fun isContentLoading() = loadingArtsJob?.isActive == true

    fun refresh() {
        viewModelScope.launch {
            _showLoading.value = true
            loadingArtsJob?.cancel()
            _artListLoadingError.value = null
            _artListUiItems.value = emptyList()
            nextPage = 0
            load(nextPage)
            _showLoading.value = false
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            load(nextPage)
        }
    }

    private fun load(page: Int) {
        if (isContentLoading()) {
            return
        }
        loadingArtsJob = viewModelScope.launch {
            when (val result = getListOfArtsUseCase.execute(page)) {
                is Result.Failure -> {
                    _artListLoadingError.value = ArtListLoadingError(
                        errorMessage = errorConverter.toErrorMessage(result.exception),
                        retryCallback = { viewModelScope.launch { load(nextPage) } }
                    )
                }
                is Result.Success -> {
                    _artListUiItems.value = artListUiItemMerger.merge(
                        _artListUiItems.value.orEmpty(),
                        result.data.listOfArts,
                        result.data.canLoadMore
                    )
                    nextPage++
                    _artListLoadingError.value = null
                }
            }
        }
    }

}
