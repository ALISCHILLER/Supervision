package com.msa.supervisor.view.dialog.multiplechoice


import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.R
import com.msa.supervisor.view.adapter.MultioleChoiceAdapter
//create Ali Soleymani
/**
 * create by Ali Soleymani.
 */
class SingleChoiceDialog {

    companion object {
        fun show(
            context: Context,
            title: String,
            icon:Int,
            multiItems: MutableList<MultiItem>,
            selectionCompleteListener: SingleSelectionCompleteListener
        ) {
            val alertDialog = AlertDialog.Builder(context).create()
            val inflater = LayoutInflater.from(context)
            val convertView = inflater.inflate(R.layout.dialog_single_choice, null)
            alertDialog.setView(convertView)

            val txtTitel = convertView.findViewById<TextView>(R.id.txtTitle)
            val iconHeder = convertView.findViewById<ImageView>(R.id.icon)
            val recyclerView = convertView.findViewById<RecyclerView>(R.id.recyclerView)
            val btn_select_all = convertView.findViewById<TextView>(R.id.btn_select_all)
            val btn_close = convertView.findViewById<ImageView>(R.id.btn_close)
            val searchView = convertView.findViewById<SearchView>(R.id.searchView)

            txtTitel.text = title
            icon?.let {
                iconHeder.setImageResource(icon)
            }

            val adapter = MultioleChoiceAdapter(
                context,
                multiItems,
                multiItems,
                object : MultioleChoiceAdapter.ItemClickListener {
                    override fun onItemClicked(
                        item: MultiItem, position: Int, b: Boolean
                    ) {
                        Log.e(TAG, "onItemClicked: $item")
                        val resultList = ArrayList<MultiItem>()
                        multiItems.forEachIndexed { index, multiItem ->
                            multiItem.takeIf { it.id == item.id }?.let {
                                resultList.add(multiItems[index])
                            }
                        }
                        selectionCompleteListener.onCompleteSelection(resultList[0])
                        alertDialog.dismiss()

                    }


                }, true
            )

            recyclerView.itemAnimator = null
            // recyclerView.layoutManager=mLayoutManager
            recyclerView.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    // do something on text submit
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    // do something when text changes
                    adapter.filter.filter(newText)
                    return false
                }
            })
            btn_close.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }
}