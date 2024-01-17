package com.msa.supervisor.view.dialog.multiplechoice

import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import com.msa.supervisor.view.dialog.multiplechoice.SearchableItem
/**
 * create by Ali Soleymani.
 */
interface SingleSelectionCompleteListener {
    fun onCompleteSelection(selectedItem: MultiItem)
}