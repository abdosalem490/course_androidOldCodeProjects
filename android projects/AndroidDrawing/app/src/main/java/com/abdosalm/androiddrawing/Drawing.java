package com.abdosalm.androiddrawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

public class Drawing extends View {
    private Paint brush;
    private Paint redBrush;
    private LinearGradient linearGradient;
    private RadialGradient radialGradient;
    private SweepGradient sweepGradient;
    private Bitmap bitmap;
    public Drawing(Context context) {
        super(context);
        init();
    }

    public void init(){
        //initiate
        linearGradient = new LinearGradient(0 , 0 , 200 , 200 , Color.RED , Color.BLACK , Shader.TileMode.REPEAT);
        radialGradient = new RadialGradient(0, 0 ,20 , Color.GREEN , Color.BLUE,Shader.TileMode.REPEAT);

        bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.image_one);

        brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        brush.setColor(Color.parseColor("green"));
        redBrush = new Paint(Paint.ANTI_ALIAS_FLAG);
        redBrush.setColor(Color.RED);
        redBrush.setStrokeWidth(23f);
        brush.setShader(radialGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
      /*  canvas.drawCircle(getMeasuredWidth() / 2 , getMeasuredHeight() / 2 , 300f , brush );
        canvas.drawLine(0 , 0 , getMeasuredWidth()/2 ,getMeasuredHeight() / 2 , redBrush);*/

        canvas.drawBitmap(bitmap , 0 , 0 , null);
        canvas.save();
        super.onDraw(canvas);

    }
}
