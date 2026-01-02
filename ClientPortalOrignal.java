import java.time.LocalDate;
import java.util.UUID;

public class ClientPortalOrignal {
    public static void main(String[] args) {
        EmployeeStoreUsingArrayList store = new EmployeeStoreUsingArrayList();

        Employee e1 = new Employee(UUID.randomUUID(), "Alice Johnson", 75000f, LocalDate.of(1988, 4, 12), "Engineering", "Seattle");
        Employee e2 = new Employee(UUID.randomUUID(), "Bob Smith", 62000f, LocalDate.of(1990, 9, 5), "Sales", "Chicago");
        Employee e3 = new Employee(UUID.randomUUID(), "Carol White", 82000f, LocalDate.of(1985, 1, 23), "Engineering", "Seattle");
        Employee e4 = new Employee(UUID.randomUUID(), "Dan Brown", 54000f, LocalDate.of(1995, 7, 30), "Support", "Chicago");

        store.addEmployee(e1);
        store.addEmployee(e2);
        store.addEmployee(e3);
        store.addEmployee(e4);

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
