package com.example.edoc.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/*
* @author : Sanjay shah
* @date : Sep 14, 2019
*/
public class FileUploadHelper {

    public static String upload(MultipartFile file) {
        if (file.isEmpty()) return null;


        String fileName = null;
        try {
            fileName = generateFileName(file.getOriginalFilename());

            /*
             * byte[] bytes = file.getBytes(); Path path =
             * Paths.get(Strings.FILE_UPLOAD_BASE_PATH + fileName); Files.write(path,
             * bytes);
             */

            file.transferTo(new File(Strings.FILE_UPLOAD_BASE_PATH + "image/"  + fileName));

//	FileUtils.cop

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
    
    private static String generateFileName(String file) {
        String fileName = System.currentTimeMillis() + "_" + file;
        //System.out.println(fileName);
        return fileName;
    }

}
