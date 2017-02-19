package me.wonwoo;

import me.wonwoo.dto.CustomerDTO;
import me.wonwoo.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootJooqExampleApplicationTests {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void contextLoads() {
		final CustomerDTO customerDTO = customerRepository
			.findAll()
			.stream()
			.findFirst()
			.get(); //
		assertThat(customerDTO.getId()).isEqualTo(1);
		assertThat(customerDTO.getName()).isEqualTo("wonwoo");
		assertThat(customerDTO.getEmail()).isEqualTo("wonwoo@test.com");
		assertThat(customerDTO.getProducts()).hasSize(4);
	}
}
