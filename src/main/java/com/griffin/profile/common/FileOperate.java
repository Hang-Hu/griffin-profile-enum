package com.griffin.profile.common;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by xiangrchen on 6/20/17.
 */
public class FileOperate {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileOperate.class);

    public static void writeFile(String pathname,String content){
        try{
            File file=new File(pathname);
            if(!file.getParentFile().exists()){
                if(!file.getParentFile().mkdirs()) {
                    System.out.println("ctrate file "+pathname+"fail");
                    return ;
                }
            }
            if(!file.exists()){
                file.createNewFile();
            }else {
                file.delete();
                file.createNewFile();
            }
            FileUtils.write(file,content, "utf-8");
        }catch (Exception e) {
            // TODO: handle exception
            LOGGER.info(""+e);
        }
    }

    public static String readFile(String pathname){
        String res=null;
        try{
            File file=new File(pathname);
            if(!file.getParentFile().exists()){
                return null;
            }
            if(!file.exists()){
                return null;
            }
            res=FileUtils.readFileToString(file,"UTF-8");
        }catch (Exception e) {
            // TODO: handle exception
            LOGGER.info(""+e);
        }finally {
            return res;
        }
    }
}
