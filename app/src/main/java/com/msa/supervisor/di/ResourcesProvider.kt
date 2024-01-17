package com.msa.supervisor.di

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * create by Ali Soleymani.
 */
@Singleton
class ResourcesProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {

    //---------------------------------------------------------------------------------------------- getString
    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }
    //---------------------------------------------------------------------------------------------- getString

}