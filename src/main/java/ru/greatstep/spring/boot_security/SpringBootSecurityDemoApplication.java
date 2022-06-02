package ru.greatstep.spring.boot_security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import ru.greatstep.spring.boot_security.models.Role;
import ru.greatstep.spring.boot_security.models.User;
import ru.greatstep.spring.boot_security.repositories.RoleRepository;
import ru.greatstep.spring.boot_security.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

class BeanInitMethodImpl {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	public void runAfterObjectCreated() {
		User admin = new User("Dmitriy","Tkachenko","greatstep@yandex.ru");
		admin.setUsername("admin");
		admin.setPassword("$2a$12$SOnZ9kd8ptoQbrTc6whqU.t/gtkmlJe3fNeWE6htnNmNgberD8I4S"); // admin

		List<Role> rolesList = new ArrayList<>();


		Role roleOne = new Role();
		roleOne.setName("ROLE_ADMIN");
		roleRepository.save(roleOne);
		rolesList.add(roleOne);


		Role roleTwo = new Role();
		roleTwo.setName("ROLE_USER");
		roleRepository.save(roleTwo);
		rolesList.add(roleTwo);


		admin.setRoles(rolesList);

		userRepository.save(admin);
	}
}

@SpringBootApplication
public class SpringBootSecurityDemoApplication {
	@Bean
	HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}


	public static void main(String[] args) {

		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);


	}
	@Bean(initMethod = "runAfterObjectCreated")
	public BeanInitMethodImpl getFunnyBean(){
		return new BeanInitMethodImpl();
	}

}
