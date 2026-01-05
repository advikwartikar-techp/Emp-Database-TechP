import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvReader {

    public static List<Employee> readEmployees(String csvPath) {
        File file = new File(csvPath);
        List<Employee> list = new ArrayList<>();

        if (!file.exists()) {
            System.out.println("CSV not found at " + csvPath + "; skipping CSV load.");
            return list;
        }

        // ✅ Modern, no-deprecation CSVFormat
        CSVFormat format = CSVFormat.DEFAULT.builder()
            .setHeader()                   // first row = header
            .setSkipHeaderRecord(true)     // skip header
            .setIgnoreSurroundingSpaces(true) // trim
            .get();                        // ← non‑deprecated
        try (Reader reader = new FileReader(file);
             CSVParser parser = CSVParser.parse(reader, format)) {

            for (CSVRecord record : parser) {
                if (record == null || record.size() == 0) continue;

                // Access columns by index
                String firstName = record.size() > 0 ? record.get(0).trim() : "";
                String startDateStr = record.size() > 2 ? record.get(2).trim() : "";
                String salaryStr = record.size() > 4 ? record.get(4).trim() : "";
                String team = record.size() > 7 ? record.get(7).trim() : "";

                if (firstName.isEmpty()) firstName = "Unknown";

                float salary;
                try {
                    salary = Float.parseFloat(salaryStr);
                } catch (NumberFormatException ex) {
                    continue; // skip invalid salary
                }

                LocalDate dob = tryParseDate(startDateStr);
                if (dob == null) continue; // skip invalid date

                Employee e = new Employee(
                        UUID.randomUUID(),
                        firstName,
                        salary,
                        dob,
                        team,
                        ""
                );

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
        String[] patterns = {
                "M/d/yyyy", "M-d-yyyy", "MM-dd-yyyy", "MM/dd/yyyy",
                "dd-MM-yyyy", "MM-dd-uuuu", "MM/dd/uuuu"
        };

        for (String pat : patterns) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern(pat);
                return LocalDate.parse(t, f);
            } catch (DateTimeParseException ignored) {}
        }

        try {
            return LocalDate.parse(t);
        } catch (DateTimeParseException ignored) {}

        return null;
    }
}
