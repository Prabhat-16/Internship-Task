import java.sql.*;

public class StudentDAO {

    // Insert Student (Avoid Duplicate PRN)
    public static void addStudent(String prn, String name, String course, double marks) {
        try (Connection con = DBConnection.getConnection()) {

            String checkQuery = "SELECT * FROM students WHERE prn=?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setString(1, prn);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("PRN already exists!");
                return;
            }

            String query = "INSERT INTO students (prn, name, course, cet_marks) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, prn);
            pst.setString(2, name);
            pst.setString(3, course);
            pst.setDouble(4, marks);

            pst.executeUpdate();
            System.out.println("Student added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update Student
    public static void updateStudent(String prn, String newName) {
        try (Connection con = DBConnection.getConnection()) {

            String query = "UPDATE students SET name=? WHERE prn=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, newName);
            pst.setString(2, prn);

            int rows = pst.executeUpdate();
            if (rows > 0)
                System.out.println("Student updated!");
            else
                System.out.println("Student not found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Student
    public static void deleteStudent(String prn) {
        try (Connection con = DBConnection.getConnection()) {

            String query = "DELETE FROM students WHERE prn=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, prn);

            int rows = pst.executeUpdate();
            if (rows > 0)
                System.out.println("Student deleted!");
            else
                System.out.println("Student not found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Retrieve All Students
    public static void viewStudents() {
        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM students";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(
                    rs.getString("prn") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("course") + " | " +
                    rs.getDouble("cet_marks")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // List MCA C Sci Students
    public static void listMCAStudents() {
        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM students WHERE course='MCA C Sci'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Students with 9.5+ CET Marks
    public static void highScorers() {
        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM students WHERE cet_marks >= 9.5";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getDouble("cet_marks"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
