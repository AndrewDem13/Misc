public class Employee_HourlyWage extends Employee{
    public Employee_HourlyWage(int id, String name, double wage) {
        super(id, name, wage);
    }

    public double getMonthlySalary() {
        return 20.8*8*getWage();
    }
}
