package com.abdosalm.androiddrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint textColor;

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public void init(){
        textColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        textColor.setColor(Color.parseColor("blue"));
        textColor.setTextSize(120f);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText("Hello World" , (canvas.getHeight() / 2 ) -(getMeasuredWidth() / 2) , getMeasuredHeight() / 2 , textColor);
        canvas.save();
        super.onDraw(canvas);
    }
}
