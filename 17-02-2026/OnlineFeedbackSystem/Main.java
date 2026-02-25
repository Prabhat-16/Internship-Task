import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        FeedbackDAO dao = new FeedbackDAO();

        while (true) {
            System.out.println("\n===== ONLINE FEEDBACK SYSTEM =====");
            System.out.println("1. Add Feedback");
            System.out.println("2. View All Feedback");
            System.out.println("3. Delete Feedback");
            System.out.println("4. Exit");
            System.out.print("Choose Option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Rating (1-5): ");
                    int rating = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Comments: ");
                    String comments = sc.nextLine();

                    Feedback feedback = new Feedback(name, email, rating, comments);
                    dao.insertFeedback(feedback);
                    break;

                case 2:
                    dao.viewAllFeedback();
                    break;

                case 3:
                    System.out.print("Enter ID to delete: ");
                    int id = sc.nextInt();
                    dao.deleteFeedback(id);
                    break;

                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}