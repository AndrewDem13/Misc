import java.io.Serializable;

public abstract class Employee implements Serializable{
    private int id;
    private String name;
    private double wage;


    Employee(int id, String name, double wage) {
        this.id = id;
        this.name = name;
        this.wage = wage;
    }

    abstract public double getMonthlySalary();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public int getId() {
        return id;
    }
}
