
public class LoginUser {

    private String firstName;
    private String lastName;

    private String storedUsername;
    private String storedPassword;

    public LoginUser(String fname, String lname) {
        this.firstName = fname;
        this.lastName = lname;
    }

    // Username must contain an underscore and be no longer than 5 characters.
    public boolean checkUserName(String username) {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    // Password must have 8 characters, a capital letter,
    // a number and a special character.
    public boolean checkPasswordComplexity(String password) {

        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasNumber = false;
        boolean hasCap = false;
        boolean hasChar = false;

        for (int i = 0; i < password.length(); i++) {

            char current = password.charAt(i);

            if (Character.isUpperCase(current)) {
                hasCap = true;
            }

            if (Character.isDigit(current)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(current)) {
                hasChar = true;
            }
        }

        return hasNumber && hasCap && hasChar;
    }

    // International cellphone number validation.
    public boolean checkCellPhoneNumber(String number) {

        if (number == null) {
            return false;
        }

        String regex = "^\\+\\d{11,13}$";

        return number.matches(regex);
    }

    // Register the user after validating all details.
    public String registerUser(
            String username,
            String password,
            String cellNumber,
            String fName,
            String lName) {

        this.firstName = fName;
        this.lastName = lName;

        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure "
                    + "that your username contains an underscore and is "
                    + "no more than five characters in length.";
        }

        System.out.println("Username successfully captured.");

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure "
                    + "that the password contains at least eight characters, "
                    + "a capital letter, a number, and a special character.";
        }

        System.out.println("Password successfully captured.");

        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number incorrectly formatted "
                    + "or does not contain international code.";
        }

        System.out.println("Cellphone number successfully added.");

        this.storedUsername = username;
        this.storedPassword = password;

        return "Registration successful.";
    }

    // Compare entered login details against registered details.
    public boolean loginUser(String username, String password) {

        return username != null
                && password != null
                && username.equals(this.storedUsername)
                && password.equals(this.storedPassword);
    }

    // Return a readable login status.
    public String returnLoginStatus(String username, String password) {

        if (loginUser(username, password)) {
            return "Welcome " + this.firstName + " "
                    + this.lastName
                    + ", it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}
