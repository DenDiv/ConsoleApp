public class PhonesTable extends DBTableWorker {
	
	protected String table_name()
	{
		return "PHONES";
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
