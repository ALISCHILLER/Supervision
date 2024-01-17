package com.msa.supervisor.tool

import android.animation.ValueAnimator
import com.facebook.shimmer.Shimmer


/**
 * create by Ali Soleymani.
 */

fun getShimmerBuild() = Shimmer.AlphaHighlightBuilder()
    .setDirection(Shimmer.Direction.RIGHT_TO_LEFT)
    .setDuration(2000L)
    .setRepeatMode(ValueAnimator.REVERSE)
    .setTilt(0f)
    .build()