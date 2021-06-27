<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	String url_mysql = "jdbc:mysql://localhost/weather?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

   String WhereDefault = "select email, name, pw, phone, image, indate, outdate from user";
            
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"user_recorded"  : [ 
<%
        while (rs.next()) {
            if (count == 0) {

            }else{
%>
            , 
<%
            }
%>            
			{
			"email" : "<%=rs.getString(1) %>", 
			"name" : "<%=rs.getString(2) %>", 
			"pw" : "<%=rs.getString(3) %>",   
			"phone" : "<%=rs.getString(4) %>",
			"image" : "<%=rs.getString(5) %>",
			"indate" : "<%=rs.getString(6) %>",  
			"outdate" : "<%=rs.getString(7) %>"
			}

<%		
        count++;
        }
%>
		  ] 
		} 
<%		
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>
