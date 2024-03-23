import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(char row,int seat, double price,Person person){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }
    public char get_row(){
        return row;
    }
    public void set_row(char row){
        this.row= row;
    }
    public int get_Seat(){
        return seat;
    }
    public void set_Seat(int seat){
        this.seat = seat;
    }
    public double get_Price(){
        return price;
    }
    public void set_Price(double price){
        this.price = price;
    }
    public Person get_Person(){
        return person;
    }
    public void set_person(Person person){
        this.person =  person;
    }
    public void save(){
        String filename = row + Integer.toString(seat) + ".txt";
        try(PrintWriter writer = new PrintWriter(new FileWriter(filename))){
            writer.println("Seat: "+row + seat);
            writer.println("Price: £" + price);
            writer.println("person: " + person.get_name() + " " + person.get_surname());
            writer.println("Email: " + person.get_email());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

