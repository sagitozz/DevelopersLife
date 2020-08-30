package com.application.developerslife.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * @autor d.sagitov
 */
interface ImageLoader {

    fun loadImage(
        url: String,
        targetView: ImageView,
        onLoad: () -> Unit,
        onFail: () -> Unit,
        isCached: () -> Unit
    )
}

class ImageLoaderImpl : ImageLoader {

    override fun loadImage(
        url: String,
        targetView: ImageView,
        onLoad: () -> Unit,
        onFail: () -> Unit,
        isCached: () -> Unit
    ) {
        Glide
            .with(targetView)
            .asGif()
            .sizeMultiplier(0.4f)
            .load(url)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    p0: GlideException?,
                    p1: Any?,
                    target: Target<GifDrawable>?,
                    p3: Boolean
                ): Boolean {
                    onFail.invoke()
                    return false
                }

                override fun onResourceReady(
                    p2: GifDrawable?,
                    p1: Any?,
                    target: Target<GifDrawable>?,
                    p3: DataSource?,
                    p4: Boolean
                ): Boolean {
                    onLoad.invoke()
                    isCached.invoke()
                    return false
                }
            })
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(targetView)
    }
}
