package com.github.kernminusminus.Vermilion.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.github.kernminusminus.Vermilion.model.ColorModel;

public abstract class ColorListenerView extends View implements ColorModel.OnBaseColorChangeListener {


    public ColorListenerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void listenForColorChange(String color) {
        ColorModel.getInstance().addBaseColorChangeListener(color, this);
    }
}
