package com.sky.txtReader.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.txtReader.activity.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaojunjie on 2014/11/6.
 */
public class FileFragment extends Fragment {

    private ListView listfile;
    //当前文件目录
    private String currentpath;
    private TextView txt1;
    private ImageView images;
    private TextView textview;
    private ImageButton imagebt1;

    private int[] img = { R.drawable.cartoon_txt, R.drawable.cartoon_folder, R.drawable.abc_ic_ab_back_holo_dark };
    private File[] files;
    private SimpleAdapter simple;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View contentView=inflater.inflate(R.layout.file_fragment,container,false);

        listfile = (ListView)contentView.findViewById(R.id.listFile);
        txt1 = (TextView) contentView.findViewById(R.id.txt1);
        imagebt1 = (ImageButton) contentView.findViewById(R.id.imageBt1);
        init(Environment.getExternalStorageDirectory());
        listfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                // 获取单击的文件或文件夹的名称
                String folder = ((TextView) arg1.findViewById(R.id.txtview))
                        .getText().toString();
                try {
                    if(folder.equals("..."))
                    {
                        int last=currentpath.lastIndexOf("/");
                        File filef=new File(currentpath.substring(0,last));
                        init(filef);
                    }
                    else {
                        File filef = new File(currentpath + '/'
                                + folder);
                        if(filef.isFile())
                        {
                            openFile(filef);
                        }
                        else {
                            init(filef);
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "无权限访问", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
        //回根目录
        imagebt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                init(Environment.getExternalStorageDirectory());
            }
        });

        return  contentView;
    }

    // 界面初始化
    public void init(File f) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // 获取SDcard目录下所有文件名
            files = f.listFiles();
            if (!files.equals(null)) {
                currentpath=f.getPath();
                txt1.setText("当前目录为:"+f.getPath());
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                if(f.getParentFile()!=null)
                {
                    Map<String, Object> maps = new HashMap<String, Object>();
                    maps.put("image", img[1]);
                    maps.put("filenames", "...");
                    list.add(maps);
                }
                for (int i = 0; i < files.length; i++) {
                    Map<String, Object> maps = new HashMap<String, Object>();
                    if (files[i].isFile())
                        maps.put("image", img[0]);
                    else
                        maps.put("image", img[1]);
                    maps.put("filenames", files[i].getName());
                    list.add(maps);
                }
                simple = new SimpleAdapter(getActivity(), list,
                        R.layout.file_item_listview, new String[] { "image",
                        "filenames" }, new int[] { R.id.images,
                        R.id.txtview });
                listfile.setAdapter(simple);

            }
        } else {
            System.out.println("该文件为空");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        simple.notifyDataSetChanged();
    }

    private final String[][] MIME_MapTable={
            //{后缀名，    MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",      "image/bmp"},
            {".c",        "text/plain"},
            {".class",    "application/octet-stream"},
            {".conf",    "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",    "application/x-gtar"},
            {".gz",        "application/x-gzip"},
            {".h",        "text/plain"},
            {".htm",    "text/html"},
            {".html",    "text/html"},
            {".jar",    "application/java-archive"},
            {".java",    "text/plain"},
            {".jpeg",    "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js",        "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",    "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",    "video/mp4"},
            {".mpga",    "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".prop",    "text/plain"},
            {".rar",    "application/x-rar-compressed"},
            {".rc",        "text/plain"},
            {".rmvb",    "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh",        "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            //{".xml",    "text/xml"},
            {".xml",    "text/plain"},
            {".z",        "application/x-compress"},
            {".zip",    "application/zip"},
            {"",        "*/*"}
    };

    /**
     * 根据文件后缀名获得对应的MIME类型。
     * @param file
     */
    private String getMIMEType(File file)
    {
        String type="*/*";
        String fName=file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
    /* 获取文件的后缀名 */
        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }
    /**
     * 打开文件
     * @param file
     */
    private void openFile(File file){
        //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
        //跳转
        startActivity(intent);
    }
}
