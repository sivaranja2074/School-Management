package SchoolManagement;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class View {
	public void displayStudent(Map<Integer, StudentModel> student) {
		System.out.println("StudentId\tStudentName\tGender");
		System.out.println("------------------------------------------------------------");

		for (Integer i : student.keySet()) {
			StudentModel s = student.get(i);
			System.out.println("\t" + s.getAdmNo() + "\t" + s.getStudentName() + "\t\t" + s.getGender());
		}
		System.out.println("------------------------------------------------------------");
	}

	public void displayStaff(Map<Integer, StaffModel> staff) {
		System.out.println("StudentId\tStudentName\tKnownSubject");
		System.out.println("------------------------------------------------------------");

		for (Integer i : staff.keySet()) {
			StaffModel s = staff.get(i);
			System.out.println("\t" + s.getStaffId() + "\t" + s.getStaffName() + "\t\t" + s.getName());
		}
		System.out.println("------------------------------------------------------------");
	}

	public void displayRank(Map<Integer, StudentModel> student) {
		System.out.println("StudentId\tStudentName\tTotal\tRank\tResult");
		System.out.println("------------------------------------------------------------");

		for (Integer i : student.keySet()) {
			StudentModel s = student.get(i);
			System.out.println("\t" + s.getAdmNo() + "\t" + s.getStudentName() + "\t\t" + s.getGrade().getTotalMark()
					+ "\t" + s.getGrade().getRank() + "\t" + s.getGrade().getResultStatus());
		}
		System.out.println("------------------------------------------------------------");
	}

	public void displaySubjectAverage(Map<Integer, SubjectModel> subject) {
		System.out.println("SubjectId\tSubjectName\tAverage");
		System.out.println("------------------------------------------------------------");

		for (Integer i : subject.keySet()) {
			SubjectModel s = subject.get(i);
			System.out.println("\t" + i + "\t" + s.getName() + "\t\t" + s.getAverage());
		}
		System.out.println("------------------------------------------------------------");
	}

	public void displayStudentAboveAverage(List<Map<Integer, StudentModel>> studentAverageList) {

		for (Map<Integer, StudentModel> collection : studentAverageList) {
			Set<Integer> set = collection.keySet();
			for (int i : set) {
				StudentModel stud = collection.get(i);
				System.out.println("Subject Name: " + stud.getGrade().getListOfSubject().get(0).getName());
				System.out.println("Subject Average: " + stud.getGrade().getListOfSubject().get(0).getAverage());
				System.out.println();
				System.out.println("StudentId\tStudentName\tMark");
				System.out.println("------------------------------------------------------------");
				break;
			}
			for (int i : set) {

				StudentModel s = collection.get(i);
				System.out.println("\t" + s.getAdmNo() + "\t" + s.getStudentName() + "\t\t"
						+ s.getGrade().getListOfSubject().get(0).getMark());

			}
			System.out.println("------------------------------------------------------------");
			System.out.println();
		}

	}

	public void displayTopScorer(List<List<String>> refList) {

		System.out.println("SubjectName\tStudentName\tMark");
		System.out.println("------------------------------------------------------------");
		for (List<String> collection : refList) {
			String out = "";
			for (String scorerList : collection) {
				out += "\t" + scorerList;
			}
			System.out.println(out);
		}
	}

	public void displayStudentAcademicDetails(List<Map<Integer, StudentModel>> studentAcademicList) {

		for (Map<Integer, StudentModel> collection : studentAcademicList) {
			Set<Integer> set = collection.keySet();
			for (int i : set) {
				StudentModel stud = collection.get(i);
				System.out.println("Subject Name: " + stud.getGrade().getListOfSubject().get(0).getName());
				System.out.println();
				System.out.println("StudentName\tMark\tResult");
				System.out.println("------------------------------------------------------------");
				break;
			}
			for (int i : set) {

				StudentModel s = collection.get(i);
				System.out.println(s.getStudentName() + "\t\t" + s.getGrade().getListOfSubject().get(0).getMark() + "\t"
						+ s.getGrade().getResultStatus());

			}

			System.out.println("------------------------------------------------------------");
			System.out.println();
		}

	}

	public void displaySubjectcountAboveAverage(Map<Integer, StudentModel> student,
			Map<Integer, SubjectModel> subject) {
		for (Map.Entry<Integer, StudentModel> studCollection : student.entrySet()) {
			int count = 0;
			String subjects = "";
			StudentModel s = studCollection.getValue();

			for (Map.Entry<Integer, SubjectModel> subCollection : subject.entrySet()) {

				SubjectModel tempsub = subCollection.getValue();
				SubjectModel studsub = getSubject(studCollection.getValue().getGrade().getListOfSubject(), tempsub);
				if (studsub.getMark() > tempsub.getAverage()) {
					subjects += tempsub.getName() + " | ";
					count++;
				}
			}
			System.out.println("Student Name :" + s.getStudentName());
			System.out.println("Subjects:" + subjects);
			System.out.println("Count:" + count);
			System.out.println("---------------------------------------------------------");
		}

	}

	private SubjectModel getSubject(List<SubjectModel> subList, SubjectModel averagesub) {
		SubjectModel temp = null;
		for (SubjectModel subject : subList) {
			if (subject.getName().equals(averagesub.getName())) {
				temp = subject;
				break;
			}
		}
		return temp;
	}

	{

	}

	public void diplayGivenStudnet(int id, Map<Integer, StudentModel> student) {

		if (!student.containsKey(id)) {
			System.out.println("Student Id is Not Found");
		} else {
			StudentModel s = student.get(id);
			System.out.println("StudentId : " + s.getAdmNo());
			System.out.println("StudentName : " + s.getStudentName());
			System.out.println();
			System.out.println("Subject\t\t Mark\tResult");
			System.out.println("----------------------------------");
			List<SubjectModel> subList = s.getGrade().getListOfSubject();
			for (SubjectModel subMark : subList) {
				System.out.println(subMark.getName() + "\t\t" + subMark.getMark());
			}
			System.out.println("----------------------------------");
			System.out.println("\t\t" + s.getGrade().getTotalMark() + "\t" + s.getGrade().getResultStatus());
			System.out.println("----------------------------------");

		}

	}

	public void displayPassPercentageOfeachSubject(Map<Integer, SubjectModel> subject) {
		System.out.println("SubjectId\tSubjectName\tPassPercentage");
		System.out.println("-----------------------------------------------------");

		for (Integer i : subject.keySet()) {
			SubjectModel s = subject.get(i);
			System.out.println("\t" + s.getSubjectId() + "\t" + s.getName() + "\t\t" + s.getPercentage() + "%");
		}
		System.out.println("-----------------------------------------------------");
	}

}
