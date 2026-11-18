
import java.nio.file.Path;
import java.util.Scanner;

public class RegistrationClass {

    private static String userName;
    private static String password;
    private static String cellPhoneNumber;
    private static String firstName;
    private static String lastName;

    public static void setFirstName(String fname) {
        firstName = fname;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setUserName(String username) {
        userName = username;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setPassword(String passw0rd) {
        password = passw0rd;
    }

    public static String getPassword() {
        return password;
    }

    public static void setLastname(String lname) {
        lastName = lname;
    }

    public static String getLastname() {
        return lastName;
    }

    public static void setCellNumber(String cellphonenumber) {
        cellPhoneNumber = cellphonenumber;
    }

    public static String getCellNumber() {
        return cellPhoneNumber;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("***** User Registration *****");

        System.out.print("Please enter your first name: ");
        firstName = input.nextLine().trim();

        while (firstName.isEmpty()) {
            System.out.println("First name cannot be empty.");
            System.out.print("Please enter your first name: ");
            firstName = input.nextLine().trim();
        }

        System.out.print("Please enter your last name: ");
        lastName = input.nextLine().trim();

        while (lastName.isEmpty()) {
            System.out.println("Last name cannot be empty.");
            System.out.print("Please enter your last name: ");
            lastName = input.nextLine().trim();
        }

        LoginUser login = new LoginUser(firstName, lastName);

        System.out.print(
            "Enter your username (5 characters or less and must contain an underscore): "
        );
        userName = input.nextLine();

        while (!login.checkUserName(userName)) {
            System.out.println("Username is not correctly formatted.");
            System.out.println(
                "Ensure your username contains an underscore " +
                "and is no more than five characters long."
            );
            System.out.print("Enter your username: ");
            userName = input.nextLine();
        }

        System.out.print(
            "Create a password (at least 8 characters, " +
            "1 capital letter, 1 number, 1 special character): "
        );
        password = input.nextLine();

        while (!login.checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted.");
            System.out.print("Create a password: ");
            password = input.nextLine();
        }

        System.out.print(
            "Please enter cellphone number with international country code: "
        );
        cellPhoneNumber = input.nextLine();

        while (!login.checkCellPhoneNumber(cellPhoneNumber)) {
            System.out.println(
                "Cell phone number incorrectly formatted " +
                "or does not contain international code."
            );
            System.out.print(
                "Please enter cellphone number with international country code: "
            );
            cellPhoneNumber = input.nextLine();
        }

        String regStatus = login.registerUser(
            userName,
            password,
            cellPhoneNumber,
            firstName,
            lastName
        );

        System.out.println(regStatus);

        if (regStatus.equals("Registration successful.")) {

            boolean loginSuccessful = false;

            while (!loginSuccessful) {

                System.out.println("\n****** User Login ******");

                System.out.print("Enter the username you created: ");
                String loginUsername = input.nextLine();

                System.out.print("Enter the password you created: ");
                String loginPassword = input.nextLine();

                String loginStatus = login.returnLoginStatus(
                    loginUsername,
                    loginPassword
                );

                System.out.println(loginStatus);

                if (login.loginUser(loginUsername, loginPassword)) {
                    loginSuccessful = true;
                } else {
                    System.out.println("Login failed, please try again.");
                }
            }

            // QuickChat starts after successful login.
            Messages messages = new Messages(Path.of("messages.json"), cellPhoneNumber);
            messages.startQuickChat(input);
        }

        input.close();
    }
}
