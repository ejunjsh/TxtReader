package com.sky.txtReader.utils;

/**
 * Created by shaojunjie on 2014/11/3.
 */

import info.monitorenter.cpdetector.io.JChardetFacade;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class CharsetDetector {

    /**
     * 检测当前文件的编码方式
     */
    public static Charset detect(File file) {
        Charset charset = null;
        InputStream in=null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            JChardetFacade detector = JChardetFacade.getInstance();
            in.mark(100);
            charset = detector.detectCodepage(in, 100);
            in.reset();
            in.close();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return charset;
    }

}

