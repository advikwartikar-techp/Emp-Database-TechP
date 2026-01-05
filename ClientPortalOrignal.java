import java.time.LocalDate;
import java.util.UUID;

public class ClientPortalOrignal {
    public static void main(String[] args) {
        EmployeeStoreUsingArrayList store = new EmployeeStoreUsingArrayList();

        Employee e1 = new Employee(UUID.randomUUID(), "Advik Wartikar", 75000f, LocalDate.of(1988, 4, 12), "Engineering", "Pune");
        Employee e2 = new Employee(UUID.randomUUID(), "Shaunak Chodhankar", 62000f, LocalDate.of(1990, 9, 5), "Sales", "Mumbai");
        Employee e3 = new Employee(UUID.randomUUID(), "Nicole Aiman", 82000f, LocalDate.of(1985, 1, 23), "Engineering", "Pune");
        Employee e4 = new Employee(UUID.randomUUID(), "Piyush Gosavi", 54000f, LocalDate.of(1995, 7, 30), "Support", "Banglore");

        store.addEmployee(e1);
        store.addEmployee(e2);
        store.addEmployee(e3);
        store.addEmployee(e4);

        EmployeeDirectory employeeService = new EmployeeServiceV1(store);

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
        System.out.println("Employees in Chicago:");
        for (Employee e : service.getAllEmployeesByCity("Chicago")) {
            System.out.println(" - " + e.getName() + " (" + e.getCity() + ")");
        }

        System.out.println();
        System.out.println("Lookup by ID (first employee):");
        UUID idToLookup = e1.getId();
        Employee found = service.getEmployeeById(idToLookup);
        System.out.println(found != null ? found : "Not found");
    }
}

