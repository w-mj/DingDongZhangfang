package com.dingdonginc.zhangfang.models;


import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;

/**
 * 这个类是用来生成rawormlite_config.txt文件的
 * 每次修改了数据库结构之后都要单独执行一次此文件
 */
public class GenreateOrmLiteConfig extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {
            Account.class,
            Method.class,
            Tag.class
    };
    public static void main(String[] args) throws Exception {
        System.out.println( System.getProperty("user.dir"));
        writeConfigFile(new File("app\\src\\main\\res\\raw\\ormlite_config.txt"), classes);
    }
}
