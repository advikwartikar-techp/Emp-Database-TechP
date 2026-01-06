import java.time.LocalDate;
import java.util.UUID;

public class Employee {
    private final UUID id;
    private final String name;
    private final float salary;
    private final LocalDate dob;
    private final String department;
    private final String city;

    public Employee(UUID id, String name, float salary, LocalDate dob, String department, String city) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.dob = dob;
        this.department = department;
        this.city = city;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getSalary() {
        return salary;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getDepartment() {
        return department;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", dob=" + dob +
                ", department='" + department + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
