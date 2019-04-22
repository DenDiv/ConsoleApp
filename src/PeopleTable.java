import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
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
	
  public String[] find_by_position(int position)
  {
    String[] result = new String[4];
    String sql = 
        "SELECT id, last_name, first_name, second_name FROM people ORDER BY last_name, first_name, second_name FETCH FIRST ROW ONLY OFFSET ? ROWS";    
    try{  
      PreparedStatement pstmt = connection.cbase.prepareStatement(sql);  
      pstmt.setInt(1, position); 
      pstmt.execute();
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
        result[0] = rs.getString(0);
        result[1] = rs.getString(1);
        result[2] = rs.getString(2);
        result[3] = rs.getString(3);
      }
    }catch(Exception e){ 
      System.out.println(sql);
      System.out.println(e);
    }
    return result;
  }

  public void create_one(String[] data)
	{
		String sql = "INSERT INTO people(id, last_name, first_name, second_name) VALUES(people_id_seq.nextval, ?, ?, ?)"; 
		try{  
		  PreparedStatement pstmt = connection.cbase.prepareStatement(sql);  
      pstmt.setString(1, data_to_field(data[0])); 
      pstmt.setString(2, data_to_field(data[1])); 
      pstmt.setString(3, data_to_field(data[2])); 
			pstmt.executeUpdate();
		}catch(Exception e){ 
			System.out.println(e);
		}
	}
}
