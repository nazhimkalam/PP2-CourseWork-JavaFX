// w1761265  SE2019281  Mohammed Nazhim Kalam
package sample;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static sample.TrainStation.SEATING_CAPACITY;
import static sample.TrainStation.waitingArrayIndex;

public class PassengerQueue {
    private Passenger[] queueArray = new Passenger[SEATING_CAPACITY];      //this is an array with the passengers
    private int first,last;                                                //the first about to leave and last person entered in the queue
    private int maxStayInQueue,minStayInQueue;                             //the maximum and minimum time spent in the queue
    private double averageStayInQueue;                                     //records the average time spent by a passenger in the queue.
    private int maxLength,size;                                            //maximum length of the queue attained and the size of the queue
    private int arrayPositionIndex = -1;                                   //queue array position
    public List<Integer> updatedPassengersInQueue = new ArrayList<>();     //create a list to get the updated Passengers In Queue

    public int getArrayPositionIndex(){                                                                        //getter for arrayPositionIndex
        return arrayPositionIndex;
    }

    public void setArrayPositionIndex(int value){                                                              //setter for setArrayPositionIndex
        arrayPositionIndex = value;
    }

    public int getMinStayInQueue() {                                                                            //getter for minStayInQueue
        return minStayInQueue;
    }

    public double getAverageStayInQueue(){                                                                      //getter for averageStayInQueue
        return averageStayInQueue;
    }


    public void setQueueArray(Passenger passenger,int position){                                                //setter for setQueueArray
        queueArray[position] = passenger;
    }

    public Passenger[] getQueueArray(){                                                                         //getter for queueArray
        return queueArray;
    }

    public void setFirst(int first){                                                                            //setter for setFirst
        this.first = first;
    }

    public int getFirst(){                                                                                      //getter for first
        return first;
    }

    public void setLast(int last){                                                                              //setter for setLast
        this.last = last;
    }

    public int getLast(){                                                                                       //getter for last
        return last;
    }

    public void setSize(int size){                                                                              //setter for setSize
        this.size = size;
    }

    public int getSize(){                                                                                       //getter for size
        return size;
    }


    public void add(Passenger passenger){            //adding the passenger to the queue
        if(!isFull()) {                              //if not full we can add elements
            queueArray[last] = passenger;            //at start rear=0, and we add data to it
            last = (last + 1)%SEATING_CAPACITY;      //updating rear value for circular array (we start from position one)
            size++;                                  //when data entered size also increases by one
            arrayPositionIndex++;                    //position of the element added in the queueArray
        }
    }

    public Passenger remove(){                               //this is used to remove the passenger from the queue
        Passenger removedPassenger = queueArray[first];      //we get the data from the queue at the front position
        if(!isEmpty()) {                                     //if not empty we can remove data
            first = (first + 1) % SEATING_CAPACITY;          //updating front value for circular array
            size = size - 1;                                 //after removing the size of the array will also reduce by 1
        }
        return removedPassenger;                             //if the removed data is needed to be used in the program
    }

    public void deletingFromQueue(String firstName, String surname, int seatNumber){
        boolean valid = false;
        for (int index = 0; index < size; index++) {                                                   //loops the queueArray elements
            if (queueArray[(first + index) % SEATING_CAPACITY].getFirstName().equals(firstName)) {     //checks if the firstName entered is equal
                if (queueArray[(first + index) % SEATING_CAPACITY].getSurname().equals(surname)) {     //checks if the surname entered is equal
                    if (queueArray[(first + index) % SEATING_CAPACITY].getSeatNumber() == seatNumber) {     //checks if the seatNumber entered is equal
                        System.out.println("\nPassenger is successfully deleted from the train queue\n");   //displays message
                        System.out.println("Details of the deleted passenger includes : ");
                        System.out.println("----------------------------------------------------------");
                        System.out.println("\t First name: " + queueArray[(first + index) % SEATING_CAPACITY].getFirstName());
                        System.out.println("\t Surname:" + queueArray[(first + index) % SEATING_CAPACITY].getSurname());
                        System.out.println("\t Seat number booked : " + queueArray[(first + index) % SEATING_CAPACITY].getSeatNumber());
                        for (int count = index; count < size; count++) {           //reordering the queueArray and pushing the deleted element to the last position
                            if (count != (size - 1)) {                             //if it's not the last element
                                queueArray[(first + count) % SEATING_CAPACITY] = queueArray[(first + count  + 1) % SEATING_CAPACITY];
                            }
                        }
                        if(maxLength==size){              //checks if the maxLength was calculated with the deleted item
                            maxLength--;                  //decrements the max length
                        }
                        size--;                           //reduces the size by one
                        last = arrayPositionIndex;        //make new last position
                        arrayPositionIndex--;             //decrements the array index
                        valid = true;
                        waitingArrayIndex--;              //this is to say that a passenger is removed to the number of people in total has to reduce by one
                        setUpdatedPassengersInQueue();    //calls the setUpdatedPassengerInQueue
                    }
                }
            }
        }
            if (!valid) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("\tThere is no passenger in the queue with the given details above");
            }
    }

    public void setMaxLength(int length){          //we set the max length of the queue, when adding passengers, we call this method to set the size accordingly.
        if(length>maxLength) {                     //checks if the length is greater than the max length and changes accordingly
            maxLength = length;
        }
    }

    public int getMaxLength(){  //getting the maximum length of the queue
        return maxLength;
    }

    public void setMaxStayInQueueAndSetMinStayInQueue(){
        List<Integer> times = new ArrayList<>();                                       //gets all the times of the passengers in the train queue and boarded ones.
        double total = 0;
        for (int index = 0; index <= arrayPositionIndex; index++) {                    // loops to add the waiting times of the passengers into a list
            times.add(queueArray[index].getSecondsInQueue());
            total += queueArray[index].getSecondsInQueue();                            //adds the time for average calculation
        }
        if(times.size()!=0) {
            maxStayInQueue = Collections.max(times);                                       //get the maximum time in the list
            minStayInQueue = Collections.min(times);                                       //get the minimum time in the list
            double average = Math.round(total / (arrayPositionIndex + 1) * 100);             //gets the average time for each passenger into 2dp
            averageStayInQueue = average / 100;                                              //sets the average value
        }
    }

    public int getMaxStayInQueue(){   //getting the total max size of the queue
        return maxStayInQueue;
    }

    public boolean isEmpty(){      //this method returns "true" if the size is equal to 0
        return getSize() == 0;
    }

    public boolean isFull(){              //this method returns "true" if the size is equal to 42
        return getSize() == SEATING_CAPACITY;
    }

    public String display(){                                    //this method is used to display the content into the text file
        StringBuilder stringBuilder = new StringBuilder();
        for(int index = 0; index<=arrayPositionIndex; index++){
            stringBuilder.append("  *Passenger ").append(index+1).append("\n").append("   -First Name: " + queueArray[index].getFirstName() + "\n");
            stringBuilder.append("   -Surname:").append(queueArray[index].getSurname()).append("\n").append("   -Waiting time in queue: " + queueArray[index].getSecondsInQueue() + "s\n");
            stringBuilder.append("   -Seat Number: ").append(queueArray[index].getSeatNumber()).append("\n\n");

        }
        return stringBuilder.toString();                   //returns the string
    }

    public void setUpdatedPassengersInQueue(){             // this method is used to get the seatNumber which represents the passengers in the train queue currently
        try {
            updatedPassengersInQueue.clear();              //clears the list
            for (int index = 0; index < size; index++) {   //loops and add the seat number into the list
                updatedPassengersInQueue.add(queueArray[(first + index) % SEATING_CAPACITY].getSeatNumber());
            }
            setMaxLength(updatedPassengersInQueue.size());    //calls this method to set the size
            setMaxStayInQueueAndSetMinStayInQueue();          //calls this method to set the times
        }catch (Exception e){
            System.out.println("Something went wrong when updating the updatedPassengersInQueue list");
        }
    }

    public List<Integer> getUpdatedPassengersInQueue(){
        return updatedPassengersInQueue;
    }

    public String getFirstName(int seatNumber){
        String FirstNameCustomer = "";              //this method is used to send the firstName only to the train queue in view method
        for(int count = 0; count<size; count++){
            if(queueArray[(first+count)%SEATING_CAPACITY].getSeatNumber()==seatNumber){
                FirstNameCustomer = queueArray[(first+count)%SEATING_CAPACITY].getFirstName();  //extracts the first name of the passenger and send
            }
        }
        return  FirstNameCustomer;                    //returns the first name
    }
}
