package registrationandlogin;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Registration {
    private static final String USERNAME_PATTERN = "^(?=.*[_])(?=.*[0-9]).{5}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";

    private User user;
    private Login login;

    public Registration() {
        login = new Login();
    }

    private class User {
        private String username;
        private String password;
        private String firstName;
        private String lastName;

        public User(String username, String password, String firstName, String lastName) {
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    private class Login {
        public String returnLoginStatus(String username, String password) {
            if (user == null) {
                return "No user registered.";
            }
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return "Welcome " + username + "!";
            }
            return "Invalid username or password.";
        }
    }

    public boolean checkUsername(String username) {
        return username.matches(USERNAME_PATTERN);
    }

    public boolean checkPasswordComplexity(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public String registerUser(String username, String password, String firstName, String lastName) {
        if (!checkUsername(username)) {
            return "Username is not correctly formatted. It must contain an underscore and a number, and be exactly 5 characters.";
        } else if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted. It must have a minimum of 8 characters, at least one capital letter, and one number.";
        } else {
            user = new User(username, password, firstName, lastName);
            return "User has been registered successfully.";
        }
    }

    public User getUser() {
        return user;
    }

    public String returnLoginStatus(String username, String password) {
        return login.returnLoginStatus(username, password);
    }

    public static void main(String[] args) {
        Registration registration = new Registration();
        List<Task> tasks = new ArrayList<>();

        while (true) {
            String[] options = {"Register", "Login", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Welcome! Please choose an option:",
                    "User Registration and Login",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    String firstName = JOptionPane.showInputDialog("Enter first name:");
                    String lastName = JOptionPane.showInputDialog("Enter last name:");
                    String username = JOptionPane.showInputDialog("Enter username (must contain an underscore and a number, exactly 5 characters):");
                    String password = JOptionPane.showInputDialog("Enter password (minimum 8 characters, at least one capital letter and one number):");

                    String regMsg = registration.registerUser(username, password, firstName, lastName);
                    JOptionPane.showMessageDialog(null, regMsg);
                    break;
                case 1:
                    if (registration.getUser() == null) {
                        JOptionPane.showMessageDialog(null, "No registered user found. Please register first.");
                    } else {
                        String loginUsername = JOptionPane.showInputDialog("Enter username to login:");
                        String loginPassword = JOptionPane.showInputDialog("Enter password to login:");

                        String loginMsg = registration.returnLoginStatus(loginUsername, loginPassword);
                        JOptionPane.showMessageDialog(null, loginMsg);

                        if (loginMsg.startsWith("Welcome")) {
                            manageTasks(tasks);
                        }
                    }
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Exiting...");
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please choose either Register, Login, or Exit.");
                    break;
            }
        }
    }

    private static void manageTasks(List<Task> tasks) {
        JOptionPane.showMessageDialog(null, "Welcome to EasyKanban");

        while (true) {
            String[] taskOptions = {"Add tasks", "Show report", "Quit"};
            int taskChoice = JOptionPane.showOptionDialog(null, "Please choose an option:",
                    "Task Management",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, taskOptions, taskOptions[0]);

            switch (taskChoice) {
                case 0:
                    try {
                        int numberOfTasks = Integer.parseInt(JOptionPane.showInputDialog("Enter number of tasks to add:"));
                        for (int i = 0; i < numberOfTasks; i++) {
                            String taskName = JOptionPane.showInputDialog("Enter task name:");
                            String taskDescription = JOptionPane.showInputDialog("Enter task description (max 50 characters):");
                            if (taskDescription == null || taskDescription.length() > 50) {
                                JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters");
                                i--;
                                continue;
                            }
                            String developerDetails = JOptionPane.showInputDialog("Enter developer details (first and last name):");
                            int taskDuration = Integer.parseInt(JOptionPane.showInputDialog("Enter task duration (in hours):"));
                            String[] statusOptions = {"To Do", "Done", "Doing"};
                            String taskStatus = (String) JOptionPane.showInputDialog(null, "Select task status:",
                                    "Task Status", JOptionPane.QUESTION_MESSAGE, null, statusOptions, statusOptions[0]);

                            Task task = new Task(taskName, taskDescription, developerDetails, taskDuration, taskStatus);
                            tasks.add(task);
                            JOptionPane.showMessageDialog(null, task.printTaskDetails());
                            int totalHours = Task.returnTotalHours(tasks);
                            JOptionPane.showMessageDialog(null, "Total number of hours across all tasks: " + totalHours);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid number entered. Please try again.");
                    } catch (NullPointerException e) {
                        JOptionPane.showMessageDialog(null, "Operation canceled.");
                    }
                    break;
                case 1:
                    showReport(tasks);
                    break;
                case 2:
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please choose either Add tasks, Show report, or Quit.");
                    break;
            }
        }
    }

    private static void showReport(List<Task> tasks) {
        while (true) {
            String reportOptions = 
                    "Please choose a report option:\n" +
                    "1. Search for a task by name\n" +
                    "2. Search for all tasks assigned to a developer\n" +
                    "3. Delete a task by name\n" +
                    "4. Display all tasks\n" +
                    "5. Show developer and duration for task with longest duration\n" +
                    "6. Return to previous menu";

            String reportChoiceStr = JOptionPane.showInputDialog(reportOptions);
            int reportChoice;
            try {
                reportChoice = Integer.parseInt(reportChoiceStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a number from 1 to 6.");
                continue;
            }

            switch (reportChoice) {
                case 1:
                    String searchTaskName = JOptionPane.showInputDialog("Enter task name to search:");
                    if (searchTaskName != null) {
                        boolean found = false;
                        StringBuilder taskReport = new StringBuilder();
                        for (Task task : tasks) {
                            if (task.getTaskName().equalsIgnoreCase(searchTaskName)) {
                                taskReport.append(task.printTaskDetails()).append("\n");
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            taskReport.append("Task with name '").append(searchTaskName).append("' not found.\n");
                        }
                        JOptionPane.showMessageDialog(null, taskReport.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "Operation canceled.");
                    }
                    break;
                case 2:
                    String searchDeveloper = JOptionPane.showInputDialog("Enter developer name to search tasks:");
                    if (searchDeveloper != null) {
                        StringBuilder developerReport = new StringBuilder();
                        boolean found = false;
                        for (Task task : tasks) {
                            if (task.getDeveloperDetails().equalsIgnoreCase(searchDeveloper)) {
                                developerReport.append(task.printTaskDetails()).append("\n");
                                found = true;
                            }
                        }
                        if (!found) {
                            developerReport.append("No tasks assigned to '").append(searchDeveloper).append("'.\n");
                        }
                        JOptionPane.showMessageDialog(null, developerReport.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "Operation canceled.");
                    }
                    break;
                case 3:
                    String deleteTaskName = JOptionPane.showInputDialog("Enter task name to delete:");
                    if (deleteTaskName != null) {
                        boolean found = false;
                        for (int i = 0; i < tasks.size(); i++) {
                            if (tasks.get(i).getTaskName().equalsIgnoreCase(deleteTaskName)) {
                                tasks.remove(i);
                                JOptionPane.showMessageDialog(null, "Task '" + deleteTaskName + "' deleted.");
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            JOptionPane.showMessageDialog(null, "Task with name '" + deleteTaskName + "' not found.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Operation canceled.");
                    }
                    break;
                case 4:
                    StringBuilder allTasksReport = new StringBuilder("Full details of all tasks:\n");
                    for (Task task : tasks) {
                        allTasksReport.append(task.printTaskDetails()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, allTasksReport.toString());
                    break;
                case 5:
                    Task longestTask = null;
                    for (Task task : tasks) {
                        if (longestTask == null || task.getTaskDuration() > longestTask.getTaskDuration()) {
                            longestTask = task;
                        }
                    }
                    if (longestTask != null) {
                        JOptionPane.showMessageDialog(null, "Developer: " + longestTask.getDeveloperDetails() + "\nDuration: " + longestTask.getTaskDuration() + " hours");
                    } else {
                        JOptionPane.showMessageDialog(null, "No tasks found.");
                    }
                    break;
                case 6:
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please choose a valid report option.");
                    break;
            }
        }
    }
}