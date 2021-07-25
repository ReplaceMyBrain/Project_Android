<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String email = null;
	email = request.getParameter("email");

	String url_mysql = "jdbc:mysql://localhost/weather?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String A = "select count(a.id_ad) from ad as a inner join payment as p on a.id_ad=p.ad_id_ad where a.user_email='"+email+"' and p.payment_outdate is null and a.ad_checkdate is not null and date(a.ad_outdate) > date_format(now(), '%Y-%m-%d') and date(a.ad_indate) < date_format(now(), '%Y-%m-%d') UNION ALL";  //now

String B = " select count(a.id_ad) from ad as a inner join payment as p on a.id_ad=p.ad_id_ad where p.payment_outdate is null and a.ad_checkdate is null and a.user_email='"+email+"' UNION ALL"; //wait

String C = " select  count(a.id_ad) from ad as a inner join payment as p on a.id_ad=p.ad_id_ad where a.user_email='"+email+"' and a.ad_checkdate is not null and p.payment_outdate is null and date(a.ad_outdate) <= date_format(now(), '%Y-%m-%d') UNION ALL"; //history

String D = " select count(a.id_ad) from ad as a inner join payment as p on a.id_ad=p.ad_id_ad where a.user_email='"+email+"' and p.payment_outdate is not null"; //cancel
    int count = 0;

    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(A+B+C+D); // 
%>		{ 
  			"adcount_info"  : [ 
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
			"count" : "<%=rs.getInt(1) %>"
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
