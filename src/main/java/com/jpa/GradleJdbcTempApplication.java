package com.jpa;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jpa.Utils.ManageJdbcConnection;

@SpringBootApplication
public class GradleJdbcTempApplication {
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(GradleJdbcTempApplication.class, args);
		jdbcManageService service = new jdbcManageService();
		service.connect();
	}

}
