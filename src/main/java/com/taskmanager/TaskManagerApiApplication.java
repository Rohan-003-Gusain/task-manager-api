package com.taskmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.taskmanager.model.RoleType;
import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;

@SpringBootApplication
public class TaskManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApiApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UserRepository repo, PasswordEncoder encoder){
	    return args -> {

	        if(repo.findByUsername("admin").isEmpty()){

	            User admin = new User();
	            admin.setUsername("admin");
	            admin.setEmail("admin@gmail.com");
	            admin.setPassword(encoder.encode("admin123"));
	            admin.setRole(RoleType.ADMIN);

	            repo.save(admin);
	        }

	    };
	}

}
