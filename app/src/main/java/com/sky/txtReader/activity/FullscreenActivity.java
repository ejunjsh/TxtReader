package com.sky.txtReader.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.sky.txtReader.utils.BookBean;
import com.sky.txtReader.utils.BookPageFactory;
import com.sky.txtReader.utils.CharsetDetector;
import com.sky.txtReader.utils.TxtReaderApplication;
import com.sky.txtReader.widget.PageWidget;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by shaojunjie on 2014/10/20.
 */
public class FullscreenActivity extends BaseActivity {

    private PageWidget mPageWidget;
    Bitmap mCurPageBitmap, mNextPageBitmap;
    Canvas mCurPageCanvas, mNextPageCanvas;
    BookPageFactory pagefactory;

    String filePath;

    private int defaultTextSize=18;//18dp;

    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //save book mark;
                    ((TxtReaderApplication)getApplication()).saveBookMark(filePath,pagefactory.getM_mbBufBegin(),pagefactory.getM_mbBufEnd());
                    break;
            }
        };
    };

    public FullscreenActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPageWidget = new PageWidget(this);
        setContentView(mPageWidget);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight= dm.heightPixels;

        mPageWidget.setScreen(screenWidth,screenHeight);

        mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap
                .createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);

        mCurPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);

        float density=getResources().getDisplayMetrics().density;

        int fontSize=Math.round(defaultTextSize*density) ;

        pagefactory = new BookPageFactory(screenWidth, screenHeight,fontSize);

        pagefactory.setBgBitmap(BitmapFactory.decodeResource(
                this.getResources(), R.drawable.shelf_bkg));



        try {

            Intent intent = getIntent();
            Uri uri = (Uri) intent.getData();

            if(uri!=null) {
                filePath = uri.getPath();
                if (filePath != null && !filePath.equals("")) {
                    File file=new File (filePath);

                    BookBean book=null;
                    Object o=getIntent().getSerializableExtra("book");

                    if(o!=null) {
                        book=(BookBean)o;
                        // if file's time is not equal the book's time ,then set book mark with zero.
                        if (book.getTime() != file.lastModified()) {
                            book.setTime(file.lastModified());
                            book.setMarkStart(0);
                            book.setMarkEnd(0);
                        }
                    }
                    else
                    {
                        book=new BookBean();
                        book.setPath(filePath);
                        book.setTime(file.lastModified());
                        book.setMarkStart(0);
                        book.setMarkEnd(0);
                    }
                    //save book to be persistent.
                    ((TxtReaderApplication)getApplication()).saveBook(book);
                    pagefactory.setM_mbBufBegin(book.getMarkStart());
                    pagefactory.setM_mbBufEnd(book.getMarkEnd());

                    Charset charset= CharsetDetector.detect(new File(filePath));
                    pagefactory.setM_strCharsetName(charset.name());
                    pagefactory.openbook(filePath);
                    pagefactory.onDraw(mCurPageCanvas);

                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            Toast.makeText(this, "文件打开异常，请选择有效的文本文件",
                    Toast.LENGTH_LONG).show();
            this.finish();
        }

        mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);

        mPageWidget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                // TODO Auto-generated method stub

                boolean ret = false;
                if (v == mPageWidget) {
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {

                        mPageWidget.calcCornerXY(e.getX(), e.getY());

                        pagefactory.onDraw(mCurPageCanvas);
                        if (mPageWidget.DragToRight()) {
                            try {
                                pagefactory.prePage();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            if (pagefactory.isfirstPage()) {
                                Toast.makeText(FullscreenActivity.this, "已经是第一页了",
                                        Toast.LENGTH_LONG).show();
                                return false;
                            }
                        } else {
                            try {
                                pagefactory.nextPage();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            if (pagefactory.islastPage()) {
                                Toast.makeText(FullscreenActivity.this, "已经是最后一页了",
                                        Toast.LENGTH_LONG).show();
                                return false;
                            }

                        }


                        mPageWidget.abortAnimation();
                        pagefactory.onDraw(mNextPageCanvas);
                        mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);


                        Message msg=new Message();
                        msg.what=1;
                        handler.sendMessage(msg);
                    }

                    ret = mPageWidget.doTouchEvent(e);
                    return ret;
                }

                return false;
            }

        });



    }

}
