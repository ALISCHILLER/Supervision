package com.msa.supervisor.view.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
/**
 * create by Ali Soleymani.
 */
public class MsaButton extends MaterialButton {

    private boolean loading = false;
    private String defaultTitle;


    //---------------------------------------------------------------------------------------------- MsaButton
    public MsaButton(@NonNull Context context) {
        super(context);
    }
    //---------------------------------------------------------------------------------------------- MsaButton


    //---------------------------------------------------------------------------------------------- MsaButton
    public MsaButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //---------------------------------------------------------------------------------------------- MsaButton


    //---------------------------------------------------------------------------------------------- MsaButton
    public MsaButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //---------------------------------------------------------------------------------------------- MsaButton


    //---------------------------------------------------------------------------------------------- stopLoading
    public void stopLoading() {
        loading = false;
        if (defaultTitle != null)
            setText(defaultTitle);
    }
    //---------------------------------------------------------------------------------------------- stopLoading


    //---------------------------------------------------------------------------------------------- startLoading
    public void startLoading(String title) {
        defaultTitle = getText().toString();
        loading = true;
        setText(title);
    }
    //---------------------------------------------------------------------------------------------- startLoading


    //---------------------------------------------------------------------------------------------- isLoading
    public Boolean isLoading() {
        return loading;
    }
    //---------------------------------------------------------------------------------------------- isLoading


}
