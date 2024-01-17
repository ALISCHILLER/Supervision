package com.msa.supervisor.ext

import android.view.View
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout


/**
 * create by Ali Soleymani.
 */

fun ShimmerFrameLayout.config(shimmer: Shimmer) {
    setShimmer(shimmer)
    hideShimmer()
    visibility = View.GONE
}


fun ShimmerFrameLayout.startLoading() {
    visibility = View.VISIBLE
    showShimmer(true)
}


fun ShimmerFrameLayout.stopLoading() {
    hideShimmer()
    visibility = View.GONE
}