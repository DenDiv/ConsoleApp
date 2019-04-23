import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PhonesTable extends DBTableWorker {
	
	protected String table_name()
	{
		return "PHONES";
	}

  public String[][] all_by_person_id(int person_id)
  {
    ArrayList<String[]> result = new ArrayList<String[]>();
    String sql = 
        "SELECT person_id, phone FROM phones WHERE person_id = ? ORDER BY phone";
    
    try{  
      PreparedStatement pstmt = connection.cbase.prepareStatement(sql);  
      pstmt.setInt(1, person_id); 
      pstmt.execute();
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
        String[] row = new String[2];
        row[0] = rs.getString(1);
        row[1] = rs.getString(2);
        result.add(row);
      }
      pstmt.close();
    }catch(Exception e){ 
      System.out.println(sql);
      System.out.println(e);
    }
    String[][] str_result = new String[result.size()][4];
    return result.toArray(str_result);
  }
	
	protected String[] column_definitions()
	{
		return new String[]
      {"PERSON_ID number(38) REFERENCES PEOPLE(id)", 
       "PHONE varchar2(12 CHAR)",
       "PRIMARY KEY(PERSON_ID, PHONE)"};
	}
	
	public String[] column_names()
	{
		return new String[]
	    {"PERSON_ID", 
       "PHONE"};
	}
}
