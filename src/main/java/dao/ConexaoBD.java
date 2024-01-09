package dao;
import java.sql.Connection;
import java.sql.DriverManager;
public class ConexaoBD {
 static String status="";

public static Connection getConnection(){
 Connection conn = null;
 try {
 Class.forName("com.mysql.jdbc.Driver");
 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uniasselvi?useSSL=false", "root", "root");

 if (conn == null){
 System.out.println("3333333333333 ----> conn está null");
 }
 } catch(Exception e) {
 e.printStackTrace();
 System.err.println("Class Conexao - Exception: " + e.getMessage());
 } finally {
 }
 return conn;
}
}