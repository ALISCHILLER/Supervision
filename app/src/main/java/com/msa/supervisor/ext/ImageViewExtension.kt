package com.msa.supervisor.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.msa.supervisor.di.Providers
import com.msa.supervisor.model.api.ApiSupervisor

/**
 * create by Ali Soleymani.
 */


//-------------------------------------------------------------------------------------------------- loadImage
@BindingAdapter("loadImage", "setEntityType")
fun ImageView.loadImage(url: String, entityType: String) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    val link = "${Providers.url}${ApiSupervisor.v1}/Content/file?entityType=$entityType&fileName=$url"
    Glide.with(this).load(link).into(this)
}
//-------------------------------------------------------------------------------------------------- loadImage


//-------------------------------------------------------------------------------------------------- loadImageByToken
@BindingAdapter("loadImageProfile", "bearerToken")
fun ImageView.loadImageByToken(url: String?, token: String) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    val link = "${Providers.url}${ApiSupervisor.v1}/LogIn/get-user-avatar/$url"
    val glideUrl = GlideUrl(
        link,
        LazyHeaders.Builder().addHeader("Authorization", token).build()
    )
    Glide.with(this).load(glideUrl).into(this)
}
//-------------------------------------------------------------------------------------------------- loadImageByToken
