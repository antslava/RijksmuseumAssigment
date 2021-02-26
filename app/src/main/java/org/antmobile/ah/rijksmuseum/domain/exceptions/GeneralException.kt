package org.antmobile.ah.rijksmuseum.domain.exceptions

class GeneralException(
    message: String?,
    cause: Throwable?
) : Exception(message, cause)
