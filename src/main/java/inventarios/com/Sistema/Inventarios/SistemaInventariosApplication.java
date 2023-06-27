package inventarios.com.Sistema.Inventarios;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Models.UserType;
import inventarios.com.Sistema.Inventarios.Repositories.UserInventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SistemaInventariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaInventariosApplication.class, args);

	}

@Bean
public CommandLineRunner initData(UserInventoryRepository userInventoryRepository){
 return (args) -> {
	UserInventory user1 = new UserInventory("user11","Gonzalez","user123","correo1@gmail.com", UserType.ADMIN);
	UserInventory user2 = new UserInventory("user22","Garcia","user456","correo2@gmail.com",UserType.CASHIER);
	UserInventory user3 = new UserInventory("user33","Guzman","user789","correo3@gmail.com",UserType.WORKER);
	UserInventory user4 = new UserInventory("user44","Rodriguez","user012","correo4@gmail.com", UserType.SUPERVISOR);
	UserInventory user5 = new UserInventory("user55","De Zubiria","user345","correo5@gmail.com",UserType.WORKER);

 	userInventoryRepository.save(user1);
	 userInventoryRepository.save(user2);
	 userInventoryRepository.save(user3);
	 userInventoryRepository.save(user4);
	 userInventoryRepository.save(user5);
 };
}
}
