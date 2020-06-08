package com.github.anhtom2000;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

@MapperScan(basePackages = "com.github.anhtom2000.mapper",basePackageClasses = Repository.class)
@SpringBootApplication(scanBasePackages = "com.github.anhtom2000")
public class SerialPortManagerApplication
{

    public static void main(String[] args) {
        SpringApplication.run(SerialPortManagerApplication.class, args);
    }

}
