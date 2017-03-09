package com.zhangyy3.orm.generator.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/13 11:14
 */
public class FileUtil {

    public static void saveFile(String filePath, String fileName, String content) throws
            IOException {
        File file = new File(filePath, fileName);
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.flush();
        writer.close();
    }
}
