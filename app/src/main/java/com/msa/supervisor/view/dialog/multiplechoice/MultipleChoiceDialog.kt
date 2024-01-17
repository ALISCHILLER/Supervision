package com.msa.supervisor.view.dialog.multiplechoice


import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.R
import com.msa.supervisor.view.adapter.MultioleChoiceAdapter
//create Ali Soleymani

/**
 * create by Ali Soleymani.
 */
class MultipleChoiceDialog  {


    companion object{
        fun show(
            context: Context,
            title: String,
            doneButtonText:String,
            multiItems: MutableList<MultiItem>,
            selectionCompleteListener: SelectionCompleteListener
        ){
            val alertDialog=AlertDialog.Builder(context).create()
            val inflater=LayoutInflater.from(context)
            val convertView=inflater.inflate(R.layout.dialog_multiple_choice,null)
            alertDialog.setView(convertView)

            val recyclerView = convertView.findViewById<RecyclerView>(R.id.recyclerView)
            val btnSelectAll = convertView.findViewById<TextView>(R.id.btn_select_all)
            val btnClose = convertView.findViewById<ImageView>(R.id.btn_close)
            btnClose.setOnClickListener {
                alertDialog.dismiss()
            }

            GridLayoutManager(context, 2)
            val adapter= MultioleChoiceAdapter(
                context,
                multiItems,
                multiItems,
                object : MultioleChoiceAdapter.ItemClickListener{
                    override fun onItemClicked(
                        item: MultiItem, position: Int, b: Boolean) {

                        multiItems.forEachIndexed { index, multiItem ->
                            multiItem.takeIf { it.id==item.id }?.let {
                                multiItems[index].inSelected=b
                            }
                        }

                    }


                },false)
            btnSelectAll?.setOnClickListener {
                multiItems.forEachIndexed { index, _ ->
                    multiItems[index].inSelected=true
                }
                adapter.notifyDataSetChanged()

            }
            recyclerView.itemAnimator=null
           // recyclerView.layoutManager=mLayoutManager
            recyclerView.adapter=adapter


            convertView.findViewById<ConstraintLayout>(R.id.btn_confirm).setOnClickListener {
                val resultList=ArrayList<MultiItem>()
                multiItems.forEachIndexed { index, multiItem ->
                    multiItem.takeIf { it.inSelected }?.let {
                        resultList.add(multiItems[index])
                    }
                }

                selectionCompleteListener.onCompleteSelection(resultList)
                alertDialog.dismiss()
            }

//            alertDialog.setPositiveButton(doneButtonText){dialogInterface, i ->
//                dialogInterface.dismiss()
//                val resultList=ArrayList<MultiItem>()
//                multiItems.forEachIndexed { index, multiItem ->
//                    multiItem.takeIf { it.inSelected }.let {
//                        resultList.add(multiItems[index])
//                    }
//                }
//                selectionCompleteListener.onCompleteSelection(resultList)
//            }

            alertDialog.show()
        }
    }

}