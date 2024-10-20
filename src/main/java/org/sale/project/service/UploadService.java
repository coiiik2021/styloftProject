package org.sale.project.service;


import jakarta.servlet.ServletContext;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadService {
    ServletContext servletContext;


    public String uploadImage(MultipartFile file, String path) {
        if(file.isEmpty())
            return "";
        String rootPath = this.servletContext.getRealPath("/resources/images");
        String finalName = "";
        try{
            byte[] bytes = file.getBytes();
            File dir = new File(rootPath + File.separator + path);
            if (!dir.exists())
                dir.mkdirs();
            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            File serverFile = new File( dir.getAbsolutePath() + File.separator + finalName);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            System.out.println("image error");
        }
        return finalName;
    }

    public void updateNameFileProduct(String oldName, String newName) {

        String rootPath = this.servletContext.getRealPath("/resources/images/product");

        try{
            byte[] bytes = oldName.getBytes();
            File dir = new File(rootPath + File.separator + oldName);

            if(dir.exists()){
                dir.renameTo(new File(rootPath + File.separator + newName));
            } else{
                return;
            }

        } catch (Exception e) {
            System.out.println("image error");
        }



    }

}