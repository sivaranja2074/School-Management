package SchoolManagement;

public class SubjectModel {

	private int SubjectId;
	private Subjects name;
	private float mark;
	private float average;
	private float percentage;

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public int getSubjectId() {
		return SubjectId;
	}

	public void setSubjectId(int subjectId) {
		SubjectId = subjectId;
	}

	public Subjects getName() {
		return name;
	}

	public void setName(Subjects name) {
		this.name = name;
	}

	public float getMark() {
		return mark;
	}

	public void setMark(float Mark) {
		mark = Mark;
	}

	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

	enum Subjects {
		TAMIL, ENGLISH, MATHS, SCIENCE, SOCIAL;
	}

}
