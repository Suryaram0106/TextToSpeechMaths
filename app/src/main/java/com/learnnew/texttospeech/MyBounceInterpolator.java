package com.learnnew.texttospeech;

import android.view.animation.Interpolator;

public class MyBounceInterpolator implements Interpolator {

    private double myapp=1;
    private double myfre=10;

    MyBounceInterpolator(double amplitude, double frequency){
        myapp=amplitude;
        myfre=frequency;
    }
    public float getInterpolation(float time)
    {
        return (float)(-1*Math.pow(Math.E,-time/myapp)*Math.cos(myfre*time)+1);
    }
}
