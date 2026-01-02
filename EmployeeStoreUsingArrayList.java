import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmployeeStoreUsingArrayList implements EmployeeDirectory {
    private final List<Employee> employees;

    public EmployeeStoreUsingArrayList() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee e) {
        if (e != null) {
            employees.add(e);
        }
    }

    @Override
    public Employee getEmployeeById(UUID id) {
        if (id == null) return null;
        for (Employee e : employees) {
            if (id.equals(e.getId())) return e;
        }
        return null;
    }

    @Override
    public Employee[] getAllEmployees() {
        return employees.toArray(new Employee[0]);
    }

    @Override
    public Employee[] getAllEmployeesByDept(String dept) {
        if (dept == null) return new Employee[0];
        List<Employee> filtered = employees.stream()
                .filter(e -> dept.equalsIgnoreCase(e.getDepartment()))
                .collect(Collectors.toList());
        return filtered.toArray(new Employee[0]);
    }

    @Override
    public Employee[] getAllEmployeesByCity(String city) {
        if (city == null) return new Employee[0];
        List<Employee> filtered = employees.stream()
                .filter(e -> city.equalsIgnoreCase(e.getCity()))
                .collect(Collectors.toList());
        return filtered.toArray(new Employee[0]);
    }
}
