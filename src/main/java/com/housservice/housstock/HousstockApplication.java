package com.housservice.housstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@SpringBootApplication
public class HousstockApplication {

	public static void main(String[] args) {
		SpringApplication.run(HousstockApplication.class, args);
	}


	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
//	@Bean
//	CommandLineRunner run(RolesService rolesService){
//		return args -> {
//			rolesService.createNewRoles(new RolesDto(null,"ROLE_RH"));
//			rolesService.createNewRoles(new RolesDto(null,"ROLE_ADMIN"));
//			rolesService.createNewRoles(new RolesDto(null,"ROLE_COMMERCIALE"));
//			rolesService.createNewRoles(new RolesDto(null,"ROLE_DEVELOPEMENT"));
//			rolesService.createNewRoles(new RolesDto(null,"ROLE_PRODUCTION"));
////			rolesService.createNewRoles(new RolesDto(null,"ROLE_CHEF_ATELIER"));
//			rolesService.createNewRoles(new RolesDto(null,"ROLE_CONDUCTEUR_MACHINE"));
//		};
//	}


}
