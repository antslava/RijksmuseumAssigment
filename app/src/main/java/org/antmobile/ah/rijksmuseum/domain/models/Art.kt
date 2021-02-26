package org.antmobile.ah.rijksmuseum.domain.models

data class Art(
    val id: String,
    val title: String,
    val longTitle: String,
    val principalOrFirstMaker: String,
    val imageUrl: String? = null,
)
