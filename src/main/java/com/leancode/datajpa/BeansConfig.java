package com.leancode.datajpa;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
    
    @Bean(name = "cloudinaryInit")
    public Cloudinary getCloudinary() {
        return new Cloudinary(ObjectUtils.asMap("cloud_name", "leandropalma27p", "api_key", "777658485899521", "api_secret", "hoOzbFZ_tZ3omPQuqQ0s7h5hJiU"));
    }

}
