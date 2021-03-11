package org.antmobile.rijksmuseum.utils

import org.antmobile.rijksmuseum.domain.exceptions.GeneralException
import org.antmobile.rijksmuseum.domain.exceptions.NoInternetException
import java.io.IOException

/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [Result.Failure] is
 * created based on the [errorMessage].
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (ex: IOException) {
        Result.Failure(NoInternetException(ex))
    } catch (e: Exception) {
        Result.Failure(GeneralException(errorMessage, e))
    }
}

