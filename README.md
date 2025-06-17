# Persona Prediction Application

A Java Swing-based desktop application designed to allow users to register, log in, and manage personal data to generate unique persona predictions based on their name and birth date (determining zodiac sign). The application follows an MVC (Model-View-Controller) architectural pattern and uses MySQL as its database.

## Features

* **User Management:**
    * User Registration: Create new accounts.
    * User Login: Securely access the application.
* **Persona Data Management:**
    * Add New Data: Input a person's name and birth date.
    * View All Data: See a list of all entered persona data associated with the logged-in user.
    * Edit Data: Update existing persona entries.
    * Delete Data: Remove persona entries.
* **Persona Prediction:**
    * Generates a prediction based on the zodiac sign derived from the birth date and a persona text associated with the first letter of the name.
* **Interactive GUI:** Built with Java Swing for a user-friendly interface.
* **Background Music:** Plays background audio upon application launch.

## Technologies Used

* **Language:** Java (JDK 21)
* **GUI Framework:** Java Swing
* **Database:** MySQL
* **Database Connector:** MySQL Connector/J 8.3.0

## Prerequisites

Before running this application, ensure you have the following installed:

* **Java Development Kit (JDK) 21 or higher:** You can download it from the [Oracle Website](https://www.oracle.com/java/technologies/downloads/).
* **MySQL Database Server:** Download and install MySQL Community Server from the [MySQL Website](https://dev.mysql.com/downloads/mysql/).
* **MySQL Connector/J 8.3.0:** This JDBC driver is required for Java applications to connect to MySQL. You can download the JAR file from the [MySQL Connector/J Downloads Page](https://dev.mysql.com/downloads/connector/j/).
* **A Java Integrated Development Environment (IDE):**
    * [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Community or Ultimate)
    * [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/packages/)
    * [Apache NetBeans](https://netbeans.apache.org/download/index.html)
    * [VS Code with Java Extensions](https://code.visualstudio.com/docs/java/java-tutorial)

## Setup Instructions

### 1. Database Setup

1.  **Start MySQL Server:** Ensure your MySQL database server is running.
2.  **Create Database:** Open your MySQL client (e.g., phpMyAdmin, MySQL Workbench, or command line) and create a new database named `database_ramalan`.
    ```sql
    CREATE DATABASE database_ramalan;
    ```
3.  **Import Schema and Data:**
    Locate the SQL dump file: `src/resources/Database/database_ramalan.sql`.
    Import this file into the newly created `database_ramalan`. This will create the necessary tables (`data`, `prediksi`, `referensi`, `user`) and populate them with initial data.
4.  **Database Credentials:** The application is configured to connect to MySQL on `localhost:3306` with the username `root` and an empty password. If your MySQL setup uses different credentials, you must update the `src/Database/Connector.java` file accordingly:
    ```java
    // src/Database/Connector.java
    private static String namadb = "database_ramalan";
    private static String urldb = "jdbc:mysql://localhost:3306/" + namadb;
    private static String username_db = "root"; // Change if your username is different
    private static String password_db = "";     // Change if your password is not empty
    ```

### 2. Project Import & Dependencies

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/your-username/persona-prediction-app.git](https://github.com/your-username/persona-prediction-app.git)
    cd persona-prediction-app
    ```
    (Note: Replace `https://github.com/your-username/persona-prediction-app.git` with the actual repository URL if different).
2.  **Open in IDE:** Import the project into your preferred Java IDE.
3.  **Add MySQL Connector/J to Classpath:**
    * Download `mysql-connector-j-8.3.0.jar` (if you haven't already).
    * In your IDE, add this JAR file to the project's build path or module dependencies. The project's `.idea/libraries/mysql_connector_j_8.3.0.xml` file indicates that this JAR is expected to be present, usually in your local Maven/Gradle repository or manually added to a project's `lib` directory.

### 3. Running the Application

1.  **Run Main Class:** Navigate to `src/Main/Main.java`.
2.  **Execute:** Run the `main` method.

The application window should appear, and background music will start playing.

## Usage

1.  **Main View:** Upon launch, you'll see the main welcome screen with options to "LOGIN" or "REGISTER".
2.  **Register:** If you're a new user, click "REGISTER," fill in your desired username and password, and click "REGISTER".
3.  **Login:** If you have an account, click "LOGIN," enter your username and password, and click "LOGIN".
4.  **Menu View:** After successful login, you'll be directed to the main menu, where you can manage persona data.
    * **ADD DATA:** Click the "ADD DATA" button to add a new person's name and birth date.
    * **VIEW/EDIT/DELETE:** The table will display your persona data. You can "Lihat" (View) the prediction, "Edit" an entry, or "Delete" it.
5.  **Prediction View:** When you view a persona, the application will display the generated prediction, including the determined zodiac sign and a text based on the first letter of the name.
6.  **Logout:** Click "LOGOUT" to return to the login screen.

## Project Structure

The project generally follows an MVC (Model-View-Controller) design pattern:

* `src/Main/`: Contains the application's entry point (`Main.java`) and `PlayMusic.java` for background audio.
* `src/Database/`: Holds the `Connector.java` class for establishing a connection to the MySQL database.
* `src/Model/Data/`: Contains classes related to data entities (`ModelData.java`, `ModelPrediksi.java`), data access objects (`DAOData.java`), and interfaces (`InterfaceDAOData.java`) for persona-related information.
* `src/Model/User/`: Contains classes related to user entities (`ModelUser.java`), data access objects (`DAOUser.java`), and interfaces (`InterfaceDAOUser.java`) for user authentication.
* `src/View/Form/`: Contains GUI forms for user authentication, such as `LoginView.java` and `RegisterView.java`.
* `src/View/Menu/`: Contains GUI forms for the main application functionalities, including `MenuView.java`, `AddView.java`, `EditView.java`, `PrediksiView.java`, `CustomDialogView.java`, and `RoundedPanel.java` for custom UI components.
* `src/Controller/`: Contains the logic to handle user input and update the model and view, with `ControllerData.java` for persona data operations and `ControllerUser.java` for user authentication.
* `src/resources/`: Stores static assets like images (`BackgroundMainView.png`, `logoMainView.png`, zodiac images) and the database schema (`database_ramalan.sql`).

## Contributing

Feel free to fork the repository, make improvements, and submit pull requests.

## License

(You can add your desired license here, e.g., MIT, Apache 2.0, etc.)
