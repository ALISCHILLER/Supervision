package com.msa.supervisor.tool.customtoast
//create Ali Soleymani

/**
 * create by Ali Soleymani.
 */
enum class MsaToastStyle {

    SUCCESS, ERROR, WARNING, INFO, DELETE, NO_INTERNET;

    fun getName(): String {
        if (this.name.contains("_")) {
            return this.name.replaceFirst("_", " ")
        }
        return this.name
    }
}