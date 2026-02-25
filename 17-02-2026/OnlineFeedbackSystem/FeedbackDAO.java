import java.sql.*;
import java.util.Scanner;

public class FeedbackDAO {

    public void insertFeedback(Feedback feedback) {
        String query = "INSERT INTO feedback(name,email,rating,comments) VALUES(?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, feedback.getName());
            ps.setString(2, feedback.getEmail());
            ps.setInt(3, feedback.getRating());
            ps.setString(4, feedback.getComments());

            ps.executeUpdate();
            System.out.println("✅ Feedback Submitted Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllFeedback() {
        String query = "SELECT * FROM feedback";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Rating: " + rs.getInt("rating"));
                System.out.println("Comments: " + rs.getString("comments"));
                System.out.println("---------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFeedback(int id) {
        String query = "DELETE FROM feedback WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("🗑 Feedback Deleted!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}