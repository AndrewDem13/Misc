import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 Create classes, which describe employees with hourly wage and fixed payment. Give your suggestions
 about relations between classes. Implement method for calculating the average monthly salary. For
 employees with hourly wage use next formula: “average monthly salary= 20.8*8* hourly rate”, for employees
 with fixed payment – “average monthly salary= fixed monthly payment”. Write well commented code for
 solving next problems
 a) Sort the collection of employees in descending order by the average monthly salary. In the case of
 equal salary – by the name. Write ID, name and monthly salary for all employees from collection.
 b) Write information about first five employees from collection (problem a).
 c) Write ID of three last employees from collection (problem b).
 d) Write code for reading and writing collection of these objects from (into) file.
 e) Write code for handling the incorrect format of incoming file.
 */
public class TestTask {
    public static void main(String[] args) {
        // Collection of employees
        ArrayList<Employee> employees = new ArrayList<>();

        // Filling collection
        for (int i = 1; i <=10; i++) {
            employees.add(new Employee_FixedPayment(i, "Employee" + i, 100 * i));
            i++;
            employees.add(new Employee_HourlyWage(i, "Employee" + i, 10));
        }

        /*
        a) Sort the collection of employees in descending order by the average monthly salary. In the case of
            equal salary – by the name. Write ID, name and monthly salary for all employees from collection.
         */
        System.out.println("Problem a:");
        employees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                if (o1.getMonthlySalary() == o2.getMonthlySalary()) {
                    return o1.getName().compareTo(o2.getName());
                }

                if (o1.getMonthlySalary() > o2.getMonthlySalary())
                    return -1;
                else
                    return 0;
            }
        });
        for (Employee e : employees)
            System.out.println(String.format("Id%d: %s - %g", e.getId(), e.getName(), e.getMonthlySalary()));

        //   b) Write information about first five employees from collection (problem a)
        System.out.println("Problem b:");
        for (int i = 0; i < 5; i++)
            System.out.println(String.format("Id%d: %s - %g",
                    employees.get(i).getId(), employees.get(i).getName(), employees.get(i).getMonthlySalary()));


        //   c) Write ID of three last employees from collection (problem b).
        // Note: "collection" in this problem I understood as output of previous problem (first 5 employees)
        System.out.println("Problem c:");
        for (int i = 5-3; i < 5; i++)
            System.out.println(String.format("Id%d: %s - %g",
                    employees.get(i).getId(), employees.get(i).getName(), employees.get(i).getMonthlySalary()));

        //   d) Write code for reading and writing collection of these objects from (into) file.
        System.out.println("Problem d:");
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("C:\\Users\\Admin.Admin-PC\\Desktop\\java.txt"));
            out.writeObject(employees);
            out.flush();
            out.close();
        }
        catch (Exception ignored) {}

        ArrayList<Employee> temp = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("C:\\Users\\Admin.Admin-PC\\Desktop\\java.txt"));
            temp = (ArrayList) in.readObject();
            in.close();
        }
        //   e) Write code for handling the incorrect format of incoming file.
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception ignored) {}

        for (Employee e : temp)
            System.out.println(String.format("Id%d: %s - %g", e.getId(), e.getName(), e.getMonthlySalary()));
    }
}
