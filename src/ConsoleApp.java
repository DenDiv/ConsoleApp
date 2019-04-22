import java.util.*;

public class ConsoleApp {
	private PeopleTable pt;
	private PhonesTable pht;
	private Scanner in;

	public void create()
	{
		pt.create();	
		pht.create();
	}
	
	public void drop()
	{
		pht.drop();
		pt.drop();	
	}

	public ConsoleApp()
	{
		String password;
		String user;
		in = new Scanner(System.in);
		System.out.println("������� ������������ ��:");
		user = in.next();
		System.out.println("������� ������ ������������ ��:");
		password = in.next();
		DBConnection con = DBConnection.connect(user, password);
		while(con.cbase == null) {
			System.out.println("������� �������� ���������� ���������������� ���/������! ��������� ����!");
			System.out.println("������� ������������ ��:");
			user = in.next();
			System.out.println("������� ������ ������������ ��:");
			password = in.next();
			con = DBConnection.reconnect(user, password);
		}
		pt = new PeopleTable();
		pht = new PhonesTable();
	}
	
	public void initialize()
	{
		try {
			drop();
			create();
		} catch(Exception e){ 
			System.out.println(e);
		} 
	}

	public void show_main_menu()
	{
		System.out.print(
			"����� ����������!\n" + 
			"�������� ���� (�������� ����� � ������������ � ����������� ���������):\n" + 
			"1 - �������� �����;\n" +
			"2 - ����� � ������������� ������;\n" +
			"9 - �����.\n"
		);
	}
	
	public int after_main_menu(int next_step)
	{
		if(next_step == 2) {
			initialize();
			System.out.println("������� ������� ������!");
			return 0;
		}
		else if(next_step != 1 && next_step != 9)
		{
			System.out.println("������� �������� �����! ��������� ����!");
			return 0;
		}
	  return next_step;
	}
	
	public void show_people()
	{
		System.out.print(
			"�������� ������ �����!\n" + 
			"�\t�������\t���\t��������\n"
		);
		String[][] list = pt.all();
		for(int i = 0; i < list.length; i++) {
			System.out.print(
				list[i][0] + "\t" +
				list[i][1] + "\t" +
				list[i][2] + "\t" +
				list[i][3] + "\n"
			);
		}
		System.out.print(
			"���������� ��������:\n" + 
			"0 - ������� � ������� ����;\n" +
			"3 - ���������� ������ ��������;\n" +
			"4 - �������� ��������;\n" +
			"5 - �������� ��������� ��������;\n" +
			"9 - �����.\n"
		);
	}
	
  public int show_phones_by_people(Scanner in)
  {
    String[] data = new String[3];
    String[] person;
    System.out.print(
      "���������� ��������:\n" + 
      "0 - ������� � ������� ����;\n" +
      "3 - ���������� ������ ��������;\n" +
      "4 - �������� ��������;\n" +
      "9 - �����.\n"
    );
    System.out.println("������� ����� ������, � ������� �������� ������������ ��� ������� (0 - ������):");
    in.nextLine();
    data[0] = in.nextLine();
    while(data[0].trim().length() == 0) {
      System.out.println("������ ������. ��������� ����! ������� ����� ������, � ������� �������� ������������ ��� ������� (0 - ������):");
      data[0] = in.nextLine();
      person = pt.find_by_position(Integer.parseInt(data[0]));
      System.out.println(person[1]);
    }
    if(data[0].equals("0")) return 1;
  }

  public int after_show_people(int next_step, Scanner in)
	{
		
		if(next_step == 4) 
		{
			System.out.println("���� �� �����������!");
			return 1;
		}
		else if(next_step == 5) 
		{
		  return show_phones_by_people(in);
		}
		else if(next_step != 0 && next_step != 9 && next_step != 3)
		{
			System.out.println("������� �������� �����! ��������� ����!");
			return 1;
		}
	  return next_step;
	}

	public int read_next_step(Scanner in)
	{
		return in.nextInt();
	}
	
	public void show_add_person(Scanner in)
	{
		// �� ����������� �������� �� ������������ ����� �����. ����� �������� ��������������!
		String[] data = new String[3];
		in.nextLine();
		System.out.println("������� ������� (1 - ������):");
		data[0] = in.nextLine();
		if(data[0].equals("1")) return;
		while(data[0].trim().length() == 0) {
			System.out.println("������� �� ����� ���� ������! ������� ������� ������ (1 - ������):");
			data[0] = in.nextLine();
			if(data[0].equals("1")) return;
		}
		System.out.println("������� ��� (1 - ������):");
		data[1] = in.nextLine();
		if(data[1].equals("1")) return;
		while(data[1].trim().length() == 0) {
			System.out.println("��� �� ����� ���� ������! ������� ��� ������ (1 - ������):");
			data[1] = in.nextLine();
			if(data[1].equals("1")) return;
		}
		System.out.println("������� �������� (1 - ������):");
		data[2] = in.nextLine();
		if(data[2].equals("1")) return;
		pt.create_one(data);
	}
	
	public void main_cycle()
	{
		int current_menu = 0;
	    int next_step;
	    while(current_menu != 9)
		{
			switch(current_menu){
			case 0:
				show_main_menu();
				next_step = read_next_step(in);
				current_menu = after_main_menu(next_step);
				break;
			case 1:
				show_people();
				next_step = read_next_step(in);
				current_menu = after_show_people(next_step, in);
				break;
			case 2:
				show_main_menu();
				break;
			case 3:
				show_add_person(in);
				current_menu = 1;
				break;
			}
		}
		System.out.println("�� ��������!");
		in.close();
	}
	
	public static void main(String args[]){  
		ConsoleApp ca = new ConsoleApp();
		ca.main_cycle();
		DBConnection.disconnect();
	}
}
