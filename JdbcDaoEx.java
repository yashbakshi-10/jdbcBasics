package importing_creating_assigning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class JdbcDaoEx {
	
	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		int roll;
		String name,place,s_date;
		Date date=new Date();
		System.out.println("enter name:");
		name=sc.next();
		System.out.println("Enter place name:");
		place= sc.next();
		System.out.println("date (yyyy-mm-dd) :");
		s_date=sc.next();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date=sdf.parse(s_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StudentDAO dao=new StudentDAO();
		dao.addStudent(name, place, date);
		
	}

}

class StudentDAO{
	
	Student s=new Student();	//create obj of the class
	
	String url="jdbc:mysql://localhost:3306/test"; //this url, user-name & password can vary for other systems
	String username="root";
	String password="root";
	
	Connection con;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	
	int result_Q;
	String query;
	
	/*new constructor to initialize
	 *Driver class & connection
	 */
	
	
	public StudentDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			System.out.println(e);
			
		}	//load j.d.b.c
	}
	
	
	//method to get student data from table 
	public Student getStudent(int rollno) {
		query="select s_name from student_table where sr_no="+rollno;	//query to run
		
		try {
			
			st=con.createStatement();
			ResultSet rs=st.executeQuery(query);
			rs.next();
			s.Name=rs.getString(1);
			
		} catch (Exception e) {
			System.out.println(e);
			
		}	//load j.d.b.c.
		
		
		s.sr_no=rollno;
		
		return s;
		
	}
	
	public void addStudent(String name, String place, Date date) {
		
		query= "insert into student_table (s_name,city,dob) values (?,?,?);";
		
		long milis=date.getTime();
		java.sql.Date sqlDate=new java.sql.Date(milis);
		
		try {
			
			pst=con.prepareStatement(query);	//use of prepared statement as its easier to use
			pst.setString(1, name);
			pst.setString(2, place);
			pst.setDate(3, sqlDate);
			pst.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}

class Student{
	
	int sr_no;
	String Name;
	String Place;
	Date date;
	
	public Student(int sr_no, String name, String place, Date date) {
		
		this.sr_no = sr_no;
		Name = name;
		Place = place;
		this.date = date;
	}

	public Student() {
		//empty constructor 
	}
	
	
}
