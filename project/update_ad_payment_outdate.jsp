<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String id_ad = null;
	String id_payment = null;
	id_ad = request.getParameter("id_ad");
	id_payment = request.getParameter("id_payment");

	String url_mysql = "jdbc:mysql://localhost/weather?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인 
	String A;
	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
	
	if (id_ad!=null){
			A = "update ad set ad_outdate = now() where id_ad = ?";
			ps = conn_mysql.prepareStatement(A);
	    		ps.setString(1, id_ad);
		}
	if (id_payment!=null) {
			A = "update payment set payment_outdate = now() where id_payment = ?";
			ps = conn_mysql.prepareStatement(A);
	    		ps.setString(1, id_payment);
		}
	
		
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

