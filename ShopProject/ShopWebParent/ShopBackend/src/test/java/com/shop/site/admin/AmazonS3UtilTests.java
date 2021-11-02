package com.shop.site.admin;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AmazonS3UtilTests {

    @Test
    public void testListFolder(){
        String folderName="user-photos/10";
        List<String> listKeys = AmazonS3Util.listFolder(folderName);
        listKeys.forEach(System.out::println);
    }
    @Test
    public void testUploadFile() throws FileNotFoundException {
        String filePath = "";
        String folderName = "test-upload";
        String fileName = "";

        InputStream inputStream = new FileInputStream(filePath);

        AmazonS3Util.uploadFile(folderName, fileName, inputStream);
    }
    @Test
    public void testDeleteFile(){
        String fileName = "test-upload";
        AmazonS3Util.deleteFile(fileName);
    }
    @Test
    public void testRemoveFolder(){
        String folderName = "test-upload";
        AmazonS3Util.removeFolder(folderName);
    }
}
