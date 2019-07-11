package com.dingdonginc.zhangfang.models;


import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;

public class GenreateOrmLiteConfig extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {
            Account.class,
            Method.class,
            Tag.class
    };
    public static void main(String[] args) throws Exception {
        System.out.println( System.getProperty("user.dir"));
        writeConfigFile(new File("app\\src\\main\\res\\raw\\rawormlite_config.txt"), classes);
    }
}
