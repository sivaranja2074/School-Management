package SchoolManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SchoolManagement.GradeModel.Result;
import SchoolManagement.StudentModel.Gender;
import SchoolManagement.SubjectModel.Subjects;

public class DbConnection {
	private Connection conn;
	private static DbConnection dbConnection;

	private DbConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/schoolmanagement";
		String user = "root";
		String password = "zoho";
		conn = DriverManager.getConnection(url, user, password);
	}

	public Connection getConnection() {

		return conn;
	}

	public static DbConnection getDbConnection() throws ClassNotFoundException, SQLException {
		if (dbConnection == null) {
			dbConnection = new DbConnection();
		}
		return dbConnection;
	}

	public static Map<Integer, StaffModel> getAllStaff() throws ClassNotFoundException {
		HashMap<Integer, StaffModel> Map = new HashMap<Integer, StaffModel>();
		String query = "select staffid,staffName\r\n" + "from Staff;";
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			boolean execute = preparedStmt.execute();
			ResultSet resultSet = preparedStmt.getResultSet();

			while (resultSet.next()) {
				StaffModel staff = new StaffModel();
				staff.setStaffId(resultSet.getInt(1));
				staff.setStaffName(resultSet.getString(2));
				String query1 = "select subjectName\r\n" + "from subjects\r\n" + "inner join staffwithsubject\r\n"
						+ "on subject_id=sub_id && staff_id=" + resultSet.getInt(1) + ";";
				PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
				boolean execute1 = preparedStmt1.execute();
				ResultSet resultSet1 = preparedStmt1.getResultSet();
				while (resultSet1.next()) {
					staff.setName(Subjects.valueOf(resultSet1.getString(1)));
				}
				Map.put(resultSet.getInt(1), staff);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return Map;
	}

	public static Map<Integer, StudentModel> getAllStudent() throws ClassNotFoundException {
		HashMap<Integer, StudentModel> Map = new HashMap<Integer, StudentModel>();
		String query = "SELECT * FROM Student";
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			boolean execute = preparedStmt.execute();
			ResultSet resultSet = preparedStmt.getResultSet();
			while (resultSet.next()) {

				StudentModel student = new StudentModel();
				student.setAdmNo(resultSet.getInt(1));
				student.setStudentName(resultSet.getString(2));
				student.setGender(resultSet.getString(3));
				Map.put(resultSet.getInt(1), student);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return Map;
	}

	public static Map<Integer, StudentModel> getRank() throws ClassNotFoundException {
		HashMap<Integer, StudentModel> Map = new HashMap<Integer, StudentModel>();
		String query = " select s.ad_no,s.studentName,sr.total,sr.grade_status\r\n" + "  from StudentReport as sr\r\n"
				+ "  inner join Student As s\r\n" + "  on sr.student_id=s.ad_no\r\n" + "  ORDER BY total DESC ;";
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			boolean execute = preparedStmt.execute();
			ResultSet resultSet = preparedStmt.getResultSet();

			int i = 1;
			while (resultSet.next()) {
				StudentModel student = new StudentModel();
				GradeModel grade = new GradeModel();
				student.setAdmNo(resultSet.getInt(1));
				student.setStudentName(resultSet.getString(2));
				grade.setTotalMark(resultSet.getInt(3));
				grade.setResultStatus(resultSet.getString(4));
				if (resultSet.getString(4).equalsIgnoreCase("pass")) {
					grade.setRank(i++);
				}

				student.setGrade(grade);
				Map.put(resultSet.getInt(1), student);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return Map;
	}

	public static Map<Integer, SubjectModel> getSubjectAverage() throws ClassNotFoundException {
		HashMap<Integer, SubjectModel> Map = new HashMap<Integer, SubjectModel>();
		String query = " select (sub.sub_id), sub.subjectName,avg(sm.Mark) as Average\r\n"
				+ "  from studentmark as sm\r\n" + "  join Subjects as sub\r\n" + "  on sm.subjectId=sub_id\r\n"
				+ "  group by sub_id;";
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			boolean execute = preparedStmt.execute();
			ResultSet resultSet = preparedStmt.getResultSet();
			while (resultSet.next()) {

				SubjectModel subject = new SubjectModel();
				subject.setName(Subjects.valueOf(resultSet.getString(2)));
				subject.setAverage(resultSet.getFloat(3));
				Map.put(resultSet.getInt(1), subject);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return Map;
	}

	public static List<Map<Integer, StudentModel>> getStudentAboveAverage() throws ClassNotFoundException {
		List<Map<Integer, StudentModel>> studentAverageList = new ArrayList<Map<Integer, StudentModel>>();

		String query1 = "select (sub.sub_id), sub.subjectName,avg(sm.Mark) as Average from studentmark as sm join Subjects as sub on sm.subjectId=sub_id group by sub_id";
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			boolean execute1 = preparedStmt1.execute();
			ResultSet resultSet1 = preparedStmt1.getResultSet();
			while (resultSet1.next()) {

				Integer id = resultSet1.getInt(1);
				String subjectName = resultSet1.getString(2);
				float subjectAverage = resultSet1.getFloat(3);
				HashMap<Integer, StudentModel> Map = new HashMap<Integer, StudentModel>();
				String query = " select Student.ad_no,Student.StudentName ,studentmark.Mark\r\n" + "  from Student \r\n"
						+ "  inner join studentmark on\r\n"
						+ "  Student.ad_no= studentmark.studentId where studentmark.subjectId=" + id + "\r\n"
						+ "  &&  mark> (select avg(studentmark.Mark) as Average from studentmark where studentmark.subjectId="
						+ id + "\r\n" + "  );";

				PreparedStatement preparedStmt = conn.prepareStatement(query);
				boolean execute = preparedStmt.execute();
				ResultSet resultSet = preparedStmt.getResultSet();
				while (resultSet.next()) {
					StudentModel student = new StudentModel();
					List<SubjectModel> templist = new ArrayList<SubjectModel>();
					GradeModel grade = new GradeModel();
					SubjectModel subject = new SubjectModel();
					student.setAdmNo(resultSet.getInt(1));
					student.setStudentName(resultSet.getString(2));
					subject.setAverage(subjectAverage);
					subject.setMark(resultSet.getFloat(3));
					subject.setName(Subjects.valueOf(subjectName));
					templist.add(subject);
					grade.setListOfSubject(templist);
					student.setGrade(grade);
					Map.put(resultSet.getInt(1), student);
				}
				studentAverageList.add(Map);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return studentAverageList;

	}

	public static List<List<String>> getTopScorer() throws ClassNotFoundException {
		List<List<String>> topScorerList = new ArrayList<List<String>>();
		String query1 = "select *from Subjects;";
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			boolean execute1 = preparedStmt1.execute();
			ResultSet resultSet1 = preparedStmt1.getResultSet();
			while (resultSet1.next()) {

				Integer id = resultSet1.getInt(1);
				String subjectName = resultSet1.getString(2);

				String query = " select student.studentName,studentmark.Mark from student inner join studentmark on ad_no= studentmark.studentId  where studentmark.subjectId="
						+ id
						+ "&& mark=(select max(studentmark.Mark) as total from studentmark where studentmark.subjectId="
						+ id + " );";

				PreparedStatement preparedStmt = conn.prepareStatement(query);
				boolean execute = preparedStmt.execute();
				ResultSet resultSet = preparedStmt.getResultSet();

				while (resultSet.next()) {
					List<String> templist = new ArrayList<String>();
					String studName = resultSet.getString(1);
					String studMark = resultSet.getString(2);
					templist.add(subjectName);
					templist.add(studName);
					templist.add(studMark);
					topScorerList.add(templist);
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return topScorerList;
	}

	public static List<Map<Integer, StudentModel>> getAcademicDetails() throws ClassNotFoundException {
		List<Map<Integer, StudentModel>> studentAcademicList = new ArrayList<Map<Integer, StudentModel>>();

		String query1 = "select *from Subjects;";
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			boolean execute1 = preparedStmt1.execute();
			ResultSet resultSet1 = preparedStmt1.getResultSet();
			while (resultSet1.next()) {

				Integer id = resultSet1.getInt(1);
				String subjectName = resultSet1.getString(2);
				HashMap<Integer, StudentModel> Map = new HashMap<Integer, StudentModel>();
				String query = " select student.ad_no,student.studentName,studentmark.Mark\r\n" + "  from student\r\n"
						+ "  inner join studentmark on\r\n"
						+ "  ad_no= studentmark.studentId  where studentmark.subjectId=" + id + ";";

				PreparedStatement preparedStmt = conn.prepareStatement(query);
				boolean execute = preparedStmt.execute();
				ResultSet resultSet = preparedStmt.getResultSet();
				while (resultSet.next()) {
					StudentModel student = new StudentModel();
					List<SubjectModel> templist = new ArrayList<SubjectModel>();
					GradeModel grade = new GradeModel();
					SubjectModel subject = new SubjectModel();
					student.setStudentName(resultSet.getString(2));
					subject.setMark(resultSet.getFloat(3));
					subject.setName(Subjects.valueOf(subjectName));
					templist.add(subject);
					if (resultSet.getFloat(3) < 35) {
						grade.setResultStatus("FAIL");
					} else {
						grade.setResultStatus("PASS");
					}

					grade.setListOfSubject(templist);
					student.setGrade(grade);
					Map.put(resultSet.getInt(1), student);
				}
				studentAcademicList.add(Map);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return studentAcademicList;

	}

	public static Map<Integer, StudentModel> getMark() throws ClassNotFoundException {
		HashMap<Integer, StudentModel> Map = new HashMap<Integer, StudentModel>();

		String query1 = "select ad_no,studentName from Student";
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			boolean execute1 = preparedStmt1.execute();
			ResultSet resultSet1 = preparedStmt1.getResultSet();
			while (resultSet1.next()) {
				StudentModel student = new StudentModel();
				GradeModel grade = new GradeModel();
				List<SubjectModel> templist = new ArrayList<SubjectModel>();
				Integer Studid = resultSet1.getInt(1);
				String StudentName = resultSet1.getString(2);
				student.setAdmNo(Studid);
				student.setStudentName(StudentName);

				String query = " select subjectName,studentmark.Mark\r\n" + "  from studentmark \r\n"
						+ "  inner join Subjects\r\n" + "  on subjectId=sub_id\r\n" + "  where studentId=" + Studid
						+ ";";

				PreparedStatement preparedStmt = conn.prepareStatement(query);
				boolean execute = preparedStmt.execute();
				ResultSet resultSet = preparedStmt.getResultSet();
				grade.setResultStatus("PASS");
				int total = 0;
				while (resultSet.next()) {
					SubjectModel subject = new SubjectModel();
					subject.setName(Subjects.valueOf(resultSet.getString(1)));
					subject.setMark(resultSet.getInt(2));
					templist.add(subject);
					total += resultSet.getInt(2);
					if (resultSet.getInt(2) < 35) {
						grade.setResultStatus("FAIL");

					}
				}
				grade.setListOfSubject(templist);
				grade.setTotalMark(total);
				student.setGrade(grade);
				Map.put(Studid, student);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return Map;

	}

	public static void addstudentdata(StudentModel student) throws ClassNotFoundException, SQLException {
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			String query = "INSERT INTO Student(studentName,gender) values ('" + student.getStudentName() + "','"
					+ student.getGender() + "')";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.executeUpdate();
			String idquery = "select max(ad_no) from Student";
			PreparedStatement preparedStmt3 = conn.prepareStatement(idquery);
			boolean execute = preparedStmt3.execute();
			ResultSet resultSet = preparedStmt3.getResultSet();
			if (resultSet.next()) {
				int studid = resultSet.getInt(1);
				student.setAdmNo(studid);
			}

			String query1 = "INSERT into StudentReport(student_id,total,grade_status)values(" + student.getAdmNo() + ","
					+ student.getGrade().getTotalMark() + ",'" + student.getGrade().getResultStatus() + "');";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);

			preparedStmt1.executeUpdate();
			List<SubjectModel> subList = student.getGrade().getListOfSubject();
			for (SubjectModel subMark : subList) {
				System.out.println(subMark.getMark());
				String query2 = "INSERT into Studentmark(studentId,subjectId,Mark)values(" + student.getAdmNo() + ","
						+ subMark.getSubjectId() + "," + subMark.getMark() + ");";
				PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
				preparedStmt2.executeUpdate();
			}
			System.out.println("Insert Successfully!..");
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static void deleteStudent(int id) throws ClassNotFoundException, SQLException {

		String query = "delete from Student where ad_no=" + id + "";
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			boolean execute = preparedStmt.execute();
			System.out.println("Remove Student from the School!...");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void updateStudent(int studentid, int subjectid, int mark) throws ClassNotFoundException {
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			String query1 = "UPDATE Studentmark SET Mark= '" + mark + "'where subjectId=" + subjectid + "&& studentid="
					+ studentid + "";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.executeUpdate();

			String idquery = "select sum(mark) from StudentMark where studentId=" + studentid + "";
			PreparedStatement preparedStmt2 = conn.prepareStatement(idquery);
			boolean execute = preparedStmt2.execute();
			ResultSet resultSet = preparedStmt2.getResultSet();
			int total = 0;
			if (resultSet.next()) {
				total += resultSet.getInt(1);
			}
			String result = "";
			if (mark < 35) {
				result = "FAIL";
			} else {
				result = "PASS";
			}
			String query = "UPDATE  StudentReport SET total=" + total + ",grade_status='" + result
					+ "' where student_id=" + studentid + "";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.executeUpdate();

			System.out.println("Updated Successfully!..");
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static void addStaff(StaffModel staff) throws ClassNotFoundException {
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			String query = "INSERT INTO Staff(staffName) values ('" + staff.getStaffName() + "')";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.executeUpdate();
			String idquery = "select max(staffid) from Staff";
			PreparedStatement preparedStmt3 = conn.prepareStatement(idquery);
			boolean execute = preparedStmt3.execute();
			ResultSet resultSet = preparedStmt3.getResultSet();
			if (resultSet.next()) {
				int staffid = resultSet.getInt(1);
				staff.setStaffId(staffid);
			}

			String query1 = "INSERT into StaffwithSubject(staff_id,subject_id)values(" + staff.getStaffId() + ","
					+ staff.getStaffSubId() + ");";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.executeUpdate();
			System.out.println("Insert Successfully!..");
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static void deleteStaff(int id) throws ClassNotFoundException {
		String query = "delete from Staff where staffid=" + id + "";
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			boolean execute = preparedStmt.execute();
			System.out.println("Remove Staff from the School!...");

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static Map<Integer, SubjectModel> getPassPercentage() throws ClassNotFoundException {
		Map<Integer,SubjectModel> percentagelist=new HashMap<Integer,SubjectModel>();
		int count=0;
		String countquery="select count(ad_no)\r\n"
				+ "from student;";
		
		
		try {
			Connection conn = DbConnection.getDbConnection().getConnection();
			PreparedStatement preparedStmt2 = conn.prepareStatement(countquery);
			boolean execute2 = preparedStmt2.execute();
			ResultSet resultSet2 = preparedStmt2.getResultSet();
			while (resultSet2.next()) {
				count=resultSet2.getInt(1);
			}
			
			String query = "select sub_id,subjectName\r\n" + "from Subjects;";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			boolean execute = preparedStmt.execute();
			ResultSet resultSet = preparedStmt.getResultSet();
			while (resultSet.next()) {

				SubjectModel subject = new SubjectModel();
				subject.setName(Subjects.valueOf(resultSet.getString(2)));
				subject.setSubjectId(resultSet.getInt(1));
				String query1 = "select Sum(mark)\r\n"
						+ "from StudentMark \r\n"
						+ "where subjectId="+resultSet.getInt(1)+" ;";

				PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
				boolean execute1 = preparedStmt1.execute();
				ResultSet resultSet1 = preparedStmt1.getResultSet();
				int scoredMark=0;
				while (resultSet1.next()) {
					 scoredMark=resultSet1.getInt(1);
					float percentage=scoredMark/count;
					subject.setPercentage(percentage);
				}

				
				
				
				percentagelist.put(resultSet.getInt(1), subject);
			}
		} catch (SQLException e) {

			System.out.println("Something went wrong!..");
		}
		return percentagelist;
	}
	
}
