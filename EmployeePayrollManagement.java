import java.sql.*;
import java.util.Scanner;

public class EmployeePayrollManagement {

    // Database credentials
    static final String URL = "jdbc:mysql://localhost:3306/syntecxhub_payroll";
    static final String USER = "root";
    static final String PASSWORD = "02@shishT05"; // change according to your MySQL password

    static Scanner sc = new Scanner(System.in);

    // Database connection method
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Add Employee
    public static void addEmployee() {
        try (Connection con = getConnection()) {

            System.out.print("Enter Employee Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Position: ");
            String position = sc.nextLine();

            System.out.print("Enter Base Salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();

            String query = "INSERT INTO employees(name, position, base_salary) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, position);
            ps.setDouble(3, salary);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee Added Successfully!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // View Employees
    public static void viewEmployees() {

        try (Connection con = getConnection()) {

            String query = "SELECT * FROM employees";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            System.out.println("\n===== EMPLOYEE RECORDS =====");

            while (rs.next()) {

                System.out.println(
                        "ID: " + rs.getInt("id") +
                                " | Name: " + rs.getString("name") +
                                " | Position: " + rs.getString("position") +
                                " | Salary: ₹" + rs.getDouble("base_salary")
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Update Salary
    public static void updateSalary() {

        try (Connection con = getConnection()) {

            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();

            System.out.print("Enter New Salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();

            String query = "UPDATE employees SET base_salary=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, salary);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Salary Updated Successfully!");
            } else {
                System.out.println("Employee Not Found!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Generate Payslip
    public static void generatePayslip() {

        try (Connection con = getConnection()) {

            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            String query = "SELECT * FROM employees WHERE id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String name = rs.getString("name");
                String position = rs.getString("position");
                double baseSalary = rs.getDouble("base_salary");

                // Payroll calculations
                double allowance = baseSalary * 0.20;
                double deduction = baseSalary * 0.10;
                double netSalary = baseSalary + allowance - deduction;

                System.out.println("\n========= PAYSLIP =========");
                System.out.println("Employee ID   : " + id);
                System.out.println("Employee Name : " + name);
                System.out.println("Position      : " + position);

                System.out.println("--------------------------------");
                System.out.println("Base Salary   : ₹" + baseSalary);
                System.out.println("Allowance     : ₹" + allowance);
                System.out.println("Deduction     : ₹" + deduction);
                System.out.println("--------------------------------");
                System.out.println("Net Salary    : ₹" + netSalary);
                System.out.println("================================");

            } else {
                System.out.println("Employee Not Found!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Main Menu
    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== EMPLOYEE PAYROLL MANAGEMENT =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Salary");
            System.out.println("4. Generate Payslip");
            System.out.println("5. Exit");

            System.out.print(" Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addEmployee();
                    break;

                case 2:
                    viewEmployees();
                    break;

                case 3:
                    updateSalary();
                    break;

                case 4:
                    generatePayslip();
                    break;

                case 5:
                    System.out.println("Exiting Program...");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}