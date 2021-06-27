<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String email = null;
	email = request.getParameter("email");

	String url_mysql = "jdbc:mysql://localhost/weather?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select a.id_ad, a.ad_title, a.ad_url, a.ad_image, a.ad_price, a.ad_location, date_format(a.ad_indate, '%Y-%m-%d') as ad_indate, date_format(a.ad_outdate, '%Y-%m-%d') as ad_outdate, p.payment_indate, p.payment_outdate, p.id_payment from ad as a inner join payment as p on a.id_ad=p.ad_id_ad where a.user_email='"+email+"' and a.ad_checkdate is not null and p.payment_outdate is null and date(a.ad_outdate) <= date_format(now(), '%Y-%m-%d')";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>		{ 
  			"adpayment_info"  : [ 
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
			"id_ad" : "<%=rs.getString(1) %>", 
			"ad_title" : "<%=rs.getString(2) %>",
			"ad_url" : "<%=rs.getString(3) %>",  
			"ad_image" : "<%=rs.getString(4) %>",
			"ad_price" : "<%=rs.getString(5) %>",  
			"ad_location" : "<%=rs.getString(6) %>",     
			"ad_indate" : "<%=rs.getString(7) %>",
			"ad_outdate" : "<%=rs.getString(8) %>", 
			"payment_indate" : "<%=rs.getString(9) %>", 
			"payment_outdate" : "<%=rs.getString(10) %>",   
			"id_payment" : "<%=rs.getString(11) %>"
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
