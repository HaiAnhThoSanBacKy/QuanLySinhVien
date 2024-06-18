import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Student implements Serializable {
    int id;
    String name;
    double marks;

    Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public String getRanking() {
        if (marks >= 0 && marks < 5.0) return "Fail";
        if (marks >= 5.0 && marks < 6.5) return "Medium";
        if (marks >= 6.5 && marks < 7.5) return "Good";
        if (marks >= 7.5 && marks < 9.0) return "Very Good";
        if (marks >= 9.0 && marks <= 10.0) return "Excellent";
        return "Invalid marks";
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Marks: " + marks + ", Ranking: " + getRanking();
    }
}

public class StudentManagementSystem {
    private ArrayList<Student> students;
    private Scanner scanner;
    private static final String DATA_FILE = "students.dat";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadStudents();
    }

    public void addStudent() {
        System.out.println("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.println("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Student Marks: ");
        double marks = scanner.nextDouble();
        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully.");
        saveStudents();
    }

    public void editStudent() {
        System.out.println("Enter Student ID to edit: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        for (Student student : students) {
            if (student.id == id) {
                System.out.println("Enter new name: ");
                student.name = scanner.nextLine();
                System.out.println("Enter new marks: ");
                student.marks = scanner.nextDouble();
                System.out.println("Student information updated successfully.");
                saveStudents();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void deleteStudent() {
        System.out.println("Enter Student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        students.removeIf(student -> student.id == id);
        System.out.println("Student deleted successfully.");
        saveStudents();
    }

    public void sortStudents() {
        Collections.sort(students, Comparator.comparingDouble(student -> student.marks));
        System.out.println("Students sorted by marks.");
    }

    public void searchStudent() {
        System.out.println("Enter Student ID to search: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        for (Student student : students) {
            if (student.id == id) {
                System.out.println(student);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void displayAllStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\n1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Sort Students");
            System.out.println("5. Search Student");
            System.out.println("6. Display All Students");
            System.out.println("7. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    editStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    sortStudents();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 6:
                    displayAllStudents();
                    break;
                case 7:
                    saveStudents();
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
            System.out.println("Student data saved.");
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    private void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (ArrayList<Student>) ois.readObject();
            System.out.println("Student data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student data: " + e.getMessage());
        }
    }
}
