package com.sky.txtReader.activity;

/**
 * Created by shaojunjie on 2014/10/29.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.txtReader.fragment.BookShelfFragment;
import com.sky.txtReader.fragment.FileFragment;
import com.sky.txtReader.utils.BookBean;
import com.sky.txtReader.utils.TxtReaderApplication;

import java.util.ArrayList;
import java.util.List;


public class BookShelfActivity extends BaseActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageView moreView;

    private ViewPager viewPager;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);



        viewPager=(ViewPager)findViewById(R.id.viewpager);

        moreView=(ImageView) findViewById(R.id.more_view);
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Animation Anim_Alpha = AnimationUtils.loadAnimation(BookShelfActivity.this,R.anim.alpha_action);
//                v.startAnimation(Anim_Alpha);
                PopupMenu morePop = new PopupMenu(BookShelfActivity.this, v);
                morePop.getMenuInflater().inflate(R.menu.main, morePop.getMenu());
                morePop.setOnMenuItemClickListener(BookShelfActivity.this);
                morePop.show();
            }
        });

        List<Fragment> fragmentList=new ArrayList<Fragment>();
        BookShelfFragment bookShelfFragment=new BookShelfFragment();
        FileFragment fileFragment=new FileFragment();
        fragmentList.add(bookShelfFragment);
        fragmentList.add(fileFragment);

        MyFragmentPagerAdapter myFragmentPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(0);//设置当前显示标签页为第一页
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });//页面变化时的监听器
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.menu_about)
        {
            Toast.makeText(this, "一个人工作团队，一个人搞！！", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> list;
        public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            this.list = list;

        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int index) {
            return list.get(index);
        }




    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("你确定退出吗？")
                    .setCancelable(false)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    finish();
                                    System.exit(0);
                                }
                            })
                    .setNegativeButton("返回",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}