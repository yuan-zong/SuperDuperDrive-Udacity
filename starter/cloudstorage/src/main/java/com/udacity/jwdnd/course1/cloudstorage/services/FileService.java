package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileCustom;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Service
public class FileService {

    private FileMapper fileMapper;

    public static int ERROR_FILE_ALREADY_EXISTED = -2;
    public static int ERROR_FILE_NOT_FOUND = -3;
    public static int ERROR_GENERAL = -1;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public FileMapper getFileMapper() {
        return fileMapper;
    }

    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public ArrayList<FileCustom> getFilesByUser(Integer userid) {
        return fileMapper.getFilesByUser(userid);
    }

    public FileCustom getFileByFileId(Integer fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    public int insert(MultipartFile uploadFile, User user) throws IOException {
        InputStream is = uploadFile.getInputStream();
        byte[] filedata = new byte[(int) uploadFile.getSize()];
        is.read(filedata);
        return fileMapper.insert(new FileCustom(null, uploadFile.getOriginalFilename(),
                uploadFile.getContentType(), filedata.length, user.getUserId(), filedata));
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }

    public int uploadFile(MultipartFile file, User user) throws FileNotFoundException, IOException {

        String filename = file.getOriginalFilename();
        // check if a file with the same name already exists
        FileCustom fileExisted = fileMapper.getFileByNameAndUser(filename, user.getUserId());
        if (fileExisted == null) {
            return insert(file, user);
        } else {
            return ERROR_FILE_ALREADY_EXISTED;
        }

    }
}
