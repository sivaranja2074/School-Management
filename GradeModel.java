package SchoolManagement;

import java.util.ArrayList;
import java.util.List;

public class GradeModel {
	private int totalMark;
	private int rank;
	private String resultStatus;
	private List<SubjectModel> listOfSubject;

	public GradeModel() {

		this.listOfSubject = new ArrayList();
		;
	}

	public void setListOfSubject(List<SubjectModel> listOfSubject) {
		this.listOfSubject = listOfSubject;
	}

	public List<SubjectModel> getListOfSubject() {
		return listOfSubject;
	}

	public int getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(int totalMark) {
		this.totalMark = totalMark;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String string) {
		this.resultStatus = string;
	}

	enum Result {
		PASS, FAIL;
	}
}
