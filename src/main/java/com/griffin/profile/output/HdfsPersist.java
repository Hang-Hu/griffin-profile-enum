package com.griffin.profile.output;

import com.griffin.profile.util.enumProfile.EnumProfile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by xiangrchen on 7/10/17.
 */
public class HdfsPersist implements Persist{
    private static final Logger LOGGER = LoggerFactory.getLogger(HdfsPersist.class);

    @Override
    public void out(Map<String, String> config, EnumProfile enumProfile, Map<String, Double> enumProfileRes){
        LOGGER.info("===========================================================");
        String path=config.get("path");
        path=path+System.currentTimeMillis()+"/_RESULT";
        try {
            createFile(path,enumProfileRes.toString().getBytes());
        } catch (IOException e) {
            LOGGER.warn("cannot create hdfs "+path+" "+e);
        }
        LOGGER.info("===========================================================");
    }
    /**
     * Create file
     *
     * @param dst
     * @param contents
     * @throws IOException
     */
    public void createFile(String dst, byte[] contents)
            throws IOException {
        Configuration conf = new Configuration();
        Path dstPath = new Path(dst);
        FileSystem fs = dstPath.getFileSystem(conf);

        FSDataOutputStream outputStream = fs.create(dstPath);
        outputStream.write(contents);
        outputStream.close();
        LOGGER.info("create file " + dst + " success!");
        fs.close();
    }
}
