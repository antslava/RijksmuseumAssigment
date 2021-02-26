package org.antmobile.ah.rijksmuseum.domain.models

data class ArtsPage(
    val listOfArts: List<Art>,
    val canLoadMore: Boolean
)
