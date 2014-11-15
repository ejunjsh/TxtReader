package com.sky.txtReader.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by shaojunjie on 2014/10/29.
 */
public class TxtReaderApplication extends Application {


    private final String BOOKLIST="BOOKLIST";
    private final String BOOKMAXINDEX="BOOKMAXINDEX";
    private final String BOOKTIME="BOOKTIME";
    private final String BOOKMARKSTART="BOOKMARKSTART";
    private final String BOOKMARKEND="BOOKMARKEND";

    @Override
    public void onCreate(){
        super.onCreate();
    }


    public void saveBook(BookBean bean)
    {
        SharedPreferences preferences=getSharedPreferences(BOOKMAXINDEX, Context.MODE_PRIVATE);
        long maxOrder=preferences.getLong(BOOKMAXINDEX,0);

        SharedPreferences.Editor editor= preferences.edit();
        maxOrder++;
        editor.putLong(BOOKMAXINDEX,maxOrder);
        editor.commit();

        preferences=getSharedPreferences(BOOKLIST, Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.putLong(bean.getPath(),maxOrder);
        editor.commit();

        preferences=getSharedPreferences(BOOKMARKSTART, Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.putInt(bean.getPath(),bean.getMarkStart());
        editor.commit();

        preferences=getSharedPreferences(BOOKMARKEND, Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.putInt(bean.getPath(),bean.getMarkEnd());
        editor.commit();

        preferences=getSharedPreferences(BOOKTIME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.putLong(bean.getPath(),bean.getTime());
        editor.commit();
    }

    public void saveBookMark(String path,int mark,int mark2)
    {
        SharedPreferences preferences=getSharedPreferences(BOOKMARKSTART, Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor=preferences.edit();
        editor.putInt(path,mark);
        editor.commit();

        preferences=getSharedPreferences(BOOKMARKEND, Context.MODE_PRIVATE);
         editor=preferences.edit();
        editor.putInt(path,mark);
        editor.commit();
    }


    public void removeBook(String path)
    {
        SharedPreferences preferences=getSharedPreferences(BOOKLIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.remove(path);
        editor.commit();

        preferences=getSharedPreferences(BOOKMARKSTART, Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.remove(path);
        editor.commit();

        preferences=getSharedPreferences(BOOKMARKEND, Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.remove(path);
        editor.commit();

        preferences=getSharedPreferences(BOOKTIME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.remove(path);
        editor.commit();
    }

    public List<BookBean> getBooks()
    {
        SharedPreferences preferences=getSharedPreferences(BOOKLIST, Context.MODE_PRIVATE);
        Map<String, ?> map= preferences.getAll();
        List<BookBean> list=new ArrayList<BookBean>();
        for(String key :map.keySet())
        {
           BookBean bean=new BookBean();
            bean.setOrder(Long.parseLong(map.get(key).toString()));
            bean.setPath(key);
            SharedPreferences sp=getSharedPreferences(BOOKMARKSTART,Context.MODE_PRIVATE);
            bean.setMarkStart(sp.getInt(key,0));
            sp=getSharedPreferences(BOOKMARKEND,Context.MODE_PRIVATE);
            bean.setMarkEnd(sp.getInt(key,0));
            sp=getSharedPreferences(BOOKTIME,Context.MODE_PRIVATE);
            bean.setTime(sp.getLong(key,0));
            list.add(bean);
        }

        Collections.sort(list,new Comparator<BookBean>() {
            @Override
            public int compare(BookBean lhs, BookBean rhs) {
                if(lhs.getOrder()<rhs.getOrder())
                {
                    return 1;
                }
                else if(lhs.getOrder()>rhs.getOrder())
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        });
        return list;
    }
}
