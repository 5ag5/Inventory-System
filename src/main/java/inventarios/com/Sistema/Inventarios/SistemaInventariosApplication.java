package inventarios.com.Sistema.Inventarios;

import inventarios.com.Sistema.Inventarios.Models.*;
import inventarios.com.Sistema.Inventarios.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.crypto.Cipher.*;

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
								  CategoryRepository categoryRepository,
								  AuditRepository auditRepository){
 return (args) -> {

	UserInventory user1 = new UserInventory("user11","Gonzalez", passwordEncoder.encode("user11"),"correo1@gmail.com", UserType.ADMIN);
	UserInventory user2 = new UserInventory("user22","Garcia",passwordEncoder.encode("user456"),"correo2@gmail.com",UserType.CASHIER);
	 UserInventory user3 = new UserInventory("user33","Guzman",passwordEncoder.encode("user789"),"correo3@gmail.com",UserType.WORKER);
	UserInventory user4 = new UserInventory("user44","Rodriguez",passwordEncoder.encode("user012"),"correo4@gmail.com", UserType.SUPERVISOR);
	UserInventory user5 = new UserInventory("user55","De Zubiria",passwordEncoder.encode("user345"),"correo5@gmail.com",UserType.WORKER);
	 user4.setStatus(false);

	 Category vegetables = new Category("VEGETABLES", true);
	 Category videoGames = new Category("VIDEO GAMES",true);

	 Product cavages = new Product("Cavage is a vegetable that we sell", 200, 12.20, 18.99,
	 20, 1000, true);

	 Product fifa2023 = new Product("football game created by EA Sports",45,10.00,40.00,
			 3,1000,true);

	 List<Character> ans = new ArrayList<Character>();

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

	 KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	 SecretKey myDesKey = keyGenerator.generateKey();
	 Cipher desCipher = getInstance("AES");
	 desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

	 byte []bytesEncrypted = desCipher.doFinal(String.valueOf(InetAddress.getLocalHost()).getBytes());
	 String textEncrypted = new String(bytesEncrypted);

	 Audit audit1 = new Audit(
			 ActionAudit.UPDATE,
			 textEncrypted,
			 LocalDate.now(),
			 tableNames.USERINVENTORY.getIdTable(),
			 2L,
			 tableNames.USERINVENTORY);


	 Audit audit2 = new Audit(
			 ActionAudit.INSERT,
			 textEncrypted,
			 LocalDate.now(),
			 tableNames.USERINVENTORY.getIdTable(),
			 2L,
			 tableNames.USERINVENTORY);

	 Audit audit3 = new Audit(
			 ActionAudit.DELETE,
			 textEncrypted,
			 LocalDate.now(),
			 tableNames.USERINVENTORY.getIdTable(),
			 2L,
			 tableNames.USERINVENTORY);

	 Audit audit4 = new Audit(
			 ActionAudit.EXIT,
			 textEncrypted,
			 LocalDate.now(),
			 tableNames.USERINVENTORY.getIdTable(),
			 3L,
			 tableNames.USERINVENTORY);

	 Audit audit5 = new Audit(
			 ActionAudit.LOGIN,
			 textEncrypted,
			 LocalDate.now(),
			 tableNames.USERINVENTORY.getIdTable(),
			 3L,
			 tableNames.USERINVENTORY);

	 Audit audit6 = new Audit(
			 ActionAudit.LOGIN,
			 textEncrypted,
			 LocalDate.now(),
			 tableNames.USERINVENTORY.getIdTable(),
			 3L,
			 tableNames.USERINVENTORY);


	 user2.addAudit(audit1);
	 user2.addAudit(audit2);
	 user2.addAudit(audit3);
	 user3.addAudit(audit4);
	 user3.addAudit(audit5);
	 user3.addAudit(audit6);

	 userInventoryRepository.save(user1);
	 userInventoryRepository.save(user2);
	 userInventoryRepository.save(user3);
	 userInventoryRepository.save(user4);
	 userInventoryRepository.save(user5);

	 auditRepository.save(audit1);
	 auditRepository.save(audit2);
	 auditRepository.save(audit3);
	 auditRepository.save(audit4);
	 auditRepository.save(audit5);
	 auditRepository.save(audit6);


 };
}
}
