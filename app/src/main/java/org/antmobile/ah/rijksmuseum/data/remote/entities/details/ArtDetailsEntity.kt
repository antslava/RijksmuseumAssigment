package org.antmobile.ah.rijksmuseum.data.remote.entities.details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.antmobile.ah.rijksmuseum.data.remote.entities.common.WebImageEntity

@JsonClass(generateAdapter = true)
data class ArtDetailsEntity(
    @Json(name = "objectNumber") val id: String,
    val title: String,
    val description: String?,
    val webImage: WebImageEntity?,
)
