package org.antmobile.rijksmuseum.data.remote

import android.content.Context
import org.antmobile.rijksmuseum.R
import java.util.*

class LanguageProvider(
    private val appContext: Context
) {

    fun provideSupportedLanguage(): Language {
        val language = appContext.getString(R.string.language)
        return Language.values().firstOrNull {
            it.name.equals(language, true)
        } ?: Language.EN
    }

}

enum class Language {
    EN, NL;

    override fun toString(): String = super.toString().toLowerCase(Locale.ENGLISH)


}

