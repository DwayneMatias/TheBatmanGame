package checklink;
import java.util.Scanner;

public class checker {

    public static void main(String[] args) {
        String[] words = {"Login", "Verify", "Account", "Security", "Update", "Win", "Prize"};
        String[] companies = {"https://www.instagram.com/accounts/login/", "https://www.facebook.com/login/", "https://www.netflix.com/login", "https://www.netflix.com/signup", "https://www.amazon.com/ap/signin", "https://myaccount.google.com" };

        // Ask the user for input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a link to be checked: ");
        String input = scanner.nextLine();
        scanner.close(); // Close the scanner after use

        boolean containsWord = false;
        boolean containsCompany = false;

        for (String word : words) {
            if (input.toLowerCase().contains(word.toLowerCase())) {
                containsWord = true;
                break;
            }
        }

        for (String company : companies) {
            if (input.equals(company)) {
                containsCompany = true;
                break;
            }
        }

        if (containsWord || containsCompany) {
            System.out.println("This might be a phishing scam, please be careful.");
        } else {
            System.out.println("This link seems to be safe, but please be careful regardless.");
        }
    }
}
