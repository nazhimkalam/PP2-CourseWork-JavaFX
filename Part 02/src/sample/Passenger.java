// w1761265  SE2019281  Mohammed Nazhim Kalam
package sample;
public class Passenger {
    private String firstName,surname;                                                //firstName variable and surname variable of string type
    private int secondsInQueue,seatNumber;                                           //waiting time and seatNumber variable of integer type

    public int getSeatNumber(){                                            //getter for seatNumber
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber){                            //setter for the seatNumber
        this.seatNumber = seatNumber;
    }

    public String getFirstName(){                                         //getter for the firstName
        return firstName;
    }

    public String getSurname(){                                           //getter for the surname
        return surname;
    }

    public int getSecondsInQueue(){                                       //getter for the waiting time in queue
        return secondsInQueue;
    }

    public void setFirstName(String firstName){                           //setter for the firstName
        this.firstName = firstName;
    }

    public void setSurname(String surname){                               //setter for the surname
        this.surname = " " + surname.trim();
    }

    public void setSecondsInQueue(int secondsInQueue){                    //setter for the waiting time in queue
        this.secondsInQueue = secondsInQueue;
    }
}
