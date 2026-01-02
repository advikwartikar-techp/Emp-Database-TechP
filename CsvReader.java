import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CsvReader {
    public static List<Employee> readEmployees(String csvPath) {
        Path p = Paths.get(csvPath);
        List<Employee> list = new ArrayList<>();
        if (!Files.exists(p)) {
            System.out.println("CSV not found at " + csvPath + "; skipping CSV load.");
            return list;
        }

        try {
            List<String> lines = Files.readAllLines(p);
            boolean headerSkipped = false;
            for (String line : lines) {
                if (!headerSkipped) { headerSkipped = true; continue; }
                if (line.trim().isEmpty()) continue;
                String[] cols = line.split(",", -1);
                if (cols.length < 5) continue;
                String firstName = cols[0].trim();
                String startDateStr = cols[2].trim();
                String salaryStr = cols[4].trim();
                String team = cols.length > 7 ? cols[7].trim() : "";

                if (firstName.isEmpty()) firstName = "Unknown";

                float salary;
                try {
                    salary = Float.parseFloat(salaryStr);
                } catch (NumberFormatException ex) {
                    continue;
                }

                LocalDate dob = tryParseDate(startDateStr);
                if (dob == null) continue;

                Employee e = new Employee(UUID.randomUUID(), firstName, salary, dob, team, "");
                list.add(e);
            }
            System.out.println("Loaded " + list.size() + " employees from " + csvPath);
        } catch (IOException ex) {
            System.out.println("Failed to read CSV: " + ex.getMessage());
        }

        return list;
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
