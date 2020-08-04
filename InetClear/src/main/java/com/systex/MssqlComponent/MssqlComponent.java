package com.systex.MssqlComponent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class MssqlComponent {

	private final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String URL;
	private Connection conn = null;

	public MssqlComponent(String URL) {
		this.URL = URL;
	}

	/**
	 * @param url
	 * @return Connect result
	 * @throws SQLException
	 * @throws Exception
	 */
	public Connection connect() throws SQLException, Exception {
		if (this.conn == null) {
			return DriverManager.getConnection(URL);
		}
		return this.conn;
	}

	public void closeConnection() throws SQLException {
		this.conn.close();
	}

	/**
	 * @param sql
	 * @return prepared execute result
	 * @throws SQLException
	 * @throws Exception
	 */
	public int SQL(String sql) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		conn = connect();
		pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	/**
	 * @param sql
	 * @param parameter
	 * @return prepared execute result
	 * @throws SQLException
	 * @throws Exception
	 */
	public int SQL(String sql, LinkedHashMap<String, Object> parameter) throws SQLException, Exception {
		PreparedStatement pstmt = null;

		if (sql.trim() == "" || parameter.isEmpty()) {
			return 0;
		}

		conn = connect();
		pstmt = conn.prepareStatement(sql);
		int count = 1;
		for (String key : parameter.keySet()) {
			pstmt.setObject(count, parameter.get(key));
			count++;
		}
		return pstmt.executeUpdate();
	}

	/**
	 * @param sql
	 * @param parameter
	 * @return prepared Batch result
	 * @throws SQLException
	 * @throws Exception
	 */
	public int[] SQL_Batch(String sql, List<LinkedHashMap<String, Object>> parameter) throws SQLException, Exception {

		PreparedStatement pstmt = null;
		int[] result = null;

		if (sql.trim() == "" || parameter.isEmpty()) {

			return result;
		}
		conn = connect();
		pstmt = conn.prepareStatement(sql);
		int count = 1;
		for (LinkedHashMap<String, Object> record : parameter) {
			for (String key : record.keySet()) {
				pstmt.setObject(count, record.get(key));
				count++;
			}
			count = 1;
			pstmt.addBatch();
		}
		result = pstmt.executeBatch();
		return result;
	}

	/**
	 * @param sql
	 * @return prepared query result
	 * @throws SQLException
	 * @throws Exception
	 */
	public ResultSet Query(String sql) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		conn = connect();
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * @param sql
	 * @param parameter
	 * @return prepared query result
	 * @throws SQLException
	 * @throws Exception
	 */
	public ResultSet Query(String sql, LinkedHashMap<String, Object> parameter) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		conn = connect();
		pstmt = conn.prepareStatement(sql);
		if (!parameter.isEmpty()) {
			int count = 1;
			for (String key : parameter.keySet()) {
				pstmt.setObject(count, parameter.get(key));
				count++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

}
