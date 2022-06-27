package SchoolManagement;

public class StudentModel {
	private int admNo;
	private String studentName;
	private String gender;
	private GradeModel grade;

	public void setAdmNo(int admNo) {
		this.admNo = admNo;
	}

	public int getAdmNo() {
		return admNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public GradeModel getGrade() {
		return grade;
	}

	public void setGrade(GradeModel grade) {
		this.grade = grade;
	}

	enum Gender {
		MALE, FEMALE;
	}

}
