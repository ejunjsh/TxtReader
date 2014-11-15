package com.sky.txtReader.widget;

/**
 * Created by shaojunjie on 2014/10/29.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.GridView;

import com.sky.txtReader.activity.R;

import java.io.InputStream;


public class MyGridView extends GridView {

    private Bitmap background;

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        InputStream is=getResources().openRawResource(R.raw.bookshelf_layer_center);

        background = BitmapFactory.decodeStream(is);

        int backgroundWidth = background.getWidth();
        int backgroundHeight = background.getHeight();
        float density=  getResources().getDisplayMetrics().density;

        //1.5:1是在hdpi中的比列，这种设备可以令书在架中间，所以其他设备就要转换
        float scale=1/1.5f*density;

        if(scale!=1) {
            backgroundWidth = Math.round(backgroundWidth * scale);
            backgroundHeight = Math.round(backgroundHeight * scale);
            background=zoomImg(background,backgroundWidth,backgroundHeight);
        }

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int count = getChildCount();
        int top = count > 0 ? getChildAt(0).getTop() : 0;
        int backgroundWidth = background.getWidth();
        int backgroundHeight = background.getHeight();
        int width = getWidth();
        int height = getHeight();

        for (int y = top; y < height; y += backgroundHeight) {
            for (int x = 0; x < width; x += backgroundWidth) {
                canvas.drawBitmap(background, x,y, null);
            }
        }

        super.dispatchDraw(canvas);
    }

    // 缩放图片
    private Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

}
