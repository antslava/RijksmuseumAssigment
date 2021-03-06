package org.antmobile.rijksmuseum.app.list

import org.antmobile.rijksmuseum.domain.models.Art

sealed class ArtListUiItem {
    data class Item(val art: Art) : ArtListUiItem()
    data class Section(val title: String) : ArtListUiItem()
    object LoadMore : ArtListUiItem()
}

data class ArtListLoadingError(
    val errorMessage: String,
    val retryCallback: () -> Unit
)
