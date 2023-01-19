package com.angelstudios.framework.global.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

private fun setImage(
    imageView: ImageView,
    imageUrl: String?,
    placeHolder: Drawable,
    picasso: Picasso,
    transformation: Transformation? = null
) {

    imageUrl?.let {
        if (imageUrl.isEmpty()) {
            imageView.setImageDrawable(placeHolder)
        } else {

            var rc = picasso.load(it).fit().placeholder(placeHolder)

            rc = when (imageView.scaleType) {
                ImageView.ScaleType.CENTER_CROP -> rc.centerCrop()
                ImageView.ScaleType.CENTER_INSIDE -> rc.centerInside()
                else -> rc
            }

            transformation?.let { rc.transform(transformation) }

            rc.into(imageView)
        }
    }

}
