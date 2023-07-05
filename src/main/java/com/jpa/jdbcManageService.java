package com.jpa;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.jpa.Utils.ManageJdbcConnection;

public class jdbcManageService {

	public boolean connect() throws ClassNotFoundException, SQLException {
		ManageJdbcConnection connect = new ManageJdbcConnection();
		
		int devide = 100000;
		int count = 0;
		boolean check = false;
		//while(true) {
			//if(count % devide == 0) {
				if(connect.getConnection() != null) connect.closeConnection();
				check = insertOrgData(connect.openConnection());
				//break;
			//}
		count++;
		//}
		return check;
	}
	
	public boolean insertOrgData(Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			String sql = "";
			StringBuilder br = new StringBuilder();
			sql = "select * from vo";
			//sql = ("Insert Into " + ") values(" + br + ")");//사용하려다 맘
			pstmt = con.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery(sql);
			ResultSetMetaData metaData = result.getMetaData();
			//pstmt.executeUpdate(sql);//create, update, delete 에 사용
			//con.commit();//executeUpdate일때 사용, 강제로 푸시해줘야할때 사용
			int columnCount = metaData.getColumnCount();
			
			for (int i = 1; i <= columnCount; i++) System.out.print(metaData.getColumnName(i) + " ");
			System.out.println();//강제로 한칸 띄우기
			while (result.next()) {
			    for (int i = 1; i <= columnCount; i++) {
			        Object value = result.getObject(i);
			        // 결과 데이터 처리
			        // 데이터 형식에 따라 적절하게 변환하여 출력
			        instanceOfCheck(value);
			    }
			    System.out.println("\n-------------------");
			}
		} catch(Exception e) {
			try {
				con.rollback();
				con.close();
			} catch (SQLException e1) {
				return false;
			}
			return false;
		} finally {
			if (pstmt != null) { 
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException e2){
					return false;
				}
			} 
		}
		return true;
	}
	
	protected void instanceOfCheck(Object value) {
		 if (value instanceof Integer) {
			 int intValue = (int) value;
			 System.out.print(intValue + " ");
	     } else if (value instanceof Double) {
	    	 double doubleValue = (double) value;
	    	 System.out.print(doubleValue + " ");
	     } else {
	    	 String stringValue = value.toString();
	    	 System.out.print(stringValue + " ");
	     }
	}
}
