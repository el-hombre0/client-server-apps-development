package com.example.prac5_ser;

import com.example.prac5_ser.service.FilesStorageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;


@SpringBootApplication
public class Prac5SerApplication implements CommandLineRunner {
	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(Prac5SerApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
//    storageService.deleteAll();
		storageService.init();
	}
}

