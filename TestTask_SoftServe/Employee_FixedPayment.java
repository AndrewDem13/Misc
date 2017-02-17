public class Employee_FixedPayment extends Employee{
    public Employee_FixedPayment(int id, String name, double wage) {
        super(id, name, wage);
    }

    public double getMonthlySalary() {
        return getWage();
    }
}
