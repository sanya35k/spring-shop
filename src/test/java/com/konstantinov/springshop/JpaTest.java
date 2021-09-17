package com.konstantinov.springshop;

import com.konstantinov.springshop.model.Product;
import com.konstantinov.springshop.model.Role;
import com.konstantinov.springshop.model.User;
import com.konstantinov.springshop.repository.ProductRepository;
import com.konstantinov.springshop.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void readFirstPage() {
        Page<Product> persons = productRepository.findAll(new PageRequest(0, 10));
        assertThat(persons.isFirst(), is(true));
    }

    @Test
    public void userOperation() {
        User nazar = new User("ua","ak@gmail.com","12345", Role.ADMIN,true);
        userRepository.save(nazar);
        User user =userRepository.findByUsername("us");
        assertEquals(nazar, user);
    }

    @Test
    public void productOperation(){
        Product product = new Product(1,"t","test",10,"test.png");
        productRepository.save(product);
        assertEquals(product, productRepository.findOne(product.getId()));
    }
}