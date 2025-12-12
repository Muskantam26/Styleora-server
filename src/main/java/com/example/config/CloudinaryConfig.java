package com.example.config;



import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

  @Bean
  public Cloudinary cloudinary() {
    return new Cloudinary(ObjectUtils.asMap(
    		 "cloud_name", "df55p3ldx",
             "api_key", "559945545876237",
             "api_secret", "iXyBPNoj4sNo7WWfwFOOr6rD1iQ"
    ));
  }
}
