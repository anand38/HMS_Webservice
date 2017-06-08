package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

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
	    
	    public String getName(String email) throws Exception{
	    	dbConnect();
	    	String name="";
	    	String sql="select name from CANDIDATE_PERSONAL where candidate_id=(select candidate_id from CANDIDATE_LOGIN where email=?)";
	    	PreparedStatement pstmt=con.prepareStatement(sql);
	    	pstmt.setString(1, email);
	    	ResultSet rst=pstmt.executeQuery();
	    	while(rst.next()){
	    		name=rst.getString("name");
	    	}
	    	dbClose();
	    	return name;
	    }
	    
	    public String getId(String email) throws Exception{
	    	String id="";
	    	dbConnect();
	    	String sql="select candidate_id from CANDIDATE_LOGIN where email=?";
	    	PreparedStatement pstmt=con.prepareStatement(sql);
	    	pstmt.setString(1, email);
	    	ResultSet rst=pstmt.executeQuery();
	    	while(rst.next()){
	    		id=String.valueOf(rst.getInt("candidate_id"));
	    	}
	    	dbClose();
	    	return id;
	    }
}
