package org.antmobile.ah.rijksmuseum.utils

import kotlinx.coroutines.CoroutineDispatcher

data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val io: CoroutineDispatcher
)
