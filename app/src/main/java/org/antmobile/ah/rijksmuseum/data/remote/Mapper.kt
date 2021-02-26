package org.antmobile.ah.rijksmuseum.data.remote

import org.antmobile.ah.rijksmuseum.data.remote.entities.collections.ArtEntity
import org.antmobile.ah.rijksmuseum.data.remote.entities.details.ArtDetailsEntity
import org.antmobile.ah.rijksmuseum.domain.models.Art
import org.antmobile.ah.rijksmuseum.domain.models.ArtDetails

fun ArtEntity.toArt(): Art = Art(
    id = id,
    title = title,
    longTitle = longTitle,
    principalOrFirstMaker = principalOrFirstMaker,
    imageUrl = webImage?.url ?: ""
)

fun ArtDetailsEntity.toArtDetails() = ArtDetails(
    id = id,
    title = title,
    description = description ?: "",
    imageUrl = webImage?.url ?: ""
)
