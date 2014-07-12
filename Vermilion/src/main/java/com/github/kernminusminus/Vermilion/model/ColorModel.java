package com.github.kernminusminus.Vermilion.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorModel {

    private static ColorModel mModel;


    public enum Colors {
        RED ("Red", 0),
        ORANGE ("Orange", 1),
        YELLOW ("Yellow", 2),
        GREEN ("Green", 3),
        BLUE ("Blue", 4),
        VIOLET ("Violet", 5);

        public final String name;
        public final int index;
        private Colors(String name, int index) {this.name = name; this.index = index;}
        public static Colors get(int i) {
            switch (i){
                case 0: return RED;
                case 1: return ORANGE;
                case 2: return YELLOW;
                case 3: return GREEN;
                case 4: return BLUE;
                case 5: return VIOLET;
            }
            return RED;
        }
        public static int numColors() {
            return Colors.values().length;
        }
    }


    private Map<String, ColorRegion> mRegions;
    private Map<String, List<OnBaseColorChangeListener>> mBaseColorChangeListeners;


    public static ColorModel getInstance() {
        if (mModel == null) {
            mModel = new ColorModel();
        }
        return mModel;
    }

    private ColorModel() {
        int numColors = Colors.numColors();

        // Create some boundaries
        ColorBoundary boundaries[] = new ColorBoundary[numColors];
        for (int i = 0; i < numColors; i++){
            boundaries[i] = new ColorBoundary(0);
        }

        // Create the regions
        mRegions = new HashMap<String, ColorRegion>();
        for (int i = 0; i < numColors; i++) {
            mRegions.put(Colors.get(i).name, new ColorRegion(0, boundaries[i], boundaries[(i+1) % numColors]));
        }

        // Create an array of listeners
        mBaseColorChangeListeners = new HashMap<String, List<OnBaseColorChangeListener>>();
    }

    public void setBaseColor(String colorName, int color) {
        ColorRegion r = mRegions.get(colorName);
        if (r != null) {
            r.setBaseColor(color);
            notifyBaseColorChangeListeners(colorName, color);
        }
    }

    private void notifyBaseColorChangeListeners(String colorName, int color) {
        List<OnBaseColorChangeListener> listeners = mBaseColorChangeListeners.get(colorName);
        if (listeners != null) {
            for (OnBaseColorChangeListener listener : listeners) {
                listener.onBaseColorChange(color);
            }
        }
    }

    public void addBaseColorChangeListener(String color, OnBaseColorChangeListener listener) {
        List<OnBaseColorChangeListener> listeners = mBaseColorChangeListeners.get(color);
        if (listeners == null) {
            listeners = new ArrayList<OnBaseColorChangeListener>();
            mBaseColorChangeListeners.put(color, listeners);
        }
        listeners.add(listener);
    }

    public void removeBaseColorChangeListener(String color, OnBaseColorChangeListener listener) {
        List<OnBaseColorChangeListener> listeners = mBaseColorChangeListeners.get(color);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    public static interface OnBaseColorChangeListener {
        public void onBaseColorChange(int color);
    }

}
