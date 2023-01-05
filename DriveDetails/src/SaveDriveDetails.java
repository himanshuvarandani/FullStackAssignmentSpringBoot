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
    
    public static void insertIntoDatabase(Statement stmt, int id, String name, String type, int folders, int files, int parentId, String path) {
    	String sql;
    	sql = String.format("INSERT INTO chandra.drives VALUES (%2d, '%s', '%s', %2d, %2d, %2d, '%s')", id, name, type, folders, files, parentId, path);
    	
    	try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static Map<String, String> getFolderDetails(Statement stmt, File folder, int parentId, int newId, String path) {
    	int folders = 0;
    	int files = 0;
    	
    	File[] content = folder.listFiles();
    	
    	for (File obj: content) {
    		if (obj.isFile()) {
    			System.out.println("Inserting a File into Database...");
    			insertIntoDatabase(stmt, newId, obj.getName(), "file", 0, 0, parentId, path);
    			files += 1;
    			newId += 1;
    		} else {
    			System.out.println("Fetching Folder Details...");
    			String newPath = String.format("%s\\\\%s", path, obj.getName());
    			Map<String, String> map = getFolderDetails(stmt, obj, newId, newId+1, newPath);
    			
    			System.out.println("Inserting Folder into Database...");
    			insertIntoDatabase(stmt, newId, obj.getName(), "folder",
    					Integer.valueOf(map.get("folders")), Integer.valueOf(map.get("files")), parentId, path);
    			folders += 1;
    			newId = Integer.valueOf(map.get("newId"));
    		}
    	}
    	
    	Map<String, String> map = new HashMap<String, String>();
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
			
			String sql;
			sql = "SELECT count(*) as total FROM chandra.drives";
			
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int newId = rs.getInt("total")+1;

			File drive = new File("C:\\Users\\Padmavathi\\Downloads\\TCS\\E");
			String driveName = "E";
			String path = "E:";
			
			System.out.println("Fetching Drive Details...");
			Map<String, String> map = getFolderDetails(stmt, drive, newId, newId+1, path);
			
			System.out.println("Inserting Drive into Database...");
			insertIntoDatabase(stmt, newId, driveName, "drive",
					Integer.valueOf(map.get("folders")), Integer.valueOf(map.get("files")), 0, "Drive");
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
