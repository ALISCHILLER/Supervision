package com.msa.supervisor.view.dialog.progress

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.mackhartley.roundedprogressbar.RoundedProgressBar
import com.msa.supervisor.R
/**
 * create by Ali Soleymani.
 */

//create Ali Soleymani
class ProgressBarDialog(val context: Context,var titel:String) {

    private lateinit var dialog:AlertDialog
    private lateinit var progress:RoundedProgressBar

    fun startLoading(){
        dialog= AlertDialog.Builder(context).create()
        dialog.setCancelable(false)
        val inflater= LayoutInflater.from(context)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val convertView=inflater.inflate(R.layout.dialog_layout_progressbar,null)
        dialog.setView(convertView)
        progress=convertView.findViewById(R.id.progress_bar)


        val texTitle=convertView.findViewById<TextView>(R.id.texttitle)
        val loading=convertView.findViewById<LottieAnimationView>(R.id.imageView)
        texTitle.text=titel

        loading.setAnimation("downloading.json")
        loading.setBackgroundColor(Color.TRANSPARENT)
        loading.loop(true)
        loading.playAnimation()
        loading.visibility = View.VISIBLE
        progress.setProgressPercentage(0.0)
        dialog.show()
    }

    fun setProgress(f:Double){
       val p= progress.getProgressPercentage()
        progress.setProgressPercentage(p+f)
    }
    fun stop(){
      dialog.dismiss()
    }

}