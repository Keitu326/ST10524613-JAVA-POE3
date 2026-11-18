
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class LoginTest {

    private LoginUser login;
    private Messages messages;

    public LoginTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        login = new LoginUser("Kyle", "Smith");
        messages = new Messages();
    }

    @After
    public void tearDown() {
    }

    // =========================
    // PART 1: LOGIN USER TESTS
    // =========================

    @Test
    public void testCheckUserName() {

        assertTrue(
                "Valid username should return true",
                login.checkUserName("kyl_1")
        );

        assertFalse(
                "Username without underscore should be invalid",
                login.checkUserName("kyle1")
        );

        assertFalse(
                "Username longer than five characters should be invalid",
                login.checkUserName("kyle_123")
        );

        assertFalse(
                "Null username should be invalid",
                login.checkUserName(null)
        );
    }

    @Test
    public void testCheckPasswordComplexity() {

        assertTrue(
                "Valid password should return true",
                login.checkPasswordComplexity("Ch&&sec@ke99!")
        );

        assertFalse(
                "Password without required complexity should be invalid",
                login.checkPasswordComplexity("password")
        );

        assertFalse(
                "Password shorter than eight characters should be invalid",
                login.checkPasswordComplexity("Ab1!")
        );

        assertFalse(
                "Null password should be invalid",
                login.checkPasswordComplexity(null)
        );
    }

    @Test
    public void testCheckCellPhoneNumber() {

        assertTrue(
                "Valid international phone should return true",
                login.checkCellPhoneNumber("+27838968976")
        );

        assertFalse(
                "Local number without international code should be invalid",
                login.checkCellPhoneNumber("08966553")
        );

        assertFalse(
                "Phone number without plus sign should be invalid",
                login.checkCellPhoneNumber("27838968976")
        );

        assertFalse(
                "Null phone number should be invalid",
                login.checkCellPhoneNumber(null)
        );
    }

    @Test
    public void testRegisterUser() {

        String success = login.registerUser(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals("Registration successful.", success);

        String invalidUser = login.registerUser(
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(
                "Username is not correctly formatted, please ensure "
                + "that your username contains an underscore and is "
                + "no more than five characters in length.",
                invalidUser
        );

        String invalidPass = login.registerUser(
                "kyl_1",
                "password",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(
                "Password is not correctly formatted; please ensure "
                + "that the password contains at least eight characters, "
                + "a capital letter, a number, and a special character.",
                invalidPass
        );

        String invalidCell = login.registerUser(
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553",
                "Kyle",
                "Smith"
        );

        assertEquals(
                "Cell phone number incorrectly formatted "
                + "or does not contain international code.",
                invalidCell
        );
    }

    @Test
    public void testLoginUser() {

        login.registerUser(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertTrue(
                "Correct credentials should return true",
                login.loginUser("kyl_1", "Ch&&sec@ke99!")
        );

        assertFalse(
                "Incorrect credentials should return false",
                login.loginUser("wrong", "wrongpass")
        );
    }

    @Test
    public void testReturnLoginStatus() {

        login.registerUser(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertEquals(
                "Welcome Kyle Smith, it is great to see you again.",
                login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!")
        );

        assertEquals(
                "Username or password incorrect, please try again.",
                login.returnLoginStatus("wrong", "wrongpass")
        );
    }

    // =========================
    // PART 2: MESSAGES TESTS
    // =========================

    // Test message ID validation.
    @Test
    public void testCheckMessageID() {

        assertTrue(
                "A 10-digit message ID should be valid",
                messages.checkMessageID("1234567890")
        );

        assertFalse(
                "An ID shorter than 10 digits should be invalid",
                messages.checkMessageID("12345")
        );

        assertFalse(
                "An ID containing letters should be invalid",
                messages.checkMessageID("123456789A")
        );

        assertFalse(
                "A null message ID should be invalid",
                messages.checkMessageID(null)
        );
    }

    // Test recipient cellphone validation.
    @Test
    public void testCheckRecipientCell() {

        assertTrue(
                "Valid international recipient number should pass",
                messages.checkRecipientCell("+27838968976")
        );

        assertFalse(
                "Local recipient number should fail",
                messages.checkRecipientCell("0838968976")
        );

        assertFalse(
                "Number without plus sign should fail",
                messages.checkRecipientCell("27838968976")
        );

        assertFalse(
                "Null recipient number should fail",
                messages.checkRecipientCell(null)
        );
    }

    // Test message hash generation.
    @Test
    public void testCreateMessageHash() {

        String hash = messages.createMessageHash(
                "1234567890",
                1,
                "Hi Mike, how are you?"
        );

        assertEquals(
                "12:1:HIYOU?",
                hash
        );
    }

    // Test that sent messages are recorded.
    @Test
    public void testSentMessage() {

        Messages.ChatMessage message = new Messages.ChatMessage(
                "1234567890",
                "+27838968976",
                "Hello there",
                "12:1:HELLOTHERE",
                1
        );

        messages.SentMessage(message);

        assertEquals(
                "One message should be recorded as sent",
                1,
                messages.returnTotalMessages()
        );
    }

    // Test the total message count.
    @Test
    public void testReturnTotalMessages() {

        assertEquals(
                "Initially there should be no sent messages",
                0,
                messages.returnTotalMessages()
        );

        Messages.ChatMessage firstMessage = new Messages.ChatMessage(
                "1234567890",
                "+27838968976",
                "Hello",
                "12:1:HELLOHELLO",
                1
        );

        Messages.ChatMessage secondMessage = new Messages.ChatMessage(
                "1234567891",
                "+27838968976",
                "How are you?",
                "12:2:HOWYOU?",
                2
        );

        messages.SentMessage(firstMessage);
        messages.SentMessage(secondMessage);

        assertEquals(
                "Two sent messages should be counted",
                2,
                messages.returnTotalMessages()
        );
    }

    // Test storing a message in JSON format.
    @Test
    public void testStoreMessage() throws Exception {

        File jsonFile = new File("messages.json");
        boolean fileExistedBefore = jsonFile.exists();
        byte[] previousContent = null;

        if (fileExistedBefore) {
            previousContent = Files.readAllBytes(jsonFile.toPath());
        }

        try {
            Messages.ChatMessage message = new Messages.ChatMessage(
                    "1234567890",
                    "+27838968976",
                    "Hello from JUnit",
                    "12:1:HELLOJUNIT",
                    1
            );

            messages.storeMessage(message);

            assertTrue(
                    "Storing a message should create messages.json",
                    jsonFile.exists()
            );

            String json = new String(
                    Files.readAllBytes(jsonFile.toPath()),
                    StandardCharsets.UTF_8
            );

            assertTrue(
                    "JSON should contain the message ID",
                    json.contains("\"messageID\": \"1234567890\"")
            );

            assertTrue(
                    "JSON should contain the recipient",
                    json.contains("\"recipient\": \"+27838968976\"")
            );

            assertTrue(
                    "JSON should contain the message text",
                    json.contains("Hello from JUnit")
            );

            assertTrue(
                    "JSON should contain the message hash",
                    json.contains("\"messageHash\": \"12:1:HELLOJUNIT\"")
            );

        } finally {
            // Restore any JSON file that existed before this test.
            if (fileExistedBefore) {
                Files.write(jsonFile.toPath(), previousContent);
            } else {
                Files.deleteIfExists(jsonFile.toPath());
            }
        }
    }

    // Test JSON escaping for special characters.
    @Test
    public void testStoreMessageWithSpecialCharacters() throws Exception {

        File jsonFile = new File("messages.json");
        boolean fileExistedBefore = jsonFile.exists();
        byte[] previousContent = null;

        if (fileExistedBefore) {
            previousContent = Files.readAllBytes(jsonFile.toPath());
        }

        try {
            Messages.ChatMessage message = new Messages.ChatMessage(
                    "1234567890",
                    "+27838968976",
                    "He said \"Hello\" and used a backslash \\",
                    "12:1:TEST",
                    1
            );

            messages.storeMessage(message);

            String json = new String(
                    Files.readAllBytes(jsonFile.toPath()),
                    StandardCharsets.UTF_8
            );

            assertTrue(
                    "Quotation marks should be escaped in JSON",
                    json.contains("\\\"Hello\\\"")
            );

            assertTrue(
                    "Backslashes should be escaped in JSON",
                    json.contains("\\\\")
            );

        } finally {
            if (fileExistedBefore) {
                Files.write(jsonFile.toPath(), previousContent);
            } else {
                Files.deleteIfExists(jsonFile.toPath());
            }
        }
    }
}
