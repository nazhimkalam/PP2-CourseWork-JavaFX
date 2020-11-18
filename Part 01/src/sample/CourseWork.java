// w1761265  SE2019281  Mohammed Nazhim Kalam
package sample;
import java.time.LocalDate;
import java.util.*;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.bson.Document;

public class CourseWork extends Application{
    static final int SEATING_CAPACITY = 42;                                              //seating capacity constant variable
    private static final String[] customerNameArray = new String[SEATING_CAPACITY+1];    //array to store the customer details.(Names)

    @Override
    public void start(Stage primaryStage) {
        String[] date = new String[1];                                //this one element array is used to carry the selected date to every method
        List<String> collectLoadedData = new ArrayList<>();           //this list is used to collect and store records when save and load methods are called.
        Button[] button = new Button[SEATING_CAPACITY+1];             //creating the button array with 43 elements from 0 to 42
        List<Integer> disableList = new ArrayList<>();                //creating a list to store the booked seats to disable them later
        String[] seatBookingArray = new String[SEATING_CAPACITY+1];   //array to store the seats which are booked and unbooked as well.
        boolean returnJourney = false;                                //initializing the returnJourney variable to false;

        for (int index = 1; index < (SEATING_CAPACITY + 1); index++) {  //this loop is used to initialize all the values in the seatBookingArray to "unbooked"
            seatBookingArray[index] = "unbooked";                       //adding "unbooked" to all the elements in the array
        }
        for (int index = 1; index < (SEATING_CAPACITY + 1); index++) {  //this loop is used to initialize all the values in the customerNameArray to " " which is empty space
            customerNameArray[index] = " ";                             //adding " " to all the elements in the array
        }

        Scanner input = new Scanner(System.in);              //creating a scanner called input
        System.out.println("    -----------------------------------------------------------------------------------------------------    ");     //displays message
        System.out.println("* * * * * * * * * * * * * * *   T R A I N   B O O K I N G   P R O G R A M   * * * * * * * * * * * * * * * * *");     //displays message
        System.out.println("    ----------------------------------------------------------------------------------------------------   \n");    //displays message
        System.out.print("-->\t\tIf you need to access the return journey (from Badulla to Colombo) menu system please enter \"Y\" or else" +
                " click \n        the enter key to access the (Colombo to Badulla) menu system : ");     //displays message
        String journey = input.nextLine();                //creating journey variable to get the users input
        if(journey.toLowerCase().equals("y")){            //checks if the user has entered "y" or "Y"
            returnJourney = true;                         //this assigns the returnJourney variable to true
            journey = " from Badulla to Colombo";         //assigning the journey to a variable
        }
        else{
            journey = " from Colombo to Badulla";        //assigning the journey to a variable
        }
        menu(seatBookingArray, disableList, button, primaryStage, returnJourney,date, collectLoadedData,journey);     //this calls the menu method
    }
    private void menu(String[] seatBookingArray, List<Integer> disableList, Button[] button, Stage window, boolean returnJourney,
                      String[] date, List<String> collectLoadedData, String journey) {
        System.out.println("\n    ------------------ T R A I N    B O O K I N G    P R O G R A M    M E N U    S Y S T E M ---------------------\n");
        System.out.println("-->\t\tEnter \"A\" to add customer to a seat");                         //displays message
        System.out.println("-->\t\tEnter \"V\" to view all seats");                                 //displays message
        System.out.println("-->\t\tEnter \"E\" to display empty seats");                            //displays message
        System.out.println("-->\t\tEnter \"D\" to delete customer from seat");                      //displays message
        System.out.println("-->\t\tEnter \"F\" to find seat for a given customer name");            //displays message
        System.out.println("-->\t\tEnter \"S\" to store program data into a file");                 //displays message
        System.out.println("-->\t\tEnter \"L\" to load program data from file ");                   //displays message
        System.out.println("-->\t\tEnter \"R\" to delete all the records stored in the database");  //displays message
        System.out.println("-->\t\tEnter \"O\" to view seats ordered alphabetically by name");      //displays message
        System.out.println("-->\t\tEnter \"Q\" to exit the program");                               //displays message

        Scanner input = new Scanner(System.in);            //creating a new scanner variable
        System.out.print("\n>>> \tEnter option : ");       //displays message
        String customerOption = input.nextLine();          //gets the users input and saves it ot the variable customerOption
        switch (customerOption.toLowerCase()) {
            case ("a"):                                    //checks if the user has entered "a" or "A" and proceed
                addingCustomerToSeat(seatBookingArray,disableList,button,window,returnJourney,date,collectLoadedData,journey);  //calls addCustomerToSeat method
                break;
            case ("v"):
                viewingSeats(seatBookingArray,disableList,button,window,returnJourney,date,collectLoadedData,journey);          //calls viewSeats method
                break;
            case ("e"):
                displayingEmptySeats(seatBookingArray,disableList,button,window,returnJourney,date,collectLoadedData,journey);  //calls displayEmptySeats method
                break;
            case ("d"):
                deletingCustomerFromSeat(seatBookingArray,disableList,button,window,returnJourney, collectLoadedData,journey);  //calls deleteCustomerFromSeat method
                break;
            case ("f"):
                findingSeatByCustomerName(seatBookingArray,disableList,button,window,returnJourney,date,collectLoadedData,journey);  //calls findSeatByCustomerName method
                break;
            case ("s"):
                storingDataToFile(seatBookingArray,disableList,button,window,returnJourney,date,collectLoadedData,journey);  //calls storeDataToFile method
                break;
            case ("l"):
                loadingDataFromFile(seatBookingArray,disableList,button,window,returnJourney,date,collectLoadedData,journey); //calls load method
                break;
            case ("o"):
                orderingSeats(seatBookingArray,disableList,button,window,returnJourney,date,collectLoadedData,journey);  //calls viewOrderedSeats method
                break;
            case ("r"):
                resettingDatabase(seatBookingArray,disableList,button,window,returnJourney,date,collectLoadedData,journey);  //calls resetDatabase method
                break;
            case ("q"):
                System.out.println("Thanks for visiting Denuwara Express...\nProgram exiting...\n");                    //displays message
                System.exit(0);                                                                                  //exits the entire program
            default:
                System.out.println("\nYou have entered an incorrect option...\nPlease enter a correct option from (A,V,E,D,F,S,L,R,O,Q) only...\n\n");  //displays message
                menu(seatBookingArray, disableList,button,window,returnJourney, date, collectLoadedData, journey);      //calls menu method
        }
    }

    private void resettingDatabase(String[] seatBookingArray, List<Integer> disableList, Button[] button, Stage window,
                                   boolean returnJourney, String[] date, List<String> collectLoadedData, String journey){
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);         //creates a new mongo client
            if (!returnJourney) {                                                              //checks if its not the return journey
                DB database = mongoClient.getDB("CourseWorkDB");                     //creates a new database
                DBCollection CustomerNameCollection = database.getCollection("CustomerNameCollection");  //creates a new collection for the seatBookingArray
                CustomerNameCollection.drop();                                                 //clears all the records stored in this collection
                System.out.println("Database successfully cleared");
                menu(seatBookingArray, disableList, button, window, false, date, collectLoadedData, journey); //calls the menu method
            } else {
                DB databaseReturn = mongoClient.getDB("CourseWorkDBReturn"); //creates a new database to get data from a database
                DBCollection CustomerNameCollectionForReturn = databaseReturn.getCollection("CustomerNameCollectionForReturn");  //creates a new collection to get data from a collection
                CustomerNameCollectionForReturn.drop();                                    //clears all the records stored in this collection
                System.out.println("Database successfully cleared");
                menu(seatBookingArray, disableList, button, window, true, date, collectLoadedData, journey); //calls the menu method
            }
        }catch (Exception e){
            System.out.println("Something went wrong when resetting");                                  //displays message if an error occurs
        }
    }

    private void loadingDataFromFile(String[] seatBookingArray, List<Integer> disableList, Button[] button, Stage window,
                                     boolean returnJourney, String[] date, List<String> collectLoadedData, String journey) {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);                //creates a new mongoClient
            if (!returnJourney) {                                                                     //checks if it's not the return journey
                MongoDatabase database = mongoClient.getDatabase("CourseWorkDB");      //creates a new database
                MongoCollection<Document> CustomerNameCollection = database.getCollection("CustomerNameCollection");  //creates a new collection for the seatBookingArray
                collectLoadedData.clear();                                 //empties this list therefore it gets the new set (updated) set of records.
                for (Document dataSet : CustomerNameCollection.find()) {
                    collectLoadedData.add(String.valueOf(dataSet));       //gets all the records stored from the collection and puts it into this list
                }
                System.out.println("Data successfully loaded");
                menu(seatBookingArray, disableList, button, window, false, date, collectLoadedData, journey); //calls the menu method
            } else {
                MongoDatabase databaseReturn = mongoClient.getDatabase("CourseWorkDBReturn");      //creates a new database to get data from a database
                MongoCollection<Document> CustomerNameCollectionForReturn = databaseReturn.getCollection("CustomerNameCollectionForReturn");  //creates a new collection to get data from a collection
                collectLoadedData.clear();                                        //empties this list therefore it gets the new set (updated) set of records.
                for (Document dataSet : CustomerNameCollectionForReturn.find()) {
                    collectLoadedData.add(String.valueOf(dataSet));               //gets all the records stored from the collection and puts it into this list
                }
                System.out.println("Data successfully loaded");
                menu(seatBookingArray, disableList, button, window, true, date, collectLoadedData, journey); //calling the menu method
            }
        }catch (Exception e){
            System.out.println("Something went wrong when loading data");    //displays message
        }
    }

    private void storingDataToFile(String[] seatBookingArray, List<Integer> disableList, Button[] button, Stage window,
                                   boolean returnJourney, String[] date, List<String> collectLoadedData, String journey) {
        try {
            int listSize = collectLoadedData.size();      //gets the size of this list
            int loopIndex = 0;                            //this variable is used when updating the collectLoadedData list.
            while (loopIndex < listSize) {                //this section is used to remove duplicated dates, so it can update the new date
                String[] NewDocumentArray = collectLoadedData.get(loopIndex).split(",");      //this array contains each document data in the form of an array
                NewDocumentArray[1] = NewDocumentArray[1].substring(6, 16);                          //date extracted in the position 1
                if (NewDocumentArray[1].equals(date[0])) {                                           //checks if the list has a record with this date
                    collectLoadedData.remove(collectLoadedData.get(loopIndex));                      //removes the record with this date
                } else {
                    loopIndex++;                          //updates the variable if date is not found to move to the next position in the list.
                }
                listSize = collectLoadedData.size();      //gets the size of the list
            }
            MongoClient mongoClient = new MongoClient("localhost", 27017);               //creates a new mongo client
            if (!returnJourney) {                                                                    //if not return journey
                DB database = mongoClient.getDB("CourseWorkDB");                           //creates a new database
                DBCollection CustomerNameCollection = database.getCollection("CustomerNameCollection");
                BasicDBObject CurrentDocument = new BasicDBObject();                  //creates a new object to store details related to the customerName
                CustomerNameCollection.drop();                                        //clears the data in this collection
                for (String data : collectLoadedData) {
                    CurrentDocument.clear();                                   //deletes the content in this document
                    String[] split = data.split(",");                   //splits the data by ","
                    split[SEATING_CAPACITY + 1] = split[SEATING_CAPACITY + 1].replace("}}", "");      //removes }}
                    for (int index = 1; index < (SEATING_CAPACITY + 2); index++) {
                        String[] innerSplit = split[index].split("=");                                            //splits data from "="
                        innerSplit[0] = innerSplit[0].trim();                                                           //used to remove excess space
                        CurrentDocument.append(innerSplit[0], innerSplit[1]);                       //appends the date and its content to the document
                    }
                    CustomerNameCollection.insert(CurrentDocument);                                 //insert the document into the collection
                }
                CurrentDocument.clear();                                                            //clears the document
                CurrentDocument.append("Date", date[0]);                                       //appends the selected date
                for (int index = 1; index < (SEATING_CAPACITY + 1); index++) {                      //looping through the array
                    CurrentDocument.append(String.valueOf(index), customerNameArray[index]);        //updates the selected date contents
                }
                CustomerNameCollection.insert(CurrentDocument);                                     //inserts the document into the collection
                System.out.println("Data successfully stored into the database");
                menu(seatBookingArray, disableList, button, window, false, date, collectLoadedData, journey);     //calls the menu method
            }
            else {                                        //if its a return journey
                DB databaseReturn = mongoClient.getDB("CourseWorkDBReturn");        //creates a new database
                DBCollection CustomerNameCollectionReturn = databaseReturn.getCollection("CustomerNameCollectionForReturn");
                BasicDBObject CurrentDocumentReturn = new BasicDBObject();                   //creates a new object to store details related to the customerName
                CustomerNameCollectionReturn.drop();                                         //clears the data in this collection
                for (String returnData : collectLoadedData) {
                    CurrentDocumentReturn.clear();                                           //clears the data in this document
                    String[] splitReturn = returnData.split(",");                     //splits the data bt ","
                    splitReturn[SEATING_CAPACITY + 1] = splitReturn[SEATING_CAPACITY + 1].replace("}}", "");      //removes }}
                    for (int index = 1; index < (SEATING_CAPACITY + 2); index++) {
                        String[] innerSplitReturn = splitReturn[index].split("=");                               //splits the data by "="
                        innerSplitReturn[0] = innerSplitReturn[0].trim();                                               //used to remove excess space
                        CurrentDocumentReturn.append(innerSplitReturn[0], innerSplitReturn[1]);      //appends the date and it's contents into the document
                    }
                    CustomerNameCollectionReturn.insert(CurrentDocumentReturn);                      //inserts the document into the collection
                }
                CurrentDocumentReturn.clear();                                         //clears the data in this document
                CurrentDocumentReturn.append("Date", date[0]);                   //appends the date into the document
                for (int index = 1; index < (SEATING_CAPACITY + 1); index++) {        //looping through the array
                    CurrentDocumentReturn.append(String.valueOf(index), customerNameArray[index]);   //appends the position and the customer name into the document
                }
                CustomerNameCollectionReturn.insert(CurrentDocumentReturn);                          //inserts the document into the collection
                System.out.println("Data successfully stored into the database");
                menu(seatBookingArray, disableList, button, window, true, date, collectLoadedData, journey);     //calls the menu method
            }
        }catch (Exception e) {
            System.out.println("Something went wrong when saving data");                                //displays message if an exception happens
        }
    }

    private void orderingSeats(String[] seatBookingArray, List<Integer> disableList, Button[] button, Stage window,
                               boolean returnJourney, String[] date, List<String> collectLoadedData, String journey) {
        Scanner input = new Scanner(System.in);                                                            //creates input scanner
        System.out.print("Enter a date in order to view the seats in alphabetical order by name: ");       //displays message
        String getDate = input.nextLine();                                                                //gets the users input
        while (!getDate.matches("[0-9-]+")) {                                                      //used to validate users input
            System.out.println("\n  - You have entered an invalid input");                                //displays error message
            System.out.print("-->\tEnter a date in order to view the seats in alphabetical order by name:  ");
            getDate = input.nextLine();                                                                   //gets the users input
        }
        boolean dateFound = false;
        for(String records : collectLoadedData){                                   //loop used to check the records in the list
            String[] split = extractDate(records);                                 //calls the extract method and it returns an array of string type
            if(getDate.equals(split[1])) {                                         //checks if the entered date is equal to the extracted date
                dateFound = true;
                for (int index = 2; index < (SEATING_CAPACITY + 2); index++) {
                    String[] innerDocumentArray = split[index].split("=");                           //splitting each element of the document
                    innerDocumentArray[0] = innerDocumentArray[0].replace(" ", "");     //we remove unnecessary spaces
                    customerNameArray[Integer.parseInt(innerDocumentArray[0])] = innerDocumentArray[1];    //adds the customer name to the customer array
                }
            }
        }
        if(dateFound) {
            String[] customerNameSeatArray = new String[SEATING_CAPACITY + 1];     //This array is used to store the names with seat numbers for the bubble sort
            for (int index = 1; index < (SEATING_CAPACITY + 1); index++) {         //looping
                customerNameSeatArray[index] = customerNameArray[index] + " seat number " + index + " booked on " + getDate + journey;    //initializing customerNameSeatArray
            }
            List<String> orderedMethod = new ArrayList<>();               //this list is currently empty and this will be used to store the final ordered outcome.
            int loopTimes = SEATING_CAPACITY - 1;                        //decrementing the looping times by one
            for (int outerIndex = 1; outerIndex < (SEATING_CAPACITY); outerIndex++) {                      //outer bubble sort loop
                for (int innerIndex = 1; innerIndex <= (loopTimes); innerIndex++) {                              //inner bubble sort loop
                    if ((int) customerNameSeatArray[innerIndex].charAt(0) > (int) customerNameSeatArray[innerIndex + 1].charAt(0)) {    //compares with the first character ascii value
                        String temp = customerNameSeatArray[innerIndex];                             //moves the content to a temp variable
                        customerNameSeatArray[innerIndex] = customerNameSeatArray[innerIndex + 1];  // shifting data from one index to the other
                        customerNameSeatArray[innerIndex + 1] = temp;                              //the contents of the temp variable is brought to the new position in the array
                    }
                }
                loopTimes -= 1;                    //decrementing the loopTimes by 1 always after the loop
            }
            for (int index = 1; index < (SEATING_CAPACITY + 1); index++) {
                if ((int) customerNameSeatArray[index].charAt(0) != 32) {     //checking if the first character is a space or not
                    orderedMethod.add(customerNameSeatArray[index]);          //adds the data to the orderedMethod list
                }
            }
            for (String content : orderedMethod) {
                System.out.println(content);                                //displays the contents of the orderedMethod List
            }
        }
        else{
            System.out.println("\nSorry there are no booking details found for the date you have entered " + getDate);   //displays message if date not found
        }
        menu(seatBookingArray, disableList,button,window,returnJourney, date, collectLoadedData, journey);             //calls the  menu method
    }

    private void deletingCustomerFromSeat(String[] seatBookingArray, List<Integer> disableList, Button[] button, Stage window,
                                          boolean returnJourney, List<String> collectLoadedData, String journey) {
        Scanner input = new Scanner(System.in);                                            //creates a scanner input
        String[] deletedDate = new String[1];                                             //array to store the deleted date for storing purposes
        System.out.println("Date format is year-month-day eg: 2015-12-14");               //displays message
        System.out.print("Please enter the date you wish to delete the customer from: "); //displays message
        String dateEntered = input.nextLine();                                            //gets the users input
        while (!dateEntered.matches("[0-9-]+")) {                                  //validates users input data
            System.out.println("\n  - You have entered an invalid input");
            System.out.print("-->\tPlease enter the date you wish to delete the customer from:  ");
            dateEntered = input.nextLine();                                              //gets the users input
        }
        System.out.print("Please enter customer name in order to delete the seat: ");   //displays message
        String name = input.nextLine();                                                 //gets the users input and stores it in a variable
        while (!name.matches("[a-z A-Z 0-9_]+")) {                               //validates users input
            System.out.println("\n  - You have entered an invalid input");
            System.out.print("-->\tPlease enter customer name in order to delete the seat: ");
            name = input.nextLine();                                                     //gets the users input
        }
        boolean dateFound = false;
        boolean nameFound = false;
        for(String records : collectLoadedData){                                            //loops through the collected records
            String[] split = extractDate(records);                                          //calls the extract method and it returns an array of string type
            if(split[1].equals(dateEntered)){                                               //checks if the user entered date is equal to the extracted date
                dateFound = true;
                for(int index = 2; index<(SEATING_CAPACITY+2); index++){
                    String[] innerSplit = split[index].split("=");            //splits the data by "="
                    innerSplit[0] = innerSplit[0].trim();                            //this is used to remove unwanted white spaces
                    if(innerSplit[1].equals(name.toLowerCase())){                    //checks if the entered name is equal to the extracted name
                        nameFound = true;
                        deletedDate[0] = dateEntered;                                    //assigns the date entered by the user to the deletedData array
                        seatBookingArray[Integer.parseInt(innerSplit[0])] = "unbooked";  //sets the value of that index position to "unbooked"
                        customerNameArray[Integer.parseInt(innerSplit[0])] = " ";        //set the value of that index position to an empty space
                        disableList.remove(Integer.valueOf(innerSplit[0]));              //removes the index value or seat number from the list
                        System.out.println(name + " with the seat number " + innerSplit[0] +
                                " has been successfully deleted on this date, "+dateEntered + " for the journey" + journey);  //displays message
                    }
                }
            }
        }
        if(!dateFound){                                                                                               //checks if the entered date is not found
            System.out.println("\nSorry the date you have entered "+dateEntered+" cannot be found from the database");  //displays message
        }
        else if (!nameFound){                                                                                         //checks if the entered name is not found
            System.out.println("\nSorry the customer "+ name + " has not booked a seat on this date");                  //displays message
        }
        menu(seatBookingArray, disableList,button,window,returnJourney, deletedDate, collectLoadedData, journey);     //calls the display menu method
    }

    private void findingSeatByCustomerName(String[] seatBookingArray, List<Integer> disableList, Button[] button,
                                           Stage window, boolean returnJourney, String[] date, List<String> collectLoadedData, String journey) {
        Scanner input = new Scanner(System.in);                                                         //creates scanner
        System.out.print("Please enter customer name: ");                                               //display message
        String name = input.nextLine();                                                                 //gets the users input and stores it in a variable
        while (!name.matches("[A-Z a-z 0-9_]+")) {                                               //validates users input
            System.out.println("\n  - You have entered an invalid input");
            System.out.print("-->\tPlease enter customer name: ");
            name = input.nextLine();                                                                    //gets the users input and stores it in a variable
        }
        String notBooked = " didn't book a seat therefore there is no seat number found.\n";           //variable declaring
        boolean found = false;                                                                         //variable declaring
        for(String records : collectLoadedData){                                                     //loops through the collected data from load
            String[] split = extractDate(records);                                                  //calls the extract method and it returns an array of string type
            for(int index=2; index<(SEATING_CAPACITY+2); index++){
                String[] innerDocumentArray = split[index].split("=");                       //splitting each element of the document
                innerDocumentArray[0] = innerDocumentArray[0].replace(" ","");  //we remove unnecessary spaces
                if(name.toLowerCase().equals(innerDocumentArray[1])){                   //checks if the entered name is equal to the extracted name from the list
                    found=true;
                    System.out.println(name + "'s seat number is equal to " + innerDocumentArray[0] +
                            " this was booked for " + split[1] + journey);  //displays message
                }
            }
        }
        if (!found){                                                //checks if the entered name by the user us not found
            System.out.println("\n" + name+notBooked);                     //displays the message not booked if customer name is not found from the list
        }
        menu(seatBookingArray, disableList,button,window,returnJourney, date, collectLoadedData, journey);      //call the display menu method
    }

    private DatePicker datePicker(){                                                          //datePicker Method
        DatePicker datePicker = new DatePicker();                                            //creating a datePicker
        datePicker.setOnMouseDragEntered(event -> datePicker.setCursor(Cursor.HAND));       //used to create the hand cursor
        datePicker.getEditor().setDisable(true);                                            //used to disable the past dates
        datePicker.setDayCellFactory(picker -> new DateCell() {           //this part disables the old dates in the datePicker
            public void updateItem(LocalDate date, boolean empty) {       //used to update the datePicker with the current date
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        return datePicker;                                                //returns the datePicker
    }

    private void displayingEmptySeats(String[] seatBookingArray, List<Integer> disableList, Button[] button,
                                   Stage window, boolean returnJourney, String[] date, List<String> collectLoadedData, String journey) {
        Button greenSeatSample = new Button();                                //this is the example for an unbooked seat
        greenSeatSample.setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png'); -fx-background-radius:40;");   //setting style
        greenSeatSample.setMinSize(45,45);                      //setting the size of the button
        greenSeatSample.setLayoutY(220);                                             //setting the position
        greenSeatSample.setLayoutX(550);                                             //setting the position

        Label dateLabel = new Label("Date : ");                                //adding date
        dateLabel.setLayoutY(120);                                                  //setting the position for the label
        dateLabel.setLayoutX(550);                                                  //setting the position for the label
        dateLabel.setStyle("-fx-font-size:20px; -fx-font-weight: bold; -fx-font-style:italic;");           //setting style

        DatePicker datePicker = datePicker();                              //getting the datePicker from the method datePicker and returning the value
        datePicker.setLayoutX(550);                                        //setting the position for the datePicker
        datePicker.setLayoutY(160);                                        //setting the position for the datePicker

        Button close = new Button("CLOSE");                           //creating a close button.
        close.setStyle("-fx-background-radius:20; -fx-border-radius:20; -fx-border-color:black;");
        close.setLayoutX(800);                                             //setting the x position for the close button
        close.setLayoutY(500);                                             //setting the y position for the close button

        Label greenSeat = new Label(" represents EMPTY SEAT");                                    // creating a label
        greenSeat.setLayoutX(600);                                                                     //setting the x position for the green label
        greenSeat.setLayoutY(225);                                                                     //setting the y position for the green label
        greenSeat.setStyle("-fx-text-fill:green; -fx-font-size:18px; -fx-font-style: italic;");        //setting styles

        Label heading = new Label("EMPTY SEATS");                                // creating a label
        heading.setLayoutX(420);                                                      //setting the x position for the close button
        heading.setStyle("-fx-font-size:30px; -fx-font-style: italic;");              //setting style

        GridPane gridPane = new GridPane();                                          // creating a gridPane
        int colIndex = 0;                                                            // variable to store the column number when creating buttons
        int rowIndex = 0;                                                            // variable to store the row number when creating buttons
        int rowLoop = 0;                                                             // variable to store the number of loops when creating buttons for the rows
        createSeats(button,gridPane,colIndex,rowIndex,rowLoop);                      //calling the createSeats method
        String[] checkDate = new String[1];                             //creating a single element array to store the date selected by the user on the datePicker

        datePicker.setOnAction(event -> {
            List<String> datesDoneForBookings = new ArrayList<>();      //used to get the dates where booking has taken place
            LocalDate dateSelected = datePicker.getValue();             //gets the date selected by the user on the datePicker
            checkDate[0] = String.valueOf(dateSelected);                //stores the selected date into the single element array
            for(String records:collectLoadedData){                      //used to access all the records in this list
                String [] split = extractDate(records);                 //calls the extract method and it returns an array of string type
                datesDoneForBookings.add(split[1]);                     //adds the extracted date into the list
                if(checkDate[0].equals(split[1])){                      //checks if the selected date is equal to the extracted date
                    disableList.clear();                                //checks for the seat if equal it disables them
                    for(int index=2; index<(SEATING_CAPACITY+2); index++) {
                        String[] innerSplit = split[index].split("=");                         //splitting the data from "="
                        innerSplit[0] = innerSplit[0].trim();                                         //used to remove excess space;
                        if(!innerSplit[1].equals(" ")){                                               //checks if the extracted customer name is not a space
                            seatBookingArray[Integer.parseInt(innerSplit[0])] = "booked";             //sets the value of that index position to "unbooked"
                            customerNameArray[Integer.parseInt(innerSplit[0])] = innerSplit[1];       //set the value of that index position with the extracted customer name
                            disableList.add(Integer.parseInt(innerSplit[0]));                         //adds the seat number to the disable list
                        }
                        else{
                            seatBookingArray[Integer.parseInt(innerSplit[0])] = "unbooked";       //sets the value of that index position to "unbooked"
                            customerNameArray[Integer.parseInt(innerSplit[0])] = " ";             //set the value of that index position to an empty space
                        }
                    }
                    for(int NewIndex = 1; NewIndex<(SEATING_CAPACITY+1); NewIndex++){       //This loop gives the colour to the seat depending on the seatBookingArray Value
                        if (seatBookingArray[NewIndex].equals("booked")) {                  //checks if the array position value is equal to "booked"
                           button[NewIndex].setVisible(false);                              //makes the buttons invisible
                        }
                        else{
                            button[NewIndex].setVisible(true);                             //makes the buttons visible
                            button[NewIndex].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png'); -fx-background-radius:30;");
                        }
                    }
                }
            }
            if(!datesDoneForBookings.contains(checkDate[0])){                       //this is for the other seats which aren't booked at all
                disableList.clear();                                                //clears the list
                for(int index=1; index<(SEATING_CAPACITY+1); index++){
                    seatBookingArray[index] = "unbooked";                           //sets the value of that index position to "unbooked"
                    customerNameArray[index] = " ";                                 //set the value of that index position to an empty space
                    button[index].setVisible(true);                                 //makes the buttons visible
                    button[index].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png'); -fx-background-radius:30;");
                }
            }
        });
        for(int index = 1; index<(SEATING_CAPACITY+1); index++){          // looping
            if (seatBookingArray[index].equals("booked")){                //checking if the seatBooking array elements are set to "booked"
                button[index].setVisible(false);                          //if the seat is booked then I disable the particular button
            }
            else{
                button[index].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png');  -fx-background-radius:30;"); //adding pic for seat
            }
        }
        List<Integer> tempBookList = new ArrayList<>();                       //this is used to store the buttons which are currently selected
        close.setOnAction(event -> {
            closeProgram(window,seatBookingArray,disableList,button,returnJourney,tempBookList, date, collectLoadedData, journey);      //calling the closeProgram method
        });
        AnchorPane anchorPane = new AnchorPane();   //creating an anchor pane
        anchorPane.setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/backgroundThree.jpg');");     //background styling
        anchorPane.getChildren().addAll(gridPane,dateLabel,datePicker,greenSeatSample,close,heading,greenSeat);                //adding the nodes to the anchorPane
        gridPaneLayout(gridPane,anchorPane,window,seatBookingArray,disableList,button,returnJourney, tempBookList, date, collectLoadedData, journey);   //calling the gridPane Layout
    }

    private String[] extractDate(String records){
        String[] split = records.split(",");                                                                    //used to split the data by ","
        split[1] = split[1].substring(6,16);                                                                           //date extracted
        split[SEATING_CAPACITY + 1] = split[SEATING_CAPACITY + 1].replace("}}","");                 //used to remove }}
        return split;                                                                                                  //returns the split array
    }

    private void viewingSeats(String[] seatBookingArray, List<Integer> disableList, Button[] button, Stage window, boolean returnJourney,
                           String[] date, List<String> collectLoadedData, String journey) {
        Button greenSeatSample = new Button();                                //this is the example for an unbooked seat
        greenSeatSample.setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png'); -fx-background-radius:40;");
        greenSeatSample.setMinSize(45,45);                //setting the size of the button
        greenSeatSample.setLayoutY(255);                                       //setting the position
        greenSeatSample.setLayoutX(575);                                       //setting the position

        Button redSeatSample = new Button();                                   //this is the example for a booked seat
        redSeatSample.setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/redSeat.png'); -fx-background-radius:40;");
        redSeatSample.setMinSize(45,45);                 //setting the size of the button
        redSeatSample.setLayoutY(305);                                        //setting the position
        redSeatSample.setLayoutX(575);                                        //setting the position

        Label dateLabel = new Label("Date : ");                           //date label
        dateLabel.setLayoutY(120);                                             //setting the position for the label
        dateLabel.setLayoutX(620);                                             //setting the position for the label
        dateLabel.setStyle("-fx-font-size:20px; -fx-font-weight: bold; -fx-font-style:italic;");

        DatePicker datePicker = datePicker();           //getting the datePicker from the method datePicker and returning the value
        datePicker.setLayoutX(620);                     //setting the position for the datePicker
        datePicker.setLayoutY(160);                     //setting the position for the datePicker

        Button close = new Button("CLOSE");         //creating a close button
        close.setStyle("-fx-background-radius:20; -fx-border-radius:20; -fx-border-color:black;");
        close.setLayoutX(820);                           //setting the x position for the close button
        close.setLayoutY(480);                           //setting the y position for the close button

        Label greenSeat = new Label(" represents UNBOOKED SEAT");                                  //creating a new label
        greenSeat.setLayoutX(620);                                                                      //setting the x coordinates
        greenSeat.setLayoutY(260);                                                                      //setting the y coordinates
        greenSeat.setStyle("-fx-text-fill:green; -fx-font-size:18px; -fx-font-style: italic;");         //setting style

        Label redSeat = new Label(" represents BOOKED SEAT");                                     //creating a new label
        redSeat.setLayoutX(620);                                                                       //setting the x coordinates
        redSeat.setLayoutY(310);                                                                       //setting the y coordinates
        redSeat.setStyle("-fx-text-fill:red; -fx-font-size:18px; -fx-font-style: italic;");            //setting style

        Label heading = new Label("VIEWING SEATS");                           //creating label heading
        heading.setLayoutX(400);                                                   //setting the x position
        heading.setStyle("-fx-font-size:30px; -fx-font-style: italic;");           //setting style

        GridPane gridPane = new GridPane();                                     //crating a gridPane
        int colIndex = 0;                                                       // variable to store the column number when creating buttons
        int rowIndex = 0;                                                       // variable to store the row number when creating buttons
        int rowLoop = 0;                                                        // variable to store the number of loops when creating buttons for the rows
        createSeats(button,gridPane,colIndex,rowIndex,rowLoop);                 //calling the create method
        String[] checkDate = new String [1];                                    //creating a single element array to store the date selected by the user on the datePicker

        datePicker.setOnAction(event -> {
            List<String> datesDoneForBookings = new ArrayList<>();         //creates a list to get the dates booked
            LocalDate dateSelected = datePicker.getValue();                //gets the date selected by the user on the datePicker
            checkDate[0] = String.valueOf(dateSelected);                   //stores the selected date into the single element array
            for(String records:collectLoadedData){                         //used to access all the records in this list
                String [] split = extractDate(records);                    //calls the extract method and it returns an array of string type
                datesDoneForBookings.add(split[1]);                        //adds the extracted date into the list
                if(checkDate[0].equals(split[1])){                         //checks if the selected date is equal to the extracted date
                    disableList.clear();
                    for(int index=2; index<(SEATING_CAPACITY+2); index++) {
                        String[] innerSplit = split[index].split("=");                       //splitting the data from "="
                        innerSplit[0] = innerSplit[0].trim();                                       //used to remove excess space
                        if(!innerSplit[1].equals(" ")){
                            seatBookingArray[Integer.parseInt(innerSplit[0])] = "booked";           //sets the value of that index position to "unbooked"
                            customerNameArray[Integer.parseInt(innerSplit[0])] = innerSplit[1];     //set the value of that index position with the extracted customer name
                            disableList.add(Integer.parseInt(innerSplit[0]));                       //adds the seat number to the disable list
                        }
                        else{
                            seatBookingArray[Integer.parseInt(innerSplit[0])] = "unbooked";         //sets the value of that index position to "unbooked"
                            customerNameArray[Integer.parseInt(innerSplit[0])] = " ";               //set the value of that index position to an empty space
                        }
                    }
                    for(int NewIndex = 1; NewIndex<(SEATING_CAPACITY+1); NewIndex++){     //This loop gives the colour to the seat depending on the seatBookingArray Value
                        if (seatBookingArray[NewIndex].equals("unbooked")) {              //checks if that position element is equal to "unbooked"
                            button[NewIndex].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png'); -fx-background-radius:30;"); //green seat
                        }
                        if (seatBookingArray[NewIndex].equals("booked")){                //checks if that position element is equal to "booked"
                            button[NewIndex].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/redSeat.png'); -fx-background-radius:30;");  //red seat
                        }
                    }
                }
            }
            if(!datesDoneForBookings.contains(checkDate[0])){
                disableList.clear();                                         //this is used to clear the list
                for(int index=1; index<(SEATING_CAPACITY+1); index++){
                    seatBookingArray[index] = "unbooked";                   //checks if that position element is equal to "unbooekd"
                    customerNameArray[index] = " ";                         //set the value of that index position to an empty space
                    button[index].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png'); -fx-background-radius:30;"); //green seat
                }
            }
        });
        for(int index = 1; index<(SEATING_CAPACITY+1); index++){               //looping
            if (seatBookingArray[index].equals("booked")){                     //checking if each seat in the array is booked or not.
                button[index].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/redSeat.png'); -fx-background-radius:30;"); //if booked seat turns red
            }
            else{
                button[index].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png'); -fx-background-radius:30;"); //remains green seat if unbooked
            }
        }
        List<Integer> tempBookList = new ArrayList<>();                    //this is used to store the buttons which are currently selected
        close.setOnAction(event -> {
            closeProgram(window,seatBookingArray, disableList,button,returnJourney,tempBookList, date, collectLoadedData, journey);  //calling the closeProgram method
        });
        AnchorPane anchorPane = new AnchorPane();                          //creating the anchorPane
        anchorPane.setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/backgroundTwo.jpg');");    //setting style
        anchorPane.getChildren().addAll(gridPane,close,heading,greenSeat,redSeat,greenSeatSample,redSeatSample,dateLabel,datePicker);       //adding the nodes to the anchorPane
        gridPaneLayout(gridPane,anchorPane,window,seatBookingArray, disableList,button,returnJourney, tempBookList,date,collectLoadedData, journey); //calling the gridPaneLayout method
    }

    private void gridPaneLayout(GridPane gridPane, AnchorPane anchorPane, Stage window, String[] seatBookingArray,
                                List<Integer> disableList, Button[] button, boolean returnJourney, List<Integer> tempBookList,
                                String[] date, List<String> collectLoadedData, String journey) {
        gridPane.setLayoutY(80);                                               //setting the x position for the gridPane
        gridPane.setLayoutX(160);                                              //setting the y position for the gridPane
        gridPane.setHgap(20);                                                  //Setting the Grid alignment gridPane.
        gridPane.setVgap(5);                                                   //Setting the Grid alignment gridPane.
        Scene scene = new Scene(anchorPane,1000,650);            //creating a scene
        window.setTitle("Train booking system");                               //setting title for the stage
        window.setScene(scene);                                                //setting the scene to the window
        window.show();                                                         //showing the stage
        window.setOnCloseRequest(event -> {
            event.consume();                                                   //from this point the closeProgram method will handle the controls
            closeProgram(window,seatBookingArray, disableList,button,returnJourney,tempBookList,date,collectLoadedData, journey);    //calling the closeProgram method
        });
    }
    private void createSeats(Button[] button, GridPane gridPane, int colIndex, int rowIndex, int rowLoop) {             //creating seats method
        for(int index = 1; index<(SEATING_CAPACITY+1); index++){                     //looping
            String seatNumber;
            if (index<=9){                                                    //checks if the index is less than 9
                seatNumber = "0"+(index);                                     //setting the seating values properly
            }
            else{
                seatNumber = ""+(index);                                          //setting the seating values properly
            }
            button[index] = new Button(seatNumber);                               //creating a new button with the seat button
            gridPane.add(button[index],colIndex,rowIndex);                        //adding button to the gridPane
            colIndex++;                                                           //incrementing the column index
            if (colIndex==2){                                                     //checks if colIndex is equal to 2 and then increments it by 2
                colIndex+=2;
            }
            rowLoop++;                                               //incrementing the column index
            if(rowLoop==4){                                          //checks if colIndex is equal to 2 and then does the changes required
                colIndex=0;
                rowLoop=0;
                rowIndex++;
            }
            button[index].setMinSize(45,45);    //setting the minimum width and height for the buttons
        }
    }
    private void addingCustomerToSeat(String[] seatBookingArray, List<Integer> disableList, Button[] button, Stage window,
                                   boolean returnJourney, String[] date, List<String> collectLoadedData, String journey) {
        Label details = new Label("\t-------------------IMPORTANT NOTE-------------------\n\n\t" +
                "* Firstly select a date from the date picker, then select seat/s ,\n\tthen enter name and finally click book.\n\t "+
                "* If you forget or don't wish to enter your name then the \n\tcustomer name will be set to \" cus_ \" + seat number you \n\tbooked.");
        details.setLayoutY(310);                            //setting the position according to the coordinates
        details.setLayoutX(500);                            //setting the position according to the coordinates
        details.setStyle("-fx-font-size:15px; -fx-font-weight:bold; -fx-font-style:italic;");            //setting style

        Button greenSeatSample = new Button();               //this is the example for an unbooked seat
        greenSeatSample.setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png'); -fx-background-radius:40;"); //setting button image
        greenSeatSample.setMinSize(45,45);
        greenSeatSample.setLayoutY(490);                     //setting the position
        greenSeatSample.setLayoutX(600);                    //setting the position

        Button redSeatSample = new Button();                             //this is the example for a booked seat
        redSeatSample.setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/redSeat.png'); -fx-background-radius:40;"); //setting button image
        redSeatSample.setMinSize(45,45);            //setting size
        redSeatSample.setLayoutY(540);                                   //setting the position
        redSeatSample.setLayoutX(600);                                    //setting the position

        Label dateLabel = new Label("Date : ");                      //adding date
        dateLabel.setLayoutY(220);                                        //setting the position for the label
        dateLabel.setLayoutX(550);                                        //setting the position for the label
        dateLabel.setStyle("-fx-font-size:20px; -fx-font-weight: bold; -fx-font-style:italic;");

        DatePicker datePicker = datePicker();               //getting the datePicker from the method datePicker and returning the value
        datePicker.setLayoutX(550);                         //setting the position for the datePicker
        datePicker.setLayoutY(260);                         //setting the position for the datePicker

        Button book = new Button("BOOK");             //created a book button
        book.setStyle("-fx-background-radius:20; -fx-border-radius:20; -fx-border-color:black;");
        book.setLayoutX(740);                               //setting the position according to the coordinates
        book.setLayoutY(170);                               //setting the position according to the coordinates

        Button close = new Button("CLOSE");
        close.setStyle("-fx-background-radius:20; -fx-border-radius:20; -fx-border-color:black;");
        close.setLayoutX(810);                              //setting the position according to the coordinates
        close.setLayoutY(170);                              //setting the position according to the coordinates

        TextField CusNameTxtField = new TextField();        //customer name txt field
        CusNameTxtField.setStyle("-fx-background-radius:20; -fx-border-radius:20;");
        CusNameTxtField.setLayoutX(550);                    //setting the position according to the coordinates
        CusNameTxtField.setLayoutY(170);                    //setting the position according to the coordinates

        Label greenSeat = new Label("represents UNBOOKED SEAT");
        greenSeat.setLayoutX(650);                              //setting the position according to the coordinates
        greenSeat.setLayoutY(500);                               //setting the position according to the coordinates
        greenSeat.setStyle("-fx-text-fill:green; -fx-font-size:18px; -fx-font-style: italic;");         //setting style

        Label redSeat = new Label("represents BOOKED SEAT");
        redSeat.setLayoutX(650);                                    //setting the position according to the coordinates
        redSeat.setLayoutY(550);                                    //setting the position according to the coordinates
        redSeat.setStyle("-fx-text-fill:red; -fx-font-size:18px; -fx-font-style: italic;");         //setting style

        Label CusNameLabel = new Label("Enter Customer Name : ");
        CusNameLabel.setLayoutX(550);                               //setting the position according to the coordinates
        CusNameLabel.setStyle("-fx-font-size:20px; -fx-font-weight: bold; -fx-font-style:italic;");
        CusNameLabel.setLayoutY(120);                                //setting the position according to the coordinates

        Label heading = new Label("ADDING CUSTOMER TO SEAT");        //creating a label
        heading.setLayoutX(320);                                          //setting the position according to the coordinates
        heading.setStyle("-fx-font-size:30px; -fx-font-style: italic;");

        GridPane gridPane = new GridPane();              //pane for the buttons
        int columnIndex = 0;                             //declaring variable
        int rowIndex = 0;                                //declaring variable
        int rowLoop = 0;                                //declaring variable
        String seatNumber;                               //declaring variable
        int[] storingTheBookedSeatPositionNumbers = new int[SEATING_CAPACITY+1];         //storing the button values booked temporally

        for(int index=1; index<(SEATING_CAPACITY+1); index++){
            storingTheBookedSeatPositionNumbers[index] = 0;                             // assigns all the elements in the array to 0
        }
        List<Integer> tempBookList = new ArrayList<>();                                 //creating a tempBookList List this is used when user cancels the program
        String[] checkDate ={"null"};                                //creating a single element array to store the date selected by the user on the datePicker
        for(int index = 1; index<(SEATING_CAPACITY+1); index++){    //This section is all about creating buttons for the add customer part
            if (index<=9){
                seatNumber = "0"+(index);
            }
            else{
                seatNumber = ""+(index);
            }
            button[index] = new Button(seatNumber);                              //create new button with the seat number
            button[index].setPadding(new Insets(10));         //Button padding space
            gridPane.add(button[index],columnIndex,rowIndex);                   //adding nodes to gridPane
            columnIndex++;                                                      //incrementing
            if (columnIndex==2){                                                //checks the condition and changes the value of colIndex
                columnIndex+=2;
            }
            rowLoop++;
            if(rowLoop==4){                                                     //checks the condition and changes the value of the variables
                columnIndex=0;
                rowLoop=0;
                rowIndex++;
            }
            button[index].setMinSize(45,45);                       //making the size of the button
            int finalIndex = index;
            button[index].setOnAction(event -> {
                if(checkDate[0].equals("null")){                                         //check if date is null("when user doesn't select a date but selects a seat")
                    Alert informationBox = new Alert(Alert.AlertType.INFORMATION);      //creating a information box
                    informationBox.setHeaderText("Please select a date from the date picker");      //Alert box displayed with these messages
                    informationBox.setTitle("Error");                                               //title of the information box
                    informationBox.showAndWait();                                                   //displays the alert box
                }
                else{
                    setOnActionButtons(seatBookingArray,button,finalIndex,storingTheBookedSeatPositionNumbers,tempBookList);   //calls the method
                }
            });
        }
        datePicker.setOnAction(event -> {
            List<String> datesDoneForBookings = new ArrayList<>();
            LocalDate dateSelected = datePicker.getValue();                 //gets the date selected by the user on the datePicker
            checkDate[0] = String.valueOf(dateSelected);                    //stores the selected date into the single element array
            for(String records:collectLoadedData){                          //used to access all the records in this list
                String [] split = extractDate(records);                     //calls the extract method and it returns an array of string type
                datesDoneForBookings.add(split[1]);                         //adds the extracted date into the list
                if(checkDate[0].equals(split[1])){                          //checks if the selected date is equal to the extracted date
                    for (int integer : disableList) {                       //loop to disable the selected button
                        button[integer].setDisable(false);                  //makes the seat visible
                    }
                    disableList.clear();                                            //clears the list
                    for(int index=2; index<(SEATING_CAPACITY+2); index++) {
                        String[] innerSplit = split[index].split("=");       //splitting the data from "="
                        innerSplit[0] = innerSplit[0].trim();                       //used to remove excess space
                        if(!innerSplit[1].equals(" ")){
                            seatBookingArray[Integer.parseInt(innerSplit[0])] = "booked";          //sets the value of that index position to "unbooked"
                            customerNameArray[Integer.parseInt(innerSplit[0])] = innerSplit[1];    //set the value of that index position with the extracted customer name
                            disableList.add(Integer.parseInt(innerSplit[0]));                      //adds the seat number to the disable list
                        }
                        else{
                            seatBookingArray[Integer.parseInt(innerSplit[0])] = "unbooked";          //checks if that position element is equal to "unbooekd"
                            customerNameArray[Integer.parseInt(innerSplit[0])] = " ";                //set the value of that index position to an empty space
                        }
                    }
                    for (int integer : disableList) {                                              //loop to disable the selected button
                        button[integer].setDisable(true);
                    }
                    for(int NewIndex = 1; NewIndex<(SEATING_CAPACITY+1); NewIndex++){   //This loop gives the colour to the seat depending on the seatBookingArray Value
                        if (seatBookingArray[NewIndex].equals("unbooked")) {            //checks if that position element is equal to "unbooked"
                            button[NewIndex].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png'); -fx-background-radius:30;"); //green seat
                        }
                        if (seatBookingArray[NewIndex].equals("booked")){               //checks if that position element is equal to "booked"
                            button[NewIndex].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/redSeat.png'); -fx-background-radius:30;");  //red seat
                        }
                    }
                }
            }
            if(!datesDoneForBookings.contains(checkDate[0])){                              //for the seats which aren't booked at all for other dates
                for(int integer : disableList){
                    button[integer].setDisable(false);                                     //makes the seats visible
                }
                disableList.clear();
                for(int index=1; index<(SEATING_CAPACITY+1); index++){
                    seatBookingArray[index] = "unbooked";                                 //checks if that position element is equal to "unbooekd"
                    customerNameArray[index] = " ";                                       //set the value of that index position to an empty space
                    button[index].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png');  -fx-background-radius:30;");
                }
            }
        });
        for (Integer integer : disableList) {                                            //loop to disable the selected button
            button[integer].setDisable(true);
        }
        for(int NewIndex = 1; NewIndex<(SEATING_CAPACITY+1); NewIndex++){            //This loop gives the colour to the seat depending on the seatBookingArray Value
            if (seatBookingArray[NewIndex].equals("unbooked")) {                     //checks if that position element is equal to "unbooked"
                button[NewIndex].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png');  -fx-background-radius:30;"); //green seat
            }
            if (seatBookingArray[NewIndex].equals("booked")){                       //checks if that position element is equal to "booked"
                button[NewIndex].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/redSeat.png');  -fx-background-radius:30;");  //red seat
            }
        }
        book.setOnAction(event -> {                                                             //actions happen when book button is clicked
            Alert informationBox = new Alert(Alert.AlertType.INFORMATION);                      //creating a information box
            String customerName = CusNameTxtField.getText();                                    //takes the customer name entered in the test field
            customerName = customerName.toLowerCase();                                          //converts the customer to lower case
            if(checkDate[0].equals("null")){                                                    //if the user didn't select a date from the datePicker when book is hit
                informationBox.setHeaderText("Please select a date from the date picker");      //Alert box displayed with these messages
                informationBox.setTitle("Error");
                informationBox.showAndWait();                                                    //displays the alert box
            }
            else {
                if (!customerName.equals("")) {                                                   //checks if customer text field is not empty
                    if (!customerName.matches("[a-z A-Z]+")) {                             //loops when the customer name is not in alphabetical order
                        informationBox.setHeaderText("You have entered an invalid name\nNOTE that customer name should not contain numerical values \n" +
                                "or other special characters such as !,@,#,$,%,^,&,*,(,),/,-,+ etc..\nPlease re-enter name  ");     //texts inside the information box
                        informationBox.setTitle("Error");                                       //setting a title to the information box
                        informationBox.showAndWait();                                           //displays the alert box
                    } else {
                        bookMethod(customerName, storingTheBookedSeatPositionNumbers, disableList, tempBookList, window,
                                seatBookingArray, button, returnJourney, event,checkDate,collectLoadedData,journey);
                    }                                                                             //calls the bookMethod
                } else {
                    bookMethod(customerName, storingTheBookedSeatPositionNumbers, disableList, tempBookList, window,
                            seatBookingArray, button, returnJourney, event, checkDate, collectLoadedData, journey);                     //calls the bookMethod
                }
            }
        });
        close.setOnAction(event -> {
            closeProgram(window,seatBookingArray,disableList,button,returnJourney,tempBookList,date,collectLoadedData,journey);         //calling the closeProgram method
        });
        AnchorPane anchorPane = new AnchorPane(); //creating an anchorPane
        anchorPane.setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/backgroundOne.jpg');"); //setting style
        anchorPane.getChildren().addAll(gridPane,greenSeatSample,dateLabel,datePicker,redSeatSample,CusNameLabel,close,CusNameTxtField,details,heading,book,greenSeat,redSeat); //adding nodes to the anchorPane
        gridPaneLayout(gridPane,anchorPane,window,seatBookingArray, disableList,button,returnJourney,tempBookList,
                date, collectLoadedData,journey);                                                                           //calling gridPaneLayout method
    }

    private void bookMethod(String customerName, int[] storingTheBookedSeatPositionNumbers, List<Integer> disableList, List<Integer> tempBookList,
                            Stage window, String[] seatBookingArray, Button[] button, boolean returnJourney, ActionEvent event, String[] checkDate,
                            List<String> collectLoadedData, String journey){
        customerName = customerName.trim();
        for (int index = 1; index < (SEATING_CAPACITY + 1); index++) {                   //looping
            if (storingTheBookedSeatPositionNumbers[index] != 0) {                       //like this we will know which seats are selected cuz when seats are selected x[array] won't be equal to zero.
                disableList.add(storingTheBookedSeatPositionNumbers[index]);             //adding the selected seats position or number to the list
            }
        }
        for (int seatArrayLoop = 1; seatArrayLoop < (SEATING_CAPACITY + 1); seatArrayLoop++) {
            if (seatArrayLoop == storingTheBookedSeatPositionNumbers[seatArrayLoop]) {
                if (customerName.equals("")) {                                      //this is when the customer doesn't enter a name so a default name will be created.
                    customerNameArray[seatArrayLoop] = "cus_" + seatArrayLoop;      //adding the default created customer name into the array
                } else {
                    customerNameArray[seatArrayLoop] = customerName;                //adding the entered customer name into the array
                }
            }
        }
        if (tempBookList.size() == 0) {                                                 //checks if tempBookList size is equal to 0
            event.consume();                                                            //consumes the event meaning that the rest of the program will take care of the alert functions.
            Alert confirmationBox = new Alert(Alert.AlertType.CONFIRMATION);            //creating a confirmation box
            confirmationBox.setTitle("Train booking system");                           //setting a title to the confirmation box
            confirmationBox.setHeaderText("Seems like you didn't book any seat, click yes to exit");           //setting a header to the confirmation box
            ButtonType yes = new ButtonType("Yes");                                                      //yes button
            ButtonType no = new ButtonType("No");                                                        //no button
            confirmationBox.getButtonTypes().setAll(yes, no);
            Optional<ButtonType> result = confirmationBox.showAndWait();         //displays the alert boxes
            if (result.get() == yes) {                                           //if the user clicks yes from the confirm box the rest of the code is executed
                window.close();                                                  //closes the stage
                menu(seatBookingArray, disableList, button, window, returnJourney, checkDate, collectLoadedData, journey);              //calls the menu method
            }
        }
        else {
            window.close();                                                                                             //closes the stage
            menu(seatBookingArray, disableList, button, window, returnJourney, checkDate, collectLoadedData, journey);  //calls the menu
        }
    }
    private void closeProgram(Stage window, String[] seatBookingArray, List<Integer> disableList, Button[] button,
                              boolean returnJourney, List<Integer> tempBookList, String[] date, List<String> collectLoadedData, String journey){
        Alert confirmationBox = new Alert(Alert.AlertType.CONFIRMATION);                     //creating the confirmation box
        confirmationBox.setTitle("Train booking system");                                    //setting title for the confirmation box
        confirmationBox.setHeaderText("Are you sure you want to close ?");                   //setting header for the confirmation box
        ButtonType yes = new ButtonType("Yes");                                         //creating the button YES
        ButtonType no = new ButtonType("No");                                           //creating the button NO
        confirmationBox.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = confirmationBox.showAndWait();               //displays the confirmation box
        if (result.get() == yes){                                                  //checks if the user has clicked YES
            for (Integer integer : tempBookList) {                                 //looping
                seatBookingArray[integer] = "unbooked";                            //when close is clicked for what ever the seats were booked will get unbooked
            }
            window.close();                                                                                              //closes the window
            menu(seatBookingArray, disableList,button,window, returnJourney, date, collectLoadedData, journey);          //calls the menu
        }
    }

    private void setOnActionButtons(String[] seatBookingArray, Button[] button, int finalIndex,
                                    int[] storingTheBookedSeatPositionNumbers, List<Integer> tempBookList) {
        for(int otherSeatColor = 1; otherSeatColor<(SEATING_CAPACITY+1); otherSeatColor++) {
            if (otherSeatColor == finalIndex) {                                   //checks if the finalI value is equal to the variable otherSeatColor
                seatBookingArray[otherSeatColor] = "booked";                      //since its clicked it will be assigned to booked on that button position
                storingTheBookedSeatPositionNumbers[otherSeatColor] = finalIndex; //this is used to store the position of the booked seat to disable later
                tempBookList.add(finalIndex);                                     //adds to the tempBookList for the close option purpose
            }

            if (seatBookingArray[otherSeatColor].equals("booked")){                 //checks if the buttons in the button array are booked
                button[otherSeatColor].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/redSeat.png');  -fx-background-radius:30;");
            }                                                                       //if booked then the button image is replaced with a red// image

            if (otherSeatColor!=finalIndex && seatBookingArray[otherSeatColor].equals("unbooked")){
                button[otherSeatColor].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png');  -fx-background-radius:30;");
            }                                                                   //if booked then the button image is replaced with a green image

        }
        button[finalIndex].setOnAction(event1 -> {                              //this part is to turn the button to green again when the user clicks on the button again
            button[finalIndex].setStyle("-fx-background-image: url('File:/C:/Users/Nazhim/Desktop/pp2_course_work/src/sample/greenSeat.png');  -fx-background-radius:30;"); //turns the seat to green
            seatBookingArray[finalIndex] = "unbooked";                         //makes the seat unbooked
            storingTheBookedSeatPositionNumbers[finalIndex] = 0;               // removes it from this array
            for(int index=0; index<tempBookList.size(); index++){
                //this loop is used to remove that index clicked from the tempBookList which will be used for the close program option
                if(tempBookList.get(index)==finalIndex) tempBookList.remove(index);
            }
            button[finalIndex].setOnAction(event2 -> {                        //calls the method again and again when user keeps clicking the button
                setOnActionButtons(seatBookingArray,button,finalIndex,storingTheBookedSeatPositionNumbers,tempBookList);
            });
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}