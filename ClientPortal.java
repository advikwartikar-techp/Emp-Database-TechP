import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ClientPortal {
    public static void main(String[] args) {
        EmployeeStoreUsingArrayList store = new EmployeeStoreUsingArrayList();

        // Hardcoded sample employees
        Employee e1 = new Employee(UUID.randomUUID(), "Advik Wartikar", 75000f, LocalDate.of(1998, 4, 12), "Engineering", "Pune");
        Employee e2 = new Employee(UUID.randomUUID(), "Piyush Gosavi", 62000f, LocalDate.of(1990, 9, 5), "Sales", "Mumbai");
        Employee e3 = new Employee(UUID.randomUUID(), "Nicole Aiman", 82000f, LocalDate.of(2001, 1, 23), "Engineering", "Delhi");
        Employee e4 = new Employee(UUID.randomUUID(), "Shaunak Chodankar", 54000f, LocalDate.of(1995, 7, 30), "Support", "Pune");

        store.addEmployee(e1);
        store.addEmployee(e2);
        store.addEmployee(e3);
        store.addEmployee(e4);

        // Load employees from CSV if available (path passed as first arg or default employees.csv)
        String csvPath = args.length > 0 ? args[0] : "employees.csv";
        List<Employee> csvEmployees = CsvReader.readEmployees(csvPath);
        int addedFromCsv = 0;
        for (Employee e : csvEmployees) {
            store.addEmployee(e);
            addedFromCsv++;
        }
        System.out.println("Number of employees added from CSV: " + addedFromCsv);

        // Accept additional employees from console input
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Add more employees interactively. Leave name blank to finish.");
            while (true) {
                System.out.print("Name (blank to finish): ");
                String name = scanner.nextLine().trim();
                if (name.isEmpty()) break;

                System.out.print("Salary (numeric): ");
                String salaryStr = scanner.nextLine().trim();
                float salary;
                try {
                    salary = Float.parseFloat(salaryStr);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid salary; please enter a numeric value.");
                    continue;
                    }

                System.out.print("DOB (yyyy-MM-dd): ");
                String dobStr = scanner.nextLine().trim();
                LocalDate dob = tryParseDate(dobStr);
                if (dob == null) {
                    System.out.println("Invalid date format; please use a supported format.");
                    continue;
                }

                System.out.print("Department: ");
                String dept = scanner.nextLine().trim();

                System.out.print("City: ");
                String city = scanner.nextLine().trim();

                Employee e = new Employee(UUID.randomUUID(), name, salary, dob, dept, city);
                store.addEmployee(e);
                System.out.println("Added: " + e.getName());
            }
        }


        EmployeeServiceV1 service = new EmployeeServiceV1(store);

        System.out.println("All employees:");
        for (Employee e : service.getAllEmployees()) {
            System.out.println(" - " + e);
        }

        System.out.println();
        System.out.println("Employees in Engineering:");
        for (Employee e : service.getAllEmployeesByDept("Engineering")) {
            System.out.println(" - " + e.getName() + " (" + e.getDepartment() + ")");
        }

        System.out.println();
        System.out.println("Employees in Pune:");
        for (Employee e : service.getAllEmployeesByCity("Pune")) {
            System.out.println(" - " + e.getName() + " (" + e.getCity() + ")");
        }

        System.out.println();
        System.out.println("Lookup by ID (first employee):");
        UUID idToLookup = e1.getId();
        Employee found = service.getEmployeeById(idToLookup);
        System.out.println(found != null ? found : "Not found");
    }

    private static LocalDate tryParseDate(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        String t = s.trim();
        String[] patterns = {"M/d/yyyy", "M-d-yyyy", "MM-dd-yyyy", "MM/dd/yyyy", "dd-MM-yyyy", "MM-dd-uuuu", "MM/dd/uuuu"};
        for (String pat : patterns) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern(pat);
                return LocalDate.parse(t, f);
            } catch (DateTimeParseException ignored) {
            }
        }
        try {
            return LocalDate.parse(t);
        } catch (DateTimeParseException ignored) {
        }
        return null;
    }
}
