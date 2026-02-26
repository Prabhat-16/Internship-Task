public class NumberPattern {

    public static void main(String[] args) {

        int n = 5;

        for (int i = n; i >= 1; i--) {

            for (int j = 1; j <= i; j++) {
                System.out.print(j);
            }

            int spaces = 2 * (n - i);
            for (int k = 1; k <= spaces; k++) {
                System.out.print(" ");
            }

             // Starting spaces for right alignment
            // for (int s = 1; s <= (n - i); s++) {
            //     System.out.print("  ");
            // }

              // Left side numbers
            // for (int j = 1; j <= i; j++) {
            //     System.out.print(j);
            // }

            for (int j = i; j >= 1; j--) {
                System.out.print(j);
            }

            

            System.out.println();
        }
    }
}
