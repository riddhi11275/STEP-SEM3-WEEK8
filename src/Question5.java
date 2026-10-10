import java.util.*;

interface NotificationChannel {
    void send(String student, String title);
}

class EmailChannel implements NotificationChannel {
    public void send(String student, String title) {
        System.out.println("[Email → " + student + "] " + title);
    }
}

class SmsChannel implements NotificationChannel {
    public void send(String student, String title) {
        System.out.println("[SMS → " + student + "] " + title);
    }
}

class AppChannel implements NotificationChannel {
    public void send(String student, String title) {
        System.out.println("[App → " + student + "] " + title);
    }
}

class Q5Student {
    String name, department;
    List<NotificationChannel> channels = new ArrayList<>();

    Q5Student(String name, String department) {
        this.name = name;
        this.department = department;
    }

    void addChannel(NotificationChannel channel) {
        channels.add(channel);
    }
}

class Notice {
    String title;
    Set<String> departments;

    Notice(String title, String... departments) {
        this.title = title;
        this.departments = new HashSet<>(
                Arrays.asList(departments));
    }
}

class NoticeBoard {
    List<Q5Student> students = new ArrayList<>();

    void addStudent(Q5Student student) {
        students.add(student);
    }

    void postNotice(Notice notice) {
        if (notice.title == null || notice.title.trim().isEmpty()) {
            System.out.println("Cannot post notice: Title is required.");
            return;
        }

        if (notice.departments.isEmpty()) {
            System.out.println(
                    "Cannot post notice: At least one target department is required.");
            return;
        }

        System.out.println("Notice '" + notice.title
                + "' posted to "
                + String.join(", ", notice.departments) + ".");

        for (Q5Student student : students) {
            if (notice.departments.contains(student.department)) {
                for (NotificationChannel channel : student.channels) {
                    channel.send(student.name, notice.title);
                }
            }
        }
    }
}

public class Q5NoticeBroadcaster {
    public static void main(String[] args) {
        NoticeBoard board = new NoticeBoard();

        Q5Student asha = new Q5Student("Asha", "CSE");
        asha.addChannel(new EmailChannel());
        asha.addChannel(new AppChannel());

        Q5Student ravi = new Q5Student("Ravi", "ECE");
        ravi.addChannel(new SmsChannel());

        board.addStudent(asha);
        board.addStudent(ravi);

        board.postNotice(new Notice("Lab Closed Tomorrow", "CSE"));
        board.postNotice(new Notice(
                "Fee Deadline Extended", "CSE", "ECE"));
        board.postNotice(new Notice("Sports Day"));
    }
}
