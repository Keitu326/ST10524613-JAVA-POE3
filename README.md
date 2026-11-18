# Keitumetse QuickChat Part 3

This Java with Ant project continues the supplied Keitumetse Parts 1 and 2 project for PROG5121. Registration and login lead into the QuickChat console.

## Build and run

Use JDK 17 or newer and Apache Ant. From the project folder, run these commands.

```sh
ant clean test jar
java -jar dist/KeitumetsePart3.jar
```

Open this folder as an existing Java with Ant project in NetBeans. The first build downloads Gson, JUnit, and Hamcrest. Keep the generated `dist/lib` folder beside `KeitumetsePart3.jar` when moving the application.

## QuickChat

Use **1. Send Messages** to create messages and choose whether to send, disregard, or store them. **2. Show Recently Sent Messages** prints the current session's sent report. Sending is simulated in the console.

Use **4. Stored Messages** to view senders and recipients, find the longest saved message, search by ID or recipient, delete a saved message by hash, and print a full stored report. The submenu also searches across sent and stored messages for a recipient.

Stored messages are saved to `messages.json` in the working directory and reloaded at startup. An existing Part 2 JSON archive can be placed there. Older entries with no sender display **Unknown**. Sent and disregarded messages are tracked for the current session.

The console keeps the international cellphone-number validation from the supplied Part 2 project. The assignment fixture for message 4 contains the local number `0838884567` and the text `It is dinner time !`. Tests inject that developer fixture directly and preserve the supplied text.

## Tests and walkthrough

Run `ant test` for the original registration and messaging tests, the five assignment fixtures, stored-message searches and reports, and JSON recovery and compatibility checks. The GitHub Actions workflow runs the same Ant build and tests on Linux and Windows.

1. Register a demo account and sign in. Send one message and show the sent report.
2. Store two messages for the same recipient and disregard another. Show the stored report, longest message, and recipient search.
3. Copy a stored message's ID and hash from the report. Search for its ID, delete it by hash, and restart to show that the deletion persisted.
4. Explain how the sent, disregarded, stored, ID, and hash arrays are populated. Explain how JSON is read into message objects and run the automated tests.

## Source and assistance

[1] Google, “Gson User Guide,” Array Examples. [Online]. Available: https://google.github.io/gson/UserGuide.html#array-examples

OpenAI Codex assisted with implementing and testing Part 3.
