package com.github.kernminusminus.Vermillion;

import android.graphics.Color;

public class ColorBoundary {
    private float mHue;

    public ColorBoundary(float hue){
        mHue = hue;
    }

    public float getHue() {
        return mHue;
    }

    public void setHue(float hue) {
        mHue = hue;
    }

}