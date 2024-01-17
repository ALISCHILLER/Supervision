package com.msa.supervisor.view.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.msa.supervisor.R



/**
 * create by Ali Soleymani.
 */
class CustomMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var iconImage: ImageView
    private lateinit var titleText: TextView
    private lateinit var linearLayoutParent: LinearLayout
    private val defaultColor = R.color.disableText
    private val selectedColor = R.color.textColor
    private var icon: Int = 0
    private var icon_select: Int = 0
    private var textsize: Float = 0.0f

    //---------------------------------------------------------------------------------------------- init
    init {
        init(attrs)
    }
    //---------------------------------------------------------------------------------------------- init


    //---------------------------------------------------------------------------------------------- init
    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_main_footer, this)

        iconImage = findViewById(R.id.image_thumb)
        titleText = findViewById(R.id.text_title)
        linearLayoutParent = findViewById(R.id.linearLayoutParent)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.CustomMenu)
        try {
            val text = ta.getString(R.styleable.CustomMenu_menu_title)
            icon = ta.getResourceId(R.styleable.CustomMenu_menu_icon, 0)
            icon_select = ta.getResourceId(R.styleable.CustomMenu_menu_icon_select, 0)
            textsize = ta.getDimensionPixelSize(R.styleable.CustomMenu_menu_textSize, 0) /
                    resources.displayMetrics.density
            textsize += textsize * 20 /100
            titleText.text = text
            setImageIcon(icon)
            clearSelected()
        } finally {
            ta.recycle()
        }

        setOnClickListener {
            selected()
        }
    }
    //---------------------------------------------------------------------------------------------- init


    //---------------------------------------------------------------------------------------------- selected
    fun selected() {
        setImageIcon(icon_select)
        titleText.setTextColor(context.getColor(selectedColor))
        titleText.textSize = textsize
    }
    //---------------------------------------------------------------------------------------------- selected


    //---------------------------------------------------------------------------------------------- clearSelected
    fun clearSelected() {
        setImageIcon(icon)
        titleText.setTextColor(context.getColor(defaultColor))
        titleText.textSize = (textsize * 80 / 100)
        Log.e("meri", "text size ${(textsize * 80 / 100)}")
    }
    //---------------------------------------------------------------------------------------------- clearSelected


    //---------------------------------------------------------------------------------------------- setImageIcon
    private fun setImageIcon(icon: Int) {
        if (icon == 0)
            return
        val drawable = AppCompatResources.getDrawable(context, icon)
        iconImage.setImageDrawable(drawable)
    }
    //---------------------------------------------------------------------------------------------- setImageIcon

}