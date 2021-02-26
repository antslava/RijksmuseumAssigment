package org.antmobile.ah.rijksmuseum.data.remote.entities.collections

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.antmobile.ah.rijksmuseum.data.remote.entities.common.WebImageEntity

@JsonClass(generateAdapter = true)
data class ArtEntity(
    @Json(name = "objectNumber") val id: String,
    val title: String,
    val longTitle: String,
    val principalOrFirstMaker: String,
    val webImage: WebImageEntity? = null,
)
