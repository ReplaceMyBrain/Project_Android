<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String email = request.getParameter("email");
	String title = request.getParameter("title");
	String url = request.getParameter("url");
	String price = request.getParameter("price");
	String image = request.getParameter("image");
		
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
	
	    String A = "insert into ad (user_email, ad_title, ad_url, ad_price, ad_image, ad_indate";
	    String B = ") values (?,?,?,?,?,now())";
	    
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, email);
	    ps.setString(2, title);
	    ps.setString(3, url);
	    ps.setString(4, price);
	    ps.setString(5, image);
 	    
		
		result = ps.executeUpdate();

	

	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql2 = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql2 = conn_mysql2.createStatement();
	
	    String c = "insert into payment (ad_id_ad, payment_indate";
	    String d = ") values ((select max(id_ad) from ad), now())";
	    
	
	    ps = conn_mysql.prepareStatement(c+d);

 	    
		
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

