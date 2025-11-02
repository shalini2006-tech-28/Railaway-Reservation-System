
public class Ticket {

    private static int pnrCounter = 10000;
    private int pnr;
    private String passengerName;
    private int age;
    private String gender;
    private Train train;
    private int seatNumber;
    private String status;

    public Ticket(String passengerName, int age, String gender, Train train) {
        this.pnr = ++pnrCounter;
        this.passengerName = passengerName;
        this.age = age;
        this.gender = gender;
        this.train = train;
        this.seatNumber = train.getAvailableSeats();
        this.status = "Confirmed";
    }

    public int getPnr() {
        return pnr;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Train getTrain() {
        return train;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PNR: " + pnr
                + "\nPassenger: " + passengerName
                + "\nAge: " + age + " | Gender: " + gender
                + "\nTrain: " + train.getTrainNumber() + " - " + train.getTrainName()
                + "\nRoute: " + train.getSource() + " → " + train.getDestination()
                + "\nSeat Number: " + seatNumber
                + "\nFare: ₹" + train.getFare()
                + "\nStatus: " + status + "\n";
    }
}
