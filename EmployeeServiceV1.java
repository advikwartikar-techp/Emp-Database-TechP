import java.util.UUID;

public class EmployeeServiceV1 implements EmployeeDirectory {
    private final EmployeeDirectory employeeStore;

    public EmployeeServiceV1(EmployeeDirectory employeeStore) {
        this.employeeStore = employeeStore;
    }

    @Override
    public Employee getEmployeeById(UUID id) {
        return employeeStore.getEmployeeById(id);
    }

    @Override
    public Employee[] getAllEmployees() {
        return employeeStore.getAllEmployees();
    }

    @Override
    public Employee[] getAllEmployeesByDept(String dept) {
        return employeeStore.getAllEmployeesByDept(dept);
    }

    @Override
    public Employee[] getAllEmployeesByCity(String city) {
        return employeeStore.getAllEmployeesByCity(city);
    }
}
