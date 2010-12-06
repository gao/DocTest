package com.n2napps.doctest.utils;

import java.io.File;

public class FolderUtils {
    
    public static void newFolder(String folderPath) {
        try {
            String filePath = System.getProperty("user.dir") + folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
        }
        catch (Exception e) {
          System.out.println("can not create the folder");
          e.printStackTrace();
        }
    }

}
