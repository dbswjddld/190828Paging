package dept;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	public static Connection connect() {
		Connection conn = null;
		try {
			//연결 URL, 드라이버명
			String jdbc_url = "jdbc:oracle:thin:@localhost:1521:xe";
			
			//2.DB연결
			return DriverManager.getConnection(jdbc_url, "hr", "hr");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
