package org.antmobile.rijksmuseum.domain.models

data class ArtsPage(
    val listOfArts: List<Art>,
    val canLoadMore: Boolean
)
