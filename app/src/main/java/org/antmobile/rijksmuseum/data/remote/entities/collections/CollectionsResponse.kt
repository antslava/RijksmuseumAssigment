package org.antmobile.rijksmuseum.data.remote.entities.collections

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionsResponse(
    var count: Int,
    var artObjects: List<ArtEntity>
)
