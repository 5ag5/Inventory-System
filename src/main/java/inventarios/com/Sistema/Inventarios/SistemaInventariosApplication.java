package inventarios.com.Sistema.Inventarios;

import inventarios.com.Sistema.Inventarios.Models.*;
import inventarios.com.Sistema.Inventarios.Repositories.CategoryRepository;
import inventarios.com.Sistema.Inventarios.Repositories.ParameterRepository;
import inventarios.com.Sistema.Inventarios.Repositories.ProductRepository;
import inventarios.com.Sistema.Inventarios.Repositories.UserInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SistemaInventariosApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SistemaInventariosApplication.class, args);

	}

@Bean
public CommandLineRunner initData(UserInventoryRepository userInventoryRepository,
								  ParameterRepository parameterRepository,
								  ProductRepository productRepository,
								  CategoryRepository categoryRepository){
 return (args) -> {
	UserInventory user1 = new UserInventory("user11","Gonzalez", passwordEncoder.encode("user123"),"correo1@gmail.com", UserType.ADMIN);
	UserInventory user2 = new UserInventory("user22","Garcia",passwordEncoder.encode("user456"),"correo2@gmail.com",UserType.CASHIER);
	 UserInventory user3 = new UserInventory("user33","Guzman",passwordEncoder.encode("user789"),"correo3@gmail.com",UserType.WORKER);
	UserInventory user4 = new UserInventory("user44","Rodriguez",passwordEncoder.encode("user012"),"correo4@gmail.com", UserType.SUPERVISOR);
	UserInventory user5 = new UserInventory("user55","De Zubiria",passwordEncoder.encode("user345"),"correo5@gmail.com",UserType.WORKER);

	userInventoryRepository.save(user1);
	 userInventoryRepository.save(user2);
	 userInventoryRepository.save(user3);
	 userInventoryRepository.save(user4);
	 userInventoryRepository.save(user5);

	 Category vegetables = new Category("VEGETABLES", true);
	 Category videoGames = new Category("VIDEO GAMES",true);

	 Product cavages = new Product("Cavage is a vegetable that we sell", 200, 12.20, 18.99,
	 20, 1000, true);

	 Product fifa2023 = new Product("football game created by EA Sports",45,10.00,40.00,
			 3,1000,true);

	 productRepository.save(cavages);
	 productRepository.save(fifa2023);

	 vegetables.addProduct(cavages);
	 videoGames.addProduct(fifa2023);

	 categoryRepository.save(vegetables);
	 categoryRepository.save(videoGames);

	 Parameter parameter1 = new Parameter("Parameter that forces password change after 30 days", "30 days password change", "30");
	 Parameter parameter2 = new Parameter("Blocks products with the name of the parameter", "Product Block", "BANANAS");
	 Parameter parameter3 = new Parameter("Blocks the creating of product that belong to this category", "Block category", "VEGETABLES");
	 parameter3.addCategory(vegetables);

	 parameterRepository.save(parameter1);
	 parameterRepository.save(parameter2);
	 parameterRepository.save(parameter3);

 };
}
}
