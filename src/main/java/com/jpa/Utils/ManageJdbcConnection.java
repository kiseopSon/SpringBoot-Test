package com.jpa.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

public class ManageJdbcConnection {
	
	private String propPath="classpath:db.properties";
	
	public Connection con = null;
	
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		
		Properties props = new Properties();
		
		try {
            props.load(new FileInputStream(ResourceUtils.getFile(propPath)));
        } catch(IOException e) {
            e.printStackTrace();
        }
		System.out.println(props);
//		com.mysql.jdbc.Driver을 사용하려면 mysql-connector-java-5 버전의 파일을 추가해줘야함.
		String driver = props.get("proc.driver").toString();
		String url = props.get("proc.url").toString();
		String username = props.get("db.username").toString();
		String password = props.get("db.password").toString();
		
		//디비 커넥션 설정
		Class.forName(driver);
		con = DriverManager.getConnection(url, username, password);
		con.setAutoCommit(false);
		
		//LOGGER.error(" ============= Connection Open ============= ");
		
		return con;
	}
	
	public  void closeConnection() throws SQLException, ClassNotFoundException {
		
		//LOGGER.error(" conStatus : " + con.isClosed());
		if(!con.isClosed()) con.close();
		
		//LOGGER.error(" ============= Connection Close ============= ");
	}
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		return con;
	}
}
