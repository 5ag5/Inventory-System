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
import java.util.Arrays;
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
								  AuditRepository auditRepository,
								  OptionsGraphsRepository optionsGraphsRepository){
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
	 20, 10000, false);

	 Product fifa2023 = new Product("football game created by EA Sports",45,12.20,40.00,
			 20,1000,true);

	 Product nikeSnickers = new Product("Snicker AIr Force snickers",550,150.00,40.00,
			 15,1000,true);

	 OptionsGraphs userLine = new OptionsGraphs("Users","Line Chart", Arrays.asList(new String[]{"Date Registered", "Status", "Last Registered Date", "User Type"}),"api/graphs/UserGraphsCount/","User Line Chart");
	 OptionsGraphs productLine = new OptionsGraphs("Products","Line Chart", Arrays.asList(new String[] {"Status Product", "Quantity Product", "Price Purchase", "Price Sell", "Minimum Stock", "Maximum Stock", "Includes IVA"}),"api/graphs/ProductGraphsCount/","Products Line Chart");
	 OptionsGraphs auditsLine = new OptionsGraphs("Audits","Line Chart", Arrays.asList(new String[] {"Action Audit","Audit Date","ID Table", "ID User","Table Names","Computer IPs"}),"api/graphs/auditGraphsCount/","Audits Line Chart");

	 OptionsGraphs userPie = new OptionsGraphs("Users","Pie Chart", Arrays.asList(new String[] {"User Type", "Status"}),"api/graphs/UserPieGraph/","Users Pie Chart");
	 OptionsGraphs productPie = new OptionsGraphs("Products","Pie Chart", Arrays.asList(new String[] {"Status", "Includes IVA", "Category Product"}),"api/graphs/ProductPieChart/", "Products Pie Chart");
	 OptionsGraphs auditsPie = new OptionsGraphs("Audits","Pie Chart", Arrays.asList(new String[] {"Action", "ID User", "Audit Date"}),"api/graphs/AuditPieChart/","Audits Pie Chart");

	 nikeSnickers.setStatusProduct(false);
	 productRepository.save(cavages);
	 productRepository.save(fifa2023);
	 productRepository.save(nikeSnickers);

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
			 user2.getLogin(),
			 tableNames.USERINVENTORY);
	cavages.addAudit(audit1);

	 Audit audit2 = new Audit(
			 ActionAudit.INSERT,
			 textEncrypted,
			 LocalDate.now().minusDays(3),
			 tableNames.USERINVENTORY.getIdTable(),
			 user2.getLogin(),
			 tableNames.USERINVENTORY);
	 cavages.addAudit(audit2);


	 Audit audit3 = new Audit(
			 ActionAudit.DELETE,
			 textEncrypted,
			 LocalDate.now(),
			 tableNames.USERINVENTORY.getIdTable(),
			 user2.getLogin(),
			 tableNames.USERINVENTORY);
	 nikeSnickers.addAudit(audit3);

	 Audit audit4 = new Audit(
			 ActionAudit.EXIT,
			 textEncrypted,
			 LocalDate.now(),
			 tableNames.USERINVENTORY.getIdTable(),
			 user3.getLogin(),
			 tableNames.USERINVENTORY);

	 Audit audit5 = new Audit(
			 ActionAudit.LOGIN,
			 textEncrypted,
			 LocalDate.now().plusDays(2),
			 tableNames.USERINVENTORY.getIdTable(),
			 user3.getLogin(),
			 tableNames.USERINVENTORY);

	 Audit audit6 = new Audit(
			 ActionAudit.LOGIN,
			 textEncrypted,
			 LocalDate.now(),
			 tableNames.USERINVENTORY.getIdTable(),
			 user4.getLogin(),
			 tableNames.USERINVENTORY);

	 Audit audit7 = new Audit(
			 ActionAudit.UPDATE,
			 textEncrypted,
			 LocalDate.now().plusDays(2),
			 tableNames.PRODUCT.getIdTable(),
			 user1.getLogin(),
			 tableNames.PRODUCT);

	 fifa2023.addAudit(audit7);
	 productRepository.save(cavages);
	 productRepository.save(fifa2023);
	 productRepository.save(nikeSnickers);

	 user2.addAudit(audit2);
	 user2.addAudit(audit1);
	 user2.addAudit(audit3);
	 user3.addAudit(audit4);
	 user3.addAudit(audit5);
	 user3.addAudit(audit6);

	 user1.setDateRegistered(LocalDate.now().minusDays(3));
	 user4.setDateRegistered(LocalDate.now().plusDays(2));

	 user2.setLastRegisteredPassword(LocalDate.now().minusDays(3));
	 user3.setLastRegisteredPassword(LocalDate.now().plusDays(2));

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
	 auditRepository.save(audit7);

	 optionsGraphsRepository.save(userLine);
	 optionsGraphsRepository.save(productLine);
	 optionsGraphsRepository.save(auditsLine);

	 optionsGraphsRepository.save(userPie);
	 optionsGraphsRepository.save(productPie);
	 optionsGraphsRepository.save(auditsPie);


 };
}
}
