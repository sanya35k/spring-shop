package com.konstantinov.springshop;

import com.konstantinov.springshop.model.Product;
import com.konstantinov.springshop.model.Role;
import com.konstantinov.springshop.model.User;
import com.konstantinov.springshop.repository.ProductRepository;
import com.konstantinov.springshop.repository.UserRepository;
import com.konstantinov.springshop.storage.FileSystemStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(FileSystemStorageService storageService) {
        return (args) -> {
        };
    }

    @Bean
    public CommandLineRunner demo(ProductRepository productRepository, UserRepository userRepository) {
        return (args) -> {
            productRepository.deleteAll();
            userRepository.deleteAll();

            Product product = new Product(8, "id", "Alex", 100, "photo.png");
            User admin = new User("admin", "alex.konstantinov@gmail.com", "password", Role.ADMIN, true);
            User user = new User("user", "konstantinov03051999@gmail.com", "password", Role.USER, true);

            userRepository.save(admin);
            productRepository.save(product);
            userRepository.save(user);
        };
    }
}