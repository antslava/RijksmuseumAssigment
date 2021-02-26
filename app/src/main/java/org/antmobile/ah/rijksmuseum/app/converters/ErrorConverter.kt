package org.antmobile.ah.rijksmuseum.app.converters

import android.content.Context
import org.antmobile.ah.rijksmuseum.R
import org.antmobile.ah.rijksmuseum.domain.exceptions.NoInternetException
import org.antmobile.ah.rijksmuseum.domain.exceptions.WrongArtIdException

interface ErrorConverter {
    fun toErrorMessage(error: Exception): String
}

// region ErrorConverterImpl
///////////////////////////////////////////////////////////////////////////

class ErrorConverterImpl(
    private val appContext: Context
) : ErrorConverter {

    override fun toErrorMessage(error: Exception): String = when (error) {
        is NoInternetException -> appContext.getString(R.string.error_no_internet_connection)
        is WrongArtIdException -> appContext.getString(R.string.error_wrong_art_id, error.artId)
        else -> appContext.getString(R.string.error_general)
    }
}

// endregion
