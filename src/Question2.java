import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

abstract class Assignment {
    String title;
    int maxMarks;
    LocalDate dueDate;

    Assignment(String title, int maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    abstract double applyPenalty(double marks, long daysLate);
}

class CodingAssignment extends Assignment {
    CodingAssignment(String title, int marks, LocalDate due) {
        super(title, marks, due);
    }

    double applyPenalty(double marks, long daysLate) {
        return marks * Math.max(0, 1 - 0.10 * daysLate);
    }
}

class WrittenAssignment extends Assignment {
    WrittenAssignment(String title, int marks, LocalDate due) {
        super(title, marks, due);
    }

    double applyPenalty(double marks, long daysLate) {
        return marks * Math.max(0, 1 - 0.20 * daysLate);
    }
}

class Question2Student {
    String name;

    Question2Student(String name) {
        this.name = name;
    }
}

class Submission {
    Student student;
    Assignment assignment;
    LocalDate submitDate;
    private String status = "Submitted";

    Submission(Student s, Assignment a, LocalDate date) {
        student = s;
        assignment = a;
        submitDate = date;

        long days = Math.max(0,
                ChronoUnit.DAYS.between(a.dueDate, date));

        System.out.println(s.name + "'s submission for '"
                + a.title + "' received (" +
                (days == 0 ? "on time" : days + " days late")
                + "). Status: " + status + ".");
    }

    void grade(int marks) {
        if (status.equals("Graded")) {
            System.out.println("Cannot resubmit: '"
                    + assignment.title + "' has already been graded.");
            return;
        }

        long days = Math.max(0,
                ChronoUnit.DAYS.between(
                        assignment.dueDate, submitDate));

        double finalMarks = assignment.applyPenalty(marks, days);
        finalMarks = Math.min(assignment.maxMarks,
                Math.max(0, finalMarks));

        status = "Graded";

        System.out.printf("%s graded: %.0f/%d",
                student.name, finalMarks, assignment.maxMarks);

        if (days > 0) {
            System.out.printf(" after %.0f%% late penalty",
                    Math.min(100, days *
                            (assignment instanceof CodingAssignment
                                    ? 10 : 20)));
        }

        System.out.println(". Status: " + status + ".");
    }
}

public class Question2 {
    public static void main(String[] args) {
        Assignment a1 = new CodingAssignment(
                "Linked List Lab", 50,
                LocalDate.of(2026, 3, 10));

        Assignment a2 = new WrittenAssignment(
                "Design Essay", 50,
                LocalDate.of(2026, 3, 12));

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        Submission s1 = new Submission(
                asha, a1, LocalDate.of(2026, 3, 10));

        Submission s2 = new Submission(
                ravi, a2, LocalDate.of(2026, 3, 14));

        s1.grade(45);
        s2.grade(40);
        s1.grade(45);
    }
}
