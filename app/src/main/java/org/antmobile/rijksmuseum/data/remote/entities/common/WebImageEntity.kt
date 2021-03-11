package org.antmobile.rijksmuseum.data.remote.entities.common

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebImageEntity(
    val url: String
)
