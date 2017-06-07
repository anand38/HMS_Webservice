package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Account {
	 String user = "anand_ic0Jh4nN";
	    String password="qipzm?123";
	    String url="jdbc:mysql://mysql.storage.cloud.wso2.com:3306/";
	    String dbname="HRM_koce1338";
	    String driver="com.mysql.jdbc.Driver";
	    Connection con;


	    private void dbConnect() throws ClassNotFoundException, SQLException{
	        Class.forName(driver);
	        con = DriverManager.getConnection(url+dbname,user,password);
	    }

	    private void dbClose() throws SQLException{
	        con.close();
	    }
	    
	    public boolean check_user(String email,String password)throws Exception{
	    	dbConnect();
	    	int count=0;
	    	String sql="select count(*) as count from CANDIDATE_LOGIN where email=? and password=?";
	    	PreparedStatement pstmt=con.prepareStatement(sql);
	    	pstmt.setString(1, email);
	    	pstmt.setString(2, password);
	    	ResultSet rst=pstmt.executeQuery();
	    	while(rst.next()){
	    		count=rst.getInt("count");
	    	}
	    	dbClose();
	    	if(count==1){
	    		return true;
	    	}else
	    		return false;
	    }
}
