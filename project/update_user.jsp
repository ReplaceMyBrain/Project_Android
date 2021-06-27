<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String email = request.getParameter("email");
	String nEmail = request.getParameter("nEmail");
	String name = request.getParameter("name");
	String phone = request.getParameter("phone");
	String password = request.getParameter("password");
		
//------
	String url_mysql = "jdbc:mysql://localhost/weather?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인 

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();

	String A;

		if (nEmail!=null){
			A = "update user set email = ? where email = ?";
			ps = conn_mysql.prepareStatement(A);
	    		ps.setString(1, nEmail);
		}else if (name!=null) {
			A = "update user set name = ? where email = ?";
			ps = conn_mysql.prepareStatement(A);
	    		ps.setString(1, name);
		}else if (phone!=null) {
			A = "update user set phone = ? where email = ?";
			ps = conn_mysql.prepareStatement(A);
	    		ps.setString(1, phone);
		}else if (password!=null) {
			A = "update user set password = ? where email = ?";
			ps = conn_mysql.prepareStatement(A);
	    		ps.setString(1, password);
		} 
	
	    ps.setString(2, email);
		
		result = ps.executeUpdate();
%>
		{
			"result" : "<%=result%>"
		}

<%		
	    conn_mysql.close();
	} 
	catch (Exception e){
%>
		{
			"result" : "<%=result%>"
		}
<%		
	    e.printStackTrace();
	} 
	
%>

