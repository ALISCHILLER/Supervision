package com.msa.supervisor.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.R
import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import java.util.Locale
/**
 * create by Ali Soleymani.
 */
class MultioleChoiceAdapter(
    internal var context:Context,
    private val mValues:List<MultiItem>,
    private var filteredList:List<MultiItem>,
    clickListener: ItemClickListener,
    var singleSelection:Boolean=false
):Filterable, RecyclerView.Adapter<MultioleChoiceAdapter.ViewHolder>() {
    private var itemClickListener: ItemClickListener = clickListener

    inner class ViewHolder(val mView: View) :
        RecyclerView.ViewHolder(mView) {
        internal var titleTxt=mView.findViewById<TextView>(R.id.titleTextView)
        internal var checkBox=mView.findViewById<CheckBox>(R.id.checkBox)
        var mItem: MultiItem?=null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view=LayoutInflater.from(parent.context)
           .inflate(R.layout.row_multi_select_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = filteredList[holder.adapterPosition]
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.titleTxt.text = holder.mItem!!.name
        holder.checkBox.isChecked = holder.mItem!!.inSelected
        var productPosition = 0

        if(singleSelection){
            holder.checkBox.visibility=View.GONE
        }else{
            holder.checkBox.visibility=View.VISIBLE
        }
        mValues.forEachIndexed { index, multiItem ->
            multiItem.takeIf { it.id == holder.mItem?.id }?.let {
                productPosition=index
            }
        }


        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            itemClickListener.onItemClicked(
                filteredList[holder.adapterPosition],
                productPosition,
                isChecked
            )
        }
        if(singleSelection)
        holder.titleTxt.setOnClickListener {
            itemClickListener.onItemClicked(
                filteredList[holder.adapterPosition],
                productPosition,
                false
            )
        }


        holder.mView.setOnClickListener { _ ->
            holder.checkBox.isChecked = holder.checkBox.isChecked
        }



    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filteredList = mValues
                } else {
                    val tempList = ArrayList<MultiItem>()
                    for (row in mValues) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name.lowercase(Locale.getDefault())
                                .contains(charString.lowercase(Locale.getDefault()))
                        ) {
                            tempList.add(row)
                        }
                    }

                    filteredList = tempList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
                filteredList = filterResults.values as ArrayList<MultiItem>

                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        lateinit var itemClickListener: ItemClickListener
    }
    interface ItemClickListener {
        fun onItemClicked(item: MultiItem, position: Int, b: Boolean)
    }
}