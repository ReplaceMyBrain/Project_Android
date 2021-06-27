<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	//String email = request.getParameter("email");

	String url_mysql = "jdbc:mysql://localhost/weather?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

    
    int count = 0;
    PreparedStatement ps = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

	String A = "select ad_title,ad_url,ad_image from ad";
   	 String B =" where not indate is null";

	ps = conn_mysql.prepareStatement(A+B);
	//ps.setString(1, email);

        ResultSet rs = ps.executeQuery(); // 
%>
		{ 
  			"ad_info"  : [ 
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
			"title" : "<%=rs.getString(1) %>", 
			"url" : "<%=rs.getString(2) %>",   
			"image" : "<%=rs.getString(3) %>"
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
