public class Main {
    public static void main(String[] args) {

        StudentDAO.addStudent("PRN101", "Rahul", "MCA C Sci", 9.7);
        StudentDAO.addStudent("PRN102", "Aman", "BCA", 8.5);

        System.out.println("\nAll Students:");
        StudentDAO.viewStudents();

        System.out.println("\nMCA C Sci Students:");
        StudentDAO.listMCAStudents();

        System.out.println("\nHigh Scorers:");
        StudentDAO.highScorers();

        StudentDAO.updateStudent("PRN101", "Rahul Sharma");

        StudentDAO.deleteStudent("PRN102");
    }
}
