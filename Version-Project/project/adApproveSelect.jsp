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

	String A = "select a.ad_title, a.ad_url, a.ad_price, a.user_email, a.ad_image, a.id_ad from ad as a join payment as p";
   	 String B =" where a.id_ad = p.ad_id_ad and not a.ad_checkdate is null and not p.payment_indate is null and a.ad_outdate is null;";

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
			"price" : "<%=rs.getString(3) %>",
			"email" : "<%=rs.getString(4) %>",
			"image" : "<%=rs.getString(5) %>",
			"adid" : "<%=rs.getString(6) %>"
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
