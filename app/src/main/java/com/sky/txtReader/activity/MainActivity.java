package com.sky.txtReader.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.txtReader.widget.BorderScrollView;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity {

    public TextView editContent;

    public ActionBar mActionBar;

    public BorderScrollView contentScroll;

    public boolean isLoading=false;

    private BufferedReader br;

    private BaseActivity context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestActionBarMenu();

        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();

        editContent = (TextView) findViewById(R.id.edit_content);

        contentScroll=(BorderScrollView)findViewById(R.id.content_scroll);

        context=this;



        // 注册刚写的滚动监听器
//        contentScroll.setOnScrollChangedListener(new OnScrollChangedListener(){
//            @Override
//            public void onScrollChanged(int l, int t, int oldl, int oldt) {
//            }
//
//            @Override
//            public void onScrollTop() {
//
//            }
//
//            @Override
//            public void onScrollBottom() {
//                synchronized (MainActivity.class){
//                    if(!isLoading){
//                        isLoading = true;
//                        new AsyncTextLoadTask(context, br).execute();
//                    }
//                }
//            }
//        });
//
//        Intent intent = getIntent();
//        Uri uri = (Uri) intent.getData();
//
//        if(uri!=null) {
//            String filePath = uri.getPath();
//            if (filePath != null && !filePath.equals("")) {
//                loadFile(filePath);
//            }
//        }

    }


//    private void loadFile(String path)
//    {
//        try{
//            InputStream is = new FileInputStream(new File(path));
//            Charset cs= CharsetDetector.detect(is);
//            if(null!=br)
//            {
//                br.close();
//            }
//            br = new BufferedReader(new InputStreamReader(is,cs));
//            new AsyncTextLoadTask(context, br).execute();
//
//        }catch(Exception ex){
//            Log.d("openFile",ex.getMessage());
//            Toast.makeText(this, "文件打开异常，请选择有效的文本文件", Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != br){
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();        //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

}
