package org.antmobile.rijksmuseum.domain.exceptions

class GeneralException(
    message: String?,
    cause: Throwable?
) : Exception(message, cause)
