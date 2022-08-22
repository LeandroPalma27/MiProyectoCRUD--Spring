package com.leancode.datajpa.models.provider.dao;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryDao {
    
    public Map<String, Object> upload(MultipartFile file);

}
