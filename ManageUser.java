import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.sql.*;
import java.util.Optional;

public class ManageUser extends Application {
    private TextField usernameField;
    private PasswordField passwordField;
    private ComboBox<String> roleBox;

    // Font sizes
    private final double labelFontSize = 18;
    private final double inputFontSize = 16;
    private final double buttonFontSize = 18;

    // Colors
    private final String headerColor = "-fx-background-color: #800000; -fx-text-fill: white;";
    private final String panelColor = "-fx-background-color: #dce6fa;";
    private final String buttonColor = "-fx-background-color: #800000; -fx-text-fill: white;";

    // Connection to SQL Server
    private Connection connect() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=LMS_TST;encrypt=true;trustServerCertificate=true";
        String user = "LMS_Admin";
        String password = "Averyleuxe08";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Connection Failed", e.getMessage());
            return null;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Management");

        TabPane tabPane = new TabPane();

        // Login Tab
        Tab loginTab = new Tab("Login");
        loginTab.setClosable(false);

        VBox loginPane = new VBox(20);
        loginPane.setPadding(new Insets(40));
        loginPane.setStyle(panelColor);
        loginPane.setAlignment(Pos.TOP_CENTER);

        Label loginHeader = new Label("Login");
        loginHeader.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");

        GridPane loginForm = new GridPane();
        loginForm.setVgap(15);
        loginForm.setHgap(10);
        loginForm.setAlignment(Pos.CENTER);

        Label loginUsernameLabel = new Label("Username:");
        Label loginPasswordLabel = new Label("Password:");

        usernameField = new TextField();
        passwordField = new PasswordField();
        usernameField.setPrefWidth(250);
        passwordField.setPrefWidth(250);

        loginForm.add(loginUsernameLabel, 0, 0);
        loginForm.add(usernameField, 1, 0);
        loginForm.add(loginPasswordLabel, 0, 1);
        loginForm.add(passwordField, 1, 1);

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(150);
        loginBtn.setStyle("-fx-font-size: " + buttonFontSize + "; " + buttonColor);
        loginBtn.setOnAction(e -> login());

        VBox loginBtnBox = new VBox(loginBtn);
        loginBtnBox.setAlignment(Pos.CENTER);

        loginPane.getChildren().addAll(loginHeader, loginForm, loginBtnBox);
        loginTab.setContent(loginPane);

        // Register Tab
        Tab registerTab = new Tab("Register");
        registerTab.setClosable(false);

        VBox registerPane = new VBox(20);
        registerPane.setPadding(new Insets(40));
        registerPane.setStyle(panelColor);
        registerPane.setAlignment(Pos.TOP_CENTER);

        Label registerHeader = new Label("Register");
        registerHeader.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");

        GridPane registerForm = new GridPane();
        registerForm.setVgap(15);
        registerForm.setHgap(10);
        registerForm.setAlignment(Pos.CENTER);

        Label regUserLabel = new Label("Username:");
        Label regPassLabel = new Label("Password:");
        Label roleLabel = new Label("Role:");

        TextField regUsernameField = new TextField();
        PasswordField regPasswordField = new PasswordField();
        roleBox = new ComboBox<>();
        roleBox.getItems().addAll("Admin", "Doctor", "Staff", "Patient");
        roleBox.getSelectionModel().selectFirst();

        regUsernameField.setPrefWidth(250);
        regPasswordField.setPrefWidth(250);
        roleBox.setPrefWidth(250);

        registerForm.add(regUserLabel, 0, 0);
        registerForm.add(regUsernameField, 1, 0);
        registerForm.add(regPassLabel, 0, 1);
        registerForm.add(regPasswordField, 1, 1);
        registerForm.add(roleLabel, 0, 2);
        registerForm.add(roleBox, 1, 2);

        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(150);
        registerBtn.setStyle("-fx-font-size: " + buttonFontSize + "; " + buttonColor);
        registerBtn.setOnAction(e -> register(regUsernameField.getText(), regPasswordField.getText(), roleBox.getValue()));

        VBox regBtnBox = new VBox(registerBtn);
        regBtnBox.setAlignment(Pos.CENTER);

        registerPane.getChildren().addAll(registerHeader, registerForm, regBtnBox);
        registerTab.setContent(registerPane);

        // Manage Users Tab
        Tab manageTab = new Tab("Manage Users");
        manageTab.setClosable(false);
        VBox managePane = new VBox(20);
        managePane.setPadding(new Insets(20));

        TableView<User> usersTable = new TableView<>();
        usersTable.getColumns().addAll(
            new TableColumn<>("ID") {{
                setCellValueFactory(new PropertyValueFactory<>("id"));
            }},
            new TableColumn<>("Username") {{
                setCellValueFactory(new PropertyValueFactory<>("username"));
            }},
            new TableColumn<>("Role") {{
                setCellValueFactory(new PropertyValueFactory<>("role"));
            }}
        );
        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button addUserBtn = new Button("Add User");
        Button editUserBtn = new Button("Edit User");
        Button deleteUserBtn = new Button("Delete User");
        addUserBtn.setStyle(buttonColor);
        editUserBtn.setStyle(buttonColor);
        deleteUserBtn.setStyle(buttonColor);
        HBox buttonsPanel = new HBox(10, addUserBtn, editUserBtn, deleteUserBtn);
        buttonsPanel.setAlignment(Pos.CENTER);
        managePane.getChildren().addAll(usersTable, buttonsPanel);
        manageTab.setContent(managePane);

        manageTab.setOnSelectionChanged(event -> {
            if (manageTab.isSelected()) {
                loadUsers(usersTable);
            }
        });

        addUserBtn.setOnAction(e -> {
            UserDialog dialog = new UserDialog(primaryStage, "Add User", null);
            Optional<User> result = dialog.showAndWait();
            result.ifPresent(user -> {
                try {
                    addUser(user.getUsername(), user.getPassword(), user.getRole());
                    loadUsers(usersTable);
                } catch (SQLException ex) {
                    showAlert(Alert.AlertType.ERROR, "Error Adding User", ex.getMessage());
                }
            });
        });

        editUserBtn.setOnAction(e -> {
            User selectedUser = usersTable.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to edit.");
                return;
            }

            UserDialog dialog = new UserDialog(primaryStage, "Edit User", selectedUser);
            Optional<User> result = dialog.showAndWait();
            result.ifPresent(user -> {
                try {
                    updateUser(selectedUser.getUsername(), user.getUsername(), user.getPassword(), user.getRole());
                    loadUsers(usersTable);
                } catch (SQLException ex) {
                    showAlert(Alert.AlertType.ERROR, "Error Updating User", ex.getMessage());
                }
            });
        });

        deleteUserBtn.setOnAction(e -> {
            User selectedUser = usersTable.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to delete.");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete user '" + selectedUser.getUsername() + "'?", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                deleteUser(selectedUser.getUsername());
                loadUsers(usersTable);
            }
        });

        tabPane.getTabs().addAll(loginTab, registerTab, manageTab);
        primaryStage.setScene(new Scene(tabPane, 900, 600));
        primaryStage.show();
    }

    private void loadUsers(TableView<User> table) {
        try (Connection con = connect()) {
            if (con == null) return;
            table.getItems().clear();
            ResultSet rs = con.createStatement().executeQuery("SELECT id, username, role FROM Users");
            while (rs.next()) {
                table.getItems().add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("role"), ""));
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Load Users Failed", e.getMessage());
        }
    }

    private void addUser(String username, String password, String role) throws SQLException {
        try (Connection con = connect()) {
            if (con == null) throw new SQLException("No DB connection");
            PreparedStatement ps = con.prepareStatement("INSERT INTO Users (username, password, role) VALUES (?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();
        }
    }

    private void updateUser(String originalUsername, String newUsername, String newPassword, String newRole) throws SQLException {
        try (Connection con = connect()) {
            if (con == null) throw new SQLException("No DB connection");
            if (!originalUsername.equals(newUsername)) {
                PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM Users WHERE username = ?");
                check.setString(1, newUsername);
                ResultSet rs = check.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) throw new SQLException("Username already exists.");
            }
            if (newPassword.isEmpty()) {
                PreparedStatement getPass = con.prepareStatement("SELECT password FROM Users WHERE username = ?");
                getPass.setString(1, originalUsername);
                ResultSet rs = getPass.executeQuery();
                if (rs.next()) newPassword = rs.getString("password");
            }
            PreparedStatement ps = con.prepareStatement("UPDATE Users SET username=?, password=?, role=? WHERE username=?");
            ps.setString(1, newUsername);
            ps.setString(2, newPassword);
            ps.setString(3, newRole);
            ps.setString(4, originalUsername);
            ps.executeUpdate();
        }
    }

    private void deleteUser(String username) {
        try (Connection con = connect()) {
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement("DELETE FROM Users WHERE username = ?");
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Delete Failed", e.getMessage());
        }
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please enter both username and password.");
            return;
        }
        try (Connection con = connect()) {
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement("SELECT role FROM Users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome! Role: " + rs.getString("role"));
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void register(String username, String password, String role) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "All fields must be filled.");
            return;
        }
        try {
            addUser(username.trim(), password.trim(), role);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User registered.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static class User {
        private int id;
        private String username;
        private String password;
        private String role;
        public User(int id, String username, String role, String password) {
            this.id = id; this.username = username; this.role = role; this.password = password;
        }
        public int getId() { return id; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getRole() { return role; }
        public void setId(int id) { this.id = id; }
        public void setUsername(String username) { this.username = username; }
        public void setPassword(String password) { this.password = password; }
        public void setRole(String role) { this.role = role; }
    }

    // UserDialog class
    class UserDialog extends Dialog<User> {
        private final TextField usernameField = new TextField();
        private final PasswordField passwordField = new PasswordField();
        private final ComboBox<String> roleBox = new ComboBox<>();

        public UserDialog(Stage owner, String title, User user) {
            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);
            setTitle(title);

            roleBox.getItems().addAll("Admin", "Doctor", "Staff", "Patient");
            if (user != null) {
                usernameField.setText(user.getUsername());
                roleBox.setValue(user.getRole());
            } else {
                roleBox.getSelectionModel().selectFirst();
            }

            GridPane grid = new GridPane();
            grid.setVgap(10);
            grid.setHgap(10);
            grid.setPadding(new Insets(20));
            grid.add(new Label("Username:"), 0, 0);
            grid.add(usernameField, 1, 0);
            grid.add(new Label("Password:"), 0, 1);
            grid.add(passwordField, 1, 1);
            grid.add(new Label("Role:"), 0, 2);
            grid.add(roleBox, 1, 2);

            getDialogPane().setContent(grid);
            getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            setResultConverter(btn -> {
                if (btn == ButtonType.OK) {
                    return new User(0, usernameField.getText(), roleBox.getValue(), passwordField.getText());
                }
                return null;
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
