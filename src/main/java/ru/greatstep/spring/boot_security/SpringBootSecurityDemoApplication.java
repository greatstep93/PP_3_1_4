package ru.greatstep.spring.boot_security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import ru.greatstep.spring.boot_security.service.UserServiceImp;

class BeanInitMethodImpl {

	@Autowired
    UserServiceImp userServiceImp;
	public void runAfterObjectCreated() {
		userServiceImp.addDefaultAdmin();
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
