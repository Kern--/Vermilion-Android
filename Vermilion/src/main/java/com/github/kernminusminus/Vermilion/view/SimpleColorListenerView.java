package com.github.kernminusminus.Vermilion.view;


import android.content.Context;
import android.util.AttributeSet;


public class SimpleColorListenerView extends ColorListenerView {
    public SimpleColorListenerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onBaseColorChange(int color) {
        setBackgroundColor(color);
    }
}
