package com.konstantinov.springshop;

import com.konstantinov.springshop.models.Product;
import com.konstantinov.springshop.models.Role;
import com.konstantinov.springshop.models.User;
import com.konstantinov.springshop.repositories.ProductRepository;
import com.konstantinov.springshop.repositories.UserRepository;
import com.konstantinov.springshop.storage.FileSystemStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
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

            Product pomdor = new Product(8, "id", "Alex", 100, "photo.png");
            User admin = new User("admin", "alex.konstantinov@gmail.com", "password", Role.ADMIN, true);
//            List<Product> basket = new ArrayList<>();
//            basket.add(pomdor)
//            admin.setBasket(basket);
//            pomdor.setCategory(new Category("Electro"));
            User user = new User("user", "konstantinov03051999@gmail.com", "password", Role.USER, true);

            userRepository.save(admin);
            productRepository.save(pomdor);
            userRepository.save(user);
        };
    }
}

