package SchoolManagement;

import SchoolManagement.SubjectModel.Subjects;

public class StaffModel {
	private int staffId;
	private String staffName;
	private int staffSubId;
	private Subjects name;

	public int getStaffSubId() {
		return staffSubId;
	}

	public void setStaffSubId(int staffSubId) {
		this.staffSubId = staffSubId;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Subjects getName() {
		return name;
	}

	public void setName(Subjects name) {
		this.name = name;
	}

}
