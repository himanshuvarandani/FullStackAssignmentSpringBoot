import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SaveDriveDetails {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/chandra";

    //  Database credentials
    static final String USER = "chandra";
    static final String PASS = "possword";
    
    public static Map<String, String> getFolderDetails(File folder, int parentId, int newId) {
    	String values = "";
    	int folders = 0;
    	int files = 0;
    	
    	File[] content = folder.listFiles();
    	
    	for (File obj: content) {
    		if (obj.isFile()) {
    			values += String.format(", (%2d, '%s', 'file', 0, 0, %2d)", newId, obj.getName(), parentId);
    			files += 1;
    			newId += 1;
    		} else {
    			Map<String, String> map = getFolderDetails(obj, newId, newId+1);
    			values += String.format(", (%2d, '%s', 'folder', %s, %s, %2d)", newId, obj.getName(), map.get("folders"), map.get("files"), parentId);
    			if (map.get("values") != "") {
    				values += map.get("values");
    			}
    			folders += 1;
    			newId = Integer.valueOf(map.get("newId"));
    		}
    	}
    	
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("values", values);
    	map.put("folders", String.valueOf(folders));
    	map.put("files", String.valueOf(files));
    	map.put("newId", String.valueOf(newId));
    	return map;
    }
	
    public static void main(String[] args) {
    	Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open a connection
			System.out.println("Connecting to database..." + JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();

			File drive = new File("C:\\Users\\Padmavathi\\Downloads\\TCS\\D");
			
			String sql;
			sql = "SELECT count(*) as total FROM chandra.drives";
			
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int newId = rs.getInt("total")+1;
			
			String driveName = "D:";
			
			System.out.println("Fetching Drive Details...");
			Map<String, String> map = getFolderDetails(drive, newId, newId+1);
			String values = String.format("(%2d, '%s', 'drive', %s, %s, 0)", newId, driveName, map.get("folders"), map.get("files"));
			if (map.get("values") != "") {
				values += map.get("values");
			}
			
			System.out.println("Inserting into Database...");
			sql = "INSERT INTO chandra.drives VALUES " + values;
			stmt.executeUpdate(sql);
			System.out.println("Completed...");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}
}
