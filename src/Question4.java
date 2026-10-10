interface MembershipPlan {
    double calculateFee();
    String getName();
}

class MonthlyPlan implements MembershipPlan {
    public double calculateFee() { return 1000; }
    public String getName() { return "Monthly"; }
}

class QuarterlyPlan implements MembershipPlan {
    public double calculateFee() { return 2700; }
    public String getName() { return "Quarterly"; }
}

class AnnualPlan implements MembershipPlan {
    public double calculateFee() { return 9000; }
    public String getName() { return "Annual"; }
}

class Member {
    String name;

    Member(String name) {
        this.name = name;
    }
}

class Membership {
    private Member member;
    private MembershipPlan plan;
    private String status = "Active";

    Membership(Member member, MembershipPlan plan) {
        this.member = member;
        this.plan = plan;

        System.out.printf("%s membership created for %s. Fee: ₹%.2f. Status: %s.%n",
                plan.getName(), member.name, plan.calculateFee(), status);
    }

    void checkIn() {
        if (status.equals("Active")) {
            System.out.println(member.name + " checked in successfully.");
        } else {
            System.out.println("Check-in denied: " + member.name
                    + "'s membership is " + status + ".");
        }
    }

    void freeze() {
        if (status.equals("Expired")) {
            System.out.println("Cannot freeze an Expired membership.");
        } else if (status.equals("Frozen")) {
            System.out.println("Membership is already Frozen.");
        } else {
            status = "Frozen";
            System.out.println(member.name + "'s membership frozen.");
            System.out.println("Status: " + status + ".");
        }
    }

    void unfreeze() {
        if (status.equals("Expired")) {
            System.out.println("Cannot unfreeze an Expired membership.");
        } else if (status.equals("Frozen")) {
            status = "Active";
            System.out.println(member.name + "'s membership unfrozen.");
            System.out.println("Status: " + status + ".");
        } else {
            System.out.println("Membership is already Active.");
        }
    }

    void expire() {
        status = "Expired";
        System.out.println(member.name + "'s membership expired.");
        System.out.println("Status: " + status + ".");
    }
}

public class Question4 {
    public static void main(String[] args) {
        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        Membership m1 = new Membership(asha, new QuarterlyPlan());
        Membership m2 = new Membership(ravi, new MonthlyPlan());

        m1.checkIn();
        m1.freeze();
        m1.checkIn();

        m2.expire();
        m2.freeze();
    }
}
