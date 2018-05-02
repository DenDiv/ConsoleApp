import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PeopleTable extends DBTableWorker {
	
	protected String table_name()
	{
		return "PEOPLE";
	}
	
	protected String[] column_definitions()
	{
		return new String[]
			     {"ID number(38) PRIMARY KEY", 
				    "LAST_NAME varchar2(32 CHAR) NOT NULL", 
		        "FIRST_NAME varchar2(32 CHAR) NOT NULL",
		        "SECOND_NAME varchar2(32 CHAR)"};
	}
	
	public String[] column_names()
	{
		return new String[]
			   {"ID", 
			    "LAST_NAME", 
	        "FIRST_NAME",
	        "SECOND_NAME"};
	}

	public void create()
	{
		super.create();
		String sql = "CREATE SEQUENCE PEOPLE_ID_SEQ";
		try{  
			Statement stmt = connection.cbase.createStatement();  
	
			stmt.executeUpdate(sql);  
		}catch(Exception e){ 
			System.out.println(sql);
			System.out.println(e);
		}  
	}
	
	public void drop()
	{
		String sql = "DROP SEQUENCE PEOPLE_ID_SEQ";
		try{  
			Statement stmt = connection.cbase.createStatement();  
	
			stmt.executeUpdate(sql);  
		}catch(Exception e){ 
			System.out.println(sql);
			System.out.println(e);
		}  
		super.drop();
	}
	
	public String[][] all()
	{
		ArrayList<String[]> result = new ArrayList<String[]>();
		String sql = 
				"SELECT last_name, first_name, second_name FROM people ORDER BY last_name, first_name, second_name";
		
		try{  
			Statement stmt = connection.cbase.createStatement();  
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String[] row = new String[4];
				row[0] = Integer.toString(rs.getRow());
				row[1] = rs.getString(1);
				row[2] = rs.getString(2);
				row[3] = rs.getString(3);
				result.add(row);
			}
		}catch(Exception e){ 
			System.out.println(sql);
			System.out.println(e);
		}
		String[][] str_result = new String[result.size()][4];
		return result.toArray(str_result);
	}
	
	public void create_one(String[] data)
	{
		String sql = "INSERT INTO people(id, last_name, first_name, second_name) VALUES(people_id_seq.nextval, " + 
				data_to_field(data[0]) + ", " + 
				data_to_field(data[1]) + ", " + 
				data_to_field(data[2]) + ")";
		try{  
			Statement stmt = connection.cbase.createStatement();  
			stmt.executeUpdate(sql);
		}catch(Exception e){ 
			System.out.println(e);
		}
	}
}
