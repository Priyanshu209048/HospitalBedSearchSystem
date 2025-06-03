package com.project.hospitalbedsearchsystem;

import com.project.hospitalbedsearchsystem.dao.RoleDao;
import org.modelmapper.ModelMapper;
import com.project.hospitalbedsearchsystem.entities.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HospitalBedSearchSystemApplication implements CommandLineRunner {

	RoleDao roleDao;

	public HospitalBedSearchSystemApplication(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public static void main(String[] args) {
		SpringApplication.run(HospitalBedSearchSystemApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public void addRole() {
		roleDao.save(new Role(1, "ROLE_PATIENT"));
		roleDao.save(new Role(2, "ROLE_HOSPITAL"));
	}

	@Override
	public void run(String... args) throws Exception {
		addRole();
	}
}
