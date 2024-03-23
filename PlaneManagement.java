import java.util.InputMismatchException;
import java.util.Scanner;

public class PlaneManagement {
    static int options;
    private static final int no_of_rows = 4;
    private static final int[] Seats_In_a_Row = {14, 12, 12, 14};
    private static final int[][] costing = {
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180, 180, 180},
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180},
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180},
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180, 180, 180}
    };
    private static int[][] seats;
    private static Ticket[] tickets;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(" Welcome to the Plane Management Application");
        seats = new int[no_of_rows][];
        for (int i = 0; i < no_of_rows; i++) {
            seats[i] = new int[Seats_In_a_Row[i]];
        }
        tickets = new Ticket[no_of_rows * Seats_In_a_Row[0] + Seats_In_a_Row[1] + Seats_In_a_Row[2] + Seats_In_a_Row[3]];
        menu();
    }

    public static void menu() {
        Scanner input = new Scanner(System.in);

        while (true) {
            String line = """
            *******************************************
            *              MENU OPTION                *
            *******************************************
            1) Buy a seat
            2) Cancel a seat
            3) Find first available seat
            4) Show seating plan
            5) Print ticket information and total sales
            6) Search ticket
            0) Quit
            *******************************************
            Enter the option you would like to choose:\s""";
            System.out.print(line);

        try{
            options = input.nextInt();
            switch (options) {
                case 0 -> {
                    System.exit(0);
                }
                case 1 -> buy_seat();
                case 2 -> cancel_seat();
                case 3 -> find_first_available();
                case 4 -> show_seating_plan();
                case 5 -> print_tickets_info();
                case 6 -> search_ticket();
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter a valid number");
            input.nextInt();
        }
    }
}

    public static void buy_seat() {
        System.out.println("Enter the row  (A - D):");
        char Row_Name = scanner.next().charAt(0);

        System.out.println("Enter the seat  number :");
        int seatnumber = scanner.nextInt();
        int row = Row_Name - 'A';
        if (row < 0 || row >= no_of_rows || seatnumber <= 0 || seatnumber > Seats_In_a_Row[row]) {
            System.out.println("Invalid selection.");
            return;
        }
        if (seats[row][seatnumber - 1] == 1) {
            System.out.println("seat" + Row_Name + seatnumber + "is already booked.");
        } else {
            System.out.println("Enter your name: ");
            String name = scanner.next();
            System.out.println("Enter your surname : ");
            String surname = scanner.next();
            System.out.println("Enter your email : ");
            String email = scanner.next();

            Person person = new Person(name, surname, email);
            double price = costing[row][seatnumber - 1];
            Ticket ticket = new Ticket(Row_Name, seatnumber, price, person);
            ticket.save();            tickets[row * Seats_In_a_Row[0] + seatnumber - 1] = ticket;
            seats[row][seatnumber - 1] = 1;


            seats[row][seatnumber - 1] = 1;
            System.out.println("seat " + Row_Name + seatnumber + " booked successfully.");
        }
    }

    public static void cancel_seat() {
        System.out.println("Enter row name (A - D):  ");
        char row_name = scanner.next().charAt(0);
        System.out.println("Enter the seat Number: ");
        int seatNumber = scanner.nextInt();

        int row = row_name - 'A';
        if (row < 0 || row >= no_of_rows || seatNumber <= 0 || seatNumber > Seats_In_a_Row[row]) {
            System.out.println("Invalid Row Name or Seat Number. Try again!");
            return;
        }
        if (seats[row][seatNumber - 1] == 0) {
            System.out.println("seat " + row_name + seatNumber + " is not booked. Please select another seat!");
        }
        else
            //if(seats[row][seatNumber - 1] == 1)
            {
            seats[row][seatNumber - 1] = 0;
            tickets[row * Seats_In_a_Row[0] + seatNumber - 1] = null;
            System.out.println("Seats " + row_name + seatNumber + " booking cancelled successfully!");
        }
    }

    public static void find_first_available() {
        for (int i = 0; i < no_of_rows; i++) {
            for (int j = 0; j < Seats_In_a_Row[i]; j++) {
                if (seats[i][j] == 0) {
                    char rowLetter = (char) ('A' + i);
                    int seatNumber = j + 1;
                    System.out.println("First seat available found: " + rowLetter + seatNumber);
                    return;
                }
            }
        }
        System.out.println("No seats available found. ");
    }

    public static void show_seating_plan() {
        for (int i = 0; i < no_of_rows; i++) {
            for (int j = 0; j < Seats_In_a_Row[i]; j++) {
                if (seats[i][j] == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    public static void print_tickets_info() {
        int total_sales = 0;
        System.out.println("Ticket information");

        for (Ticket ticket : tickets) {
            if (ticket != null) {
                System.out.println("Seat: " + ticket.get_row() + ticket.get_Seat() +
                        " price: £" + ticket.get_Price() +
                        " Person: " + ticket.get_Person().get_name() + " " +
                        ticket.get_Person().get_surname() +
                        " Email: " + ticket.get_Person().get_email());
                total_sales += ticket.get_Price();
            }
        }
        System.out.println("Total Sales: £" + total_sales);
    }
    public static void search_ticket(){
        System.out.println("Enter row letter: ");
        char rowLetter = scanner.next().charAt(0);
        System.out.println("Enter seat number: ");
        int seatNumber = scanner.nextInt();

        int row= rowLetter -'A';
        if (row < 0 || row >= no_of_rows || seatNumber <= 0 || seatNumber > Seats_In_a_Row[row]){
            System.out.println("invalid row or seat number. try again!");
            return;
        }
        if (seats[row][seatNumber - 1] == 1 && tickets[row * Seats_In_a_Row[0] + seatNumber -1] != null){
            Ticket ticket = tickets[row * Seats_In_a_Row[0] + seatNumber -1];
            System.out.println("Ticket and personal information: ");
            System.out.println("Seat: " + ticket.get_row() + ticket.get_Seat() +
                    " price: £" + ticket.get_Price() +
                    " Person: " + ticket.get_Person().get_name() + " " +
                    ticket.get_Person().get_surname() +
                    " Email: " + ticket.get_Person().get_email());
        }else{
            System.out.println("This seat is available.");
        }
    }
}
