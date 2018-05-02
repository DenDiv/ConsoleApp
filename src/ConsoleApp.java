import java.util.*;

public class ConsoleApp {
	private PeopleTable pt;
	private PhonesTable pht;

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
		Scanner in = new Scanner(System.in);
		String password;
		String user;
		System.out.println("Введите пользователя БД:");
		user = in.next();
		System.out.println("Введите пароль пользователя БД:");
		password = in.next();
		DBConnection con = DBConnection.connect(user, password);
		while(con.cbase == null) {
			System.out.println("Указана неверная комбинация пользовательское имя/пароль! Повторите ввод!");
			System.out.println("Введите пользователя БД:");
			user = in.next();
			System.out.println("Введите пароль пользователя БД:");
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
				"Добро пожаловать!\n" + 
				"Основное меню (выберите цифру в соответствии с необходимым действием):\n" + 
				"1 - просмотр людей;\n" +
				"2 - сброс и инициализация таблиц;\n" +
				"9 - выход.\n"
		);
	}
	
	public int after_main_menu(int next_step)
	{
		if(next_step == 2) {
			initialize();
			System.out.println("Таблицы созданы заново!");
			return 0;
		}
		else if(next_step != 1 && next_step != 9)
		{
			System.out.println("Выбрано неверное число! Повторите ввод!");
			return 0;
		}
	  return next_step;
	}
	
	public void show_people()
	{
		System.out.print(
				"Просмотр списка людей!\n" + 
				"№\tФамилия\tИмя\tОтчество\n"
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
				"Дальнейшие операции:\n" + 
						"0 - возврат в главное меню;\n" +
						"3 - добавление нового человека;\n" +
						"4 - удаление человека;\n" +
						"5 - просмотр телефонов человека;\n" +
						"9 - выход.\n"
		);
	}
	
	public int after_show_people(int next_step)
	{
		
		if(next_step == 4 || next_step == 5) 
		{
			System.out.println("Пока не реализовано!");
			return 1;
		}
		else if(next_step != 0 && next_step != 9 && next_step != 3)
		{
			System.out.println("Выбрано неверное число! Повторите ввод!");
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
		// Не реализована проверка на максимальную длину строк. Нужно доделать самостоятельно!
		String[] data = new String[3];
		in.nextLine();
		System.out.println("Введите фамилию (1 - отмена):");
		data[0] = in.nextLine();
		if(data[0].equals("1")) return;
		while(data[0].trim().length() == 0) {
			System.out.println("Фамилия не может быть пустой! Введите фамилию заново (1 - отмена):");
			data[0] = in.nextLine();
			if(data[0].equals("1")) return;
		}
		System.out.println("Введите имя (1 - отмена):");
		data[1] = in.nextLine();
		if(data[1].equals("1")) return;
		while(data[1].trim().length() == 0) {
			System.out.println("Имя не может быть пустым! Введите имя заново (1 - отмена):");
			data[1] = in.nextLine();
			if(data[1].equals("1")) return;
		}
		System.out.println("Введите отчество (1 - отмена):");
		data[2] = in.nextLine();
		if(data[2].equals("1")) return;
		pt.create_one(data);
	}
	
	public void main_cycle()
	{
		int current_menu = 0;
    Scanner in = new Scanner(System.in);
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
				current_menu = after_show_people(next_step);
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
		System.out.println("До свидания!");
		in.close();
	}
	
	public static void main(String args[]){  
		ConsoleApp ca = new ConsoleApp();
		ca.main_cycle();
		DBConnection.disconnect();
	}
}
