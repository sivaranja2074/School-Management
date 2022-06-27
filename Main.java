package SchoolManagement;

import java.sql.SQLException;
import java.util.*;

import SchoolManagement.GradeModel.Result;
import SchoolManagement.SubjectModel.Subjects;

public class Main {

	public static String choosesub(int subid) {
		if (subid == 1) {
			return "TAMIL";
		} else if (subid == 2) {
			return "ENGLISH";
		} else if (subid == 3) {
			return "MATHS";
		} else if (subid == 4) {
			return "SCIENCE";
		} else if (subid == 5) {
			return "SOCIAL";
		} else
			return "Invalid Subject id";

	}
	

	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		System.out.print("\n~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
		System.out.print(" |SCHOOL MANAGEMENT|");
		System.out.print("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
		Scanner scanner = new Scanner(System.in);
		View view = new View();
		boolean bool1 = true;
		while (bool1) {
			try {
				System.out.println("\n1.Management \n2.Staff \n3.Student \n4.Exit");
				int choice = Integer.parseInt(scanner.nextLine());
				boolean bool2 = true;
				switch (choice) {
				case 1: {
					while (bool2) {
						try {
							System.out.println("\n1.StaffDetails \n2.StudentDetails \n3.Exit");
							int choose = Integer.parseInt(scanner.nextLine());
							boolean bool3 = true;
							switch (choose) {
							case 1: {

								while (bool3) {
									try {
										System.out.println("1.SAVE 2.DELETE 3.PRINT STAFFDETAILS 4.exit");
										int option = Integer.parseInt(scanner.nextLine());
										switch (option) {
										case 1: {

											StaffModel staff = new StaffModel();
											System.out.println("Enter the StaffName : ");
											staff.setStaffName(scanner.next());
											System.out.println("Choose the Subject : ");
											for (Map.Entry<Integer, SubjectModel> sublist : DbConnection
													.getSubjectAverage().entrySet()) {
												SubjectModel sub = sublist.getValue();
												System.out.println("" + sublist.getKey() + "." + sub.getName() + "");
											}
											staff.setStaffSubId(scanner.nextInt());
											staff.setName(Subjects.valueOf(choosesub(staff.getStaffSubId())));
											DbConnection.addStaff(staff);
											break;
										}
										case 2: {
											
											try {
												System.out.println("Enter the Staff Id");
												int id = scanner.nextInt();
												DbConnection.deleteStaff(id);
												
											} catch (InputMismatchException e) {
												System.out.println("invalid input!Please enter the corret Id");
											}
											break;
											
										}
										case 3: {
											view.displayStaff(DbConnection.getAllStaff());
											break;
										}
										case 4: {
											bool3 = false;
											break;
										}
										

										}

									} catch (IllegalArgumentException e) {
										System.out.println(e);

									}
								}
								break;
							}

							case 2: {
								bool3 = true;

								while (bool3) {
									try {
										StudentModel student = new StudentModel();
										GradeModel grade = new GradeModel();

										List<SubjectModel> templist = new ArrayList<SubjectModel>();
										System.out.println("1.SAVE 2.UPDATE 3.DELETE 4.PRINT STUDENTDETAILS 5.exit");
										int option = Integer.parseInt(scanner.nextLine());
										switch (option) {
										case 1: {

											System.out.println("Enter the StudentName : ");
											student.setStudentName(scanner.next());
											System.out.println("Enter the Student Gender : ");
											student.setGender(scanner.next());
											int total = 0;
											grade.setResultStatus("PASS");
											for (Map.Entry<Integer, SubjectModel> sublist : DbConnection
													.getSubjectAverage().entrySet()) {
												SubjectModel subject = new SubjectModel();
												SubjectModel sub = sublist.getValue();
												System.out.println("Enter the " + sub.getName() + " Mark : ");
												subject.setSubjectId(sublist.getKey());
												subject.setName(sub.getName());
												int mark = scanner.nextInt();
												subject.setMark(mark);
												if (mark < 35) {
													grade.setResultStatus("FAIL");

												}
												templist.add(subject);
												total += mark;
											}
											grade.setListOfSubject(templist);
											grade.setTotalMark(total);
											student.setGrade(grade);
											DbConnection.addstudentdata(student);
											break;

										}
										case 2: {

											
											System.out.println("Enter the Student id : ");
											int studentid=scanner.nextInt();
											for (Map.Entry<Integer, SubjectModel> sublist : DbConnection
													.getSubjectAverage().entrySet()) {
												SubjectModel sub = sublist.getValue();												
												System.out.println("" + sublist.getKey() + "." + sub.getName() + "");
											}
											System.out.println("Enter the Subject id :");
											int subjectid=scanner.nextInt();
											System.out.println("Enter the Mark : ");
											int mark=scanner.nextInt();
											DbConnection.updateStudent(studentid,subjectid,mark);
											break;
										}
										case 3: {
											try {
												System.out.println("Enter the Student Id");
												int id = scanner.nextInt();
												DbConnection.deleteStudent(id);
												;
											} catch (InputMismatchException e) {
												System.out.println("invalid input!Please enter the corret Id");
											}
											break;
										}
										case 4: {
									
											view.displayStudent(DbConnection.getAllStudent());
											break;
										}
										case 5: {
											bool3 = false;
											break;
										}

										}

									} catch (IllegalArgumentException e) {
										System.out.println(e);

									}
								}
								break;
							}
							case 3: {
								bool2 = false;
								break;
							}
							}
						} catch (IllegalArgumentException e) {
							System.out.println("Something went wrong! ");

						}
					}

					break;

				}

				case 2: {
					bool2 = true;
					while (bool2) {
						try {
							System.out.println(
									"\n1.Display Rank \n2.Subject Average \n3.AboveAverageStudent \n4.TopScorer \n5.AcademicDetails \n6.Count \n7.PassPercentage of Each Subject \n8.Exit");
							int option1 = Integer.parseInt(scanner.nextLine());
							switch (option1) {
							case 1: {
								view.displayRank(DbConnection.getRank());
								break;
							}
							case 2: {
								view.displaySubjectAverage(DbConnection.getSubjectAverage());
								break;
							}
							case 3: {
								view.displayStudentAboveAverage(DbConnection.getStudentAboveAverage());
								break;
							}
							case 4: {
								view.displayTopScorer(DbConnection.getTopScorer());
								break;
							}
							case 5: {
								view.displayStudentAcademicDetails(DbConnection.getAcademicDetails());
								break;
							}
							case 6: {
								view.displaySubjectcountAboveAverage(DbConnection.getMark(),
										DbConnection.getSubjectAverage());
								break;
							}
							case 7:
							{
								view.displayPassPercentageOfeachSubject(DbConnection.getPassPercentage());
								break;
							}
							case 8: {
								bool2 = false;
								break;
							}
							}

						} catch (IllegalArgumentException e) {
							System.out.println(e);
//							e.printStackTrace();

						}
					}
					break;
				}
				case 3: {

					try {
						System.out.println("Enter the Student Id");
						int id = scanner.nextInt();
						view.diplayGivenStudnet(id, DbConnection.getMark());
					} catch (InputMismatchException e) {
						System.out.println("invalid input!Please enter the corret Id");
					}

					break;
				}

				case 4: {
					bool1 = false;
					break;
				}

				}

			} catch (IllegalArgumentException e) {
				System.out.println(e);
				

			}
		}

	}

}
