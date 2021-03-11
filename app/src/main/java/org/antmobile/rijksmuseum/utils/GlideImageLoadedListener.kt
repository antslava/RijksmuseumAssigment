package org.antmobile.rijksmuseum.utils

import android.graphics.drawable.Drawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GlideImageLoadedListener(
    private val imageLoaded: () -> Unit
) : RequestListener<Drawable?> {

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable?>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        imageLoaded.invoke()
        return false
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable?>?,
        isFirstResource: Boolean
    ): Boolean {
        imageLoaded.invoke()
        return false
    }
}
