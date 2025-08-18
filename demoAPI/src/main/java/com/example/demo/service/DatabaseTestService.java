package com.example.demo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

@Service
public class DatabaseTestService {

	/**
	 *	DBアクセステスト
	 * @return
	 */
	public String dataBaseAccessTest() {

		StringBuffer sOutput = new StringBuffer();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		//接続文字列
		String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
		String user = "postgres";
		String password = "admin";

		try {
			//PostgreSQLへ接続
			conn = DriverManager.getConnection(url, user, password);

			//自動コミットOFF
			conn.setAutoCommit(false);

			//SELECT文の実行
			stmt = conn.createStatement();
			String sql = "SELECT * from public.\"tblUserInfo\"";
			rset = stmt.executeQuery(sql);

			//SELECT結果の受け取り
			while (rset.next()) {
				String col1 = rset.getString(1);
				String col2 = rset.getString(2);
				String col3 = rset.getString(3);
				String col4 = rset.getString(4);
				String col5 = rset.getString(5);
				String s = col1 + " " + col2 + " " + col3 + " " + col4 + " " + col5 + "\r";
				sOutput.append(s);
			}

			//			//INSERT文の実行
			//			sql = "INSERT INTO jdbc_test VALUES (1, 'AAA')";
			//			stmt.executeUpdate(sql);
			//			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rset != null)
					rset.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return sOutput.toString();
	}
}
