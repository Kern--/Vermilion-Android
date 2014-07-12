package com.github.kernminusminus.Vermilion.model;

import android.graphics.Color;

public class ColorRegion {
    private int mBaseColor;
    private ColorBoundary mUpperBound;
    private ColorBoundary mLowerBound;


    public ColorRegion(int baseColor, ColorBoundary upperBound, ColorBoundary lowerBound) {
        mBaseColor = baseColor;
        mUpperBound = upperBound;
        mLowerBound = lowerBound;
    }

    public void setBaseColor(int baseColor) {
        mBaseColor = baseColor;
    }

    public int getBaseColor() {
        return mBaseColor;
    }

    private void adjustBound(ColorBoundary boundary, float delta) {
        float hue = boundary.getHue();
        hue = (hue + delta) % 360;

        // Wrap negative numbers
        while (hue < 0) {
            hue += 360;
        }

        boundary.setHue(hue);
    }

    public void adjustLowerBound(float delta) {
        adjustBound(mLowerBound, delta);
    }

    public void adjustUpperBound(float delta) {
        adjustBound(mUpperBound, delta);
    }

    public float rotateHue(float hue) {
        return (hue + 180f) % 360f;
    }

    public boolean contains(int color) {
        float uHue = mUpperBound.getHue();
        float lHue = mLowerBound.getHue();

        float hsv[] = new float[3];
        Color.colorToHSV(color, hsv);
        float hue = hsv[0];

        // If lHue < uHue (i.e. the region does not span the 359-0 disjunction) then a simple
        //  lHue < hue < uHues is fine. In the case where the region does span the disjuction,
        //  rotate the section 180 degrees around the circle (mod 360, of course) and swap the
        //  lower and upper bound. This will not affect containment, but will remove the
        //  disjunction from the region
        return (hue < uHue && hue > lHue) ||
                (rotateHue(hue) < rotateHue(lHue) && rotateHue(hue) > rotateHue(uHue));

    }
}