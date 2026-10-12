
package utilsLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import configLayer.ConfigReader;
import configLayer.Log;

public class DBUtils {

	private static Connection con;
	private static Statement stm;

	// Open connection (call once before all tests)
	public static void connectToDB() {

		try {

			Class.forName(ConfigReader.get("drivername"));

			String url = ConfigReader.get("dbURL");
			String username = ConfigReader.get("dbUsername");
			String password = ConfigReader.get("dbPassword");

			con = DriverManager.getConnection(url, username, password);

			stm = con.createStatement();

			System.out.println("Connection opened successfully");

		} catch (Exception e) {

			e.printStackTrace();
			throw new CustomException("Failed to connect to database", e);
		}
	}

	public static Map<String, String> selectQuery_Map(String query) throws SQLException {

		Map<String, String> resultMap = new HashMap<String, String>();

		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {

				for (int i = 1; i <= metaData.getColumnCount(); i++) {

					String columnName = metaData.getColumnName(i);

					String columnValue = rs.getString(i);

					resultMap.put(columnName, columnValue);
				}
			}
		}

		return resultMap;
	}

	public static List<Map<String, String>> selectQuery_MapList(String query) throws SQLException {

		List<Map<String, String>> resultList = new ArrayList<>();

		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {

				Map<String, String> rowData = new HashMap<String, String>();

				for (int i = 1; i <= metaData.getColumnCount(); i++) {

					String columnName = metaData.getColumnName(i);

					String columnValue = rs.getString(i);

					rowData.put(columnName, columnValue);
				}

				resultList.add(rowData);
			}
		}

		return resultList;
	}

	public static List<String> executeQueryToDB(String query) throws SQLException {

		List<String> record = new ArrayList<String>();

		try (Statement stnt = con.createStatement(); ResultSet rs = stnt.executeQuery(query)) {

			try {

				int cols = rs.getMetaData().getColumnCount();

				System.out.println("cols " + cols);

				if (rs.next()) {

					for (int i = 1; i <= cols; i++) {

						System.out.println(rs.getString(i));

						record.add(rs.getString(i));
					}
				}

				System.out.println("array" + record);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return record;
	}

	public static boolean getBooleanFromDB(String value) {
		return "1".equals(value);
	}

	// Close database connection
	public static void disconnectFromDB() {

	    if (stm != null) {
	        try {
	            stm.close();
	            Log.info("Database statement closed successfully");
	        } catch (SQLException e) {
	            Log.error("Failed to close database statement: " + e.getMessage());
	        } finally {
	            stm = null;
	        }
	    }

	    if (con != null) {
	        try {
	            con.close();
	            Log.info("Database connection closed successfully");
	        } catch (SQLException e) {
	            Log.error("Failed to close database connection: " + e.getMessage());
	        } finally {
	            con = null;
	        }
	    }
	}
}
