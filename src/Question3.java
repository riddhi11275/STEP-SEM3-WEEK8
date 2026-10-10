import java.util.*;

abstract class Seat {
    String id;

    Seat(String id) {
        this.id = id;
    }

    abstract int getPrice();
}

class RegularSeat extends Seat {
    RegularSeat(String id) { super(id); }
    int getPrice() { return 150; }
}

class PremiumSeat extends Seat {
    PremiumSeat(String id) { super(id); }
    int getPrice() { return 250; }
}

class ReclinerSeat extends Seat {
    ReclinerSeat(String id) { super(id); }
    int getPrice() { return 400; }
}

class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Show {
    String time;
    Set<String> bookedSeats = new HashSet<>();

    Show(String time) {
        this.time = time;
    }
}

class Booking {
    Customer customer;
    Show show;
    List<Seat> seats;
    boolean cancelled = false;

    Booking(Customer c, Show s, List<Seat> seats) {
        customer = c;
        show = s;
        this.seats = seats;
    }

    int total() {
        int sum = 0;
        for (Seat seat : seats) {
            sum += seat.getPrice();
        }
        return sum;
    }

    void cancel(boolean showStarted) {
        if (cancelled) {
            System.out.println("Booking already cancelled.");
        } else if (showStarted) {
            System.out.println("Cannot cancel after show starts.");
        } else {
            for (Seat seat : seats) {
                show.bookedSeats.remove(seat.id);
            }
            cancelled = true;
            System.out.println(customer.name
                    + "'s booking cancelled. Seats "
                    + seatNames() + " released.");
        }
    }

    String seatNames() {
        List<String> names = new ArrayList<>();
        for (Seat seat : seats) names.add(seat.id);
        return String.join(", ", names);
    }
}

public class Question3{
    static Booking book(Customer c, Show s, Seat... seats) {
        if (seats.length == 0 || seats.length > 6) {
            System.out.println("Booking must have 1 to 6 seats.");
            return null;
        }

        Set<String> requested = new HashSet<>();
        for (Seat seat : seats) {
            if (!requested.add(seat.id)
                    || s.bookedSeats.contains(seat.id)) {
                System.out.println("Seat " + seat.id
                        + " is already booked for this show.");
                return null;
            }
        }

        s.bookedSeats.addAll(requested);
        Booking b = new Booking(c, s, Arrays.asList(seats));

        System.out.println("Booking confirmed for " + c.name
                + ": " + b.seatNames()
                + ". Total: ₹" + String.format("%.2f", b.total()) + ".");
        return b;
    }

    public static void main(String[] args) {
        Show show = new Show("7 PM");

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Booking b1 = book(asha, show,
                new RegularSeat("A1"),
                new RegularSeat("A2"),
                new PremiumSeat("F5"));

        book(ravi, show, new RegularSeat("A2"));
        book(ravi, show, new ReclinerSeat("R1"));

        b1.cancel(false);

        book(neha, show, new RegularSeat("A2"));
    }
}
