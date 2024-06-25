package registrationandlogin;

import java.util.ArrayList;
import java.util.List;

class Task {
    private static int taskCounter = 0;  // To generate unique task numbers

    private String taskName;
    private int taskNumber;
    private String taskDescription;
    private String developerDetails;
    private int taskDuration;
    private String taskID;
    private String taskStatus;

    // Static lists to keep track of all tasks
    private static List<String> developers = new ArrayList<>();
    private static List<String> taskNames = new ArrayList<>();
    private static List<String> taskIds = new ArrayList<>();
    private static List<Integer> taskDurations = new ArrayList<>();
    private static List<String> taskStatuses = new ArrayList<>();

    public Task(String taskName, String taskDescription, String developerDetails, int taskDuration, String taskStatus) {
        this.taskName = taskName;
        this.taskNumber = taskCounter++;
        this.taskDescription = taskDescription;
        this.developerDetails = developerDetails;
        this.taskDuration = taskDuration;
        this.taskStatus = taskStatus;
        this.taskID = createTaskID();
        
        // Add task details to the static lists
        developers.add(developerDetails);
        taskNames.add(taskName);
        taskIds.add(taskID);
        taskDurations.add(taskDuration);
        taskStatuses.add(taskStatus);
    }

    // Method to check if the task description length is within the limit
    public boolean checkTaskDescription() {
        return taskDescription.length() <= 50;
    }

    // Method to create a unique Task ID
    private String createTaskID() {
        String taskID = taskName.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" +
                developerDetails.substring(developerDetails.length() - 3).toUpperCase();
        return taskID;
    }

    // Method to print task details
    public String printTaskDetails() {
        return "Task Details:\n" +
                "1. Task Status: " + taskStatus + "\n" +
                "2. Developer Details: " + developerDetails + "\n" +
                "3. Task Number: " + taskNumber + "\n" +
                "4. Task Name: " + taskName + "\n" +
                "5. Task Description: " + taskDescription + "\n" +
                "6. Task ID: " + taskID + "\n" +
                "7. Duration: " + taskDuration + " hours";
    }

    public int getTaskDuration() {
        return taskDuration;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDeveloperDetails() {
        return developerDetails;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    // Method to calculate the total duration of all tasks
    public static int returnTotalHours(List<Task> tasks) {
        int totalHours = 0;
        for (Task task : tasks) {
            totalHours += task.getTaskDuration();
        }
        return totalHours;
    }

    // Static methods to get task lists
    public static List<String> getDevelopers() {
        return developers;
    }

    public static List<String> getTaskNames() {
        return taskNames;
    }

    public static List<String> getTaskIds() {
        return taskIds;
    }

    public static List<Integer> getTaskDurations() {
        return taskDurations;
    }

    public static List<String> getTaskStatuses() {
        return taskStatuses;
    }
}