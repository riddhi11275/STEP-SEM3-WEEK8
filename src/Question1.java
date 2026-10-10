abstract class WashType {
    abstract int getDuration();
    abstract double getCharge();
    abstract String getName();
}

class QuickWash extends WashType {
    int getDuration() { return 30; }
    double getCharge() { return 20; }
    String getName() { return "Quick"; }
}

class NormalWash extends WashType {
    int getDuration() { return 45; }
    double getCharge() { return 30; }
    String getName() { return "Normal"; }
}

class HeavyWash extends WashType {
    int getDuration() { return 60; }
    double getCharge() { return 45; }
    String getName() { return "Heavy"; }
}

class Student {
    String name;

    Student(String name) {
        this.name = name;
    }
}

class WashCycle {
    Student student;
    WashingMachine machine;
    WashType wash;

    WashCycle(Student s, WashingMachine m, WashType w) {
        student = s;
        machine = m;
        wash = w;
    }
}

class WashingMachine {
    String id;
    private WashCycle currentCycle;

    WashingMachine(String id) {
        this.id = id;
    }

    boolean startWash(Student s, WashType w) {
        if (currentCycle != null) {
            System.out.println("Machine " + id + " is currently busy.");
            return false;
        }

        currentCycle = new WashCycle(s, this, w);
        System.out.println(w.getName() + " wash started on " + id
                + " for " + s.name + " (" + w.getDuration()
                + " min). Charge: ₹"
                + String.format("%.2f", w.getCharge()) + ".");
        return true;
    }

    void completeWash() {
        if (currentCycle == null) {
            System.out.println(id + " is already free.");
            return;
        }

        System.out.println(id + " cycle completed.");
        currentCycle = null;
        System.out.println(id + " is now free.");
    }
}

public class Question1 {
    public static void main(String[] args) {
        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        m1.startWash(asha, new QuickWash());
        m1.startWash(ravi, new HeavyWash());
        m2.startWash(ravi, new HeavyWash());

        m1.completeWash();
        m1.startWash(neha, new NormalWash());
    }
}
