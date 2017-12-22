import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Theater{
    static int rows = 10, cols = 19;
    //Create theater
    static int [][] seating = new int[rows][cols+1];
    //Creates array for row priority in order to maximize customer satisfaction
    static int [][] rowPriority = {{9, 20},
                                 {8, 20},
                                 {6, 20},
                                 {5, 20},
                                 {3, 20},
                                 {2, 20},
                                 {1, 20},
                                 {4, 20},
                                 {7, 20},
                                 {10, 20}};
    //Array created to get row letters for writing to the file
    static char [] rowLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    static int count = 0;
    
    public static List<Object> assignSeat(int seats){
        List<Object> resSeats = new ArrayList<Object>();
        int thisRow = 0, j = 1, tRow = 0;
        //Figure out which row has seating available in priority of row
        for (int x = 0; x < rows; x++){
            if (seats > rowPriority[x][1] || rowPriority[x][1] == 0)
                j++;
        }
        //Get row to place people in
        for(int x = 0; x < rows; x++){
            if (rowPriority[x][0] == j)
                thisRow = x;
        }
        //Mark seats with people as full and decrease amount of seats available in row
        for (int y = cols; seats > 0; y--){
            if (seating[thisRow][y] == 0){
                seating[thisRow][y] = 1;
                resSeats.add(rowLetters[thisRow] + String.valueOf(y+1));
                rowPriority[thisRow][1] -= 1;
                seats--;
            }
        }  
        return resSeats;
    }
    
    //Open and read file input if possible
    public static void main(String[] args){
        File file = new File(args[0]);
        String reserves, resNo = "";
        List<Object> reserveSeats = new ArrayList<Object>();

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            while ((reserves = br.readLine()) != null){
                int row = 0;
                String answer = "";
                String[] reservations = reserves.split("\\s+");
                resNo = reservations[0];
                boolean isFirst = true;
                int seatNo = Integer.parseInt(reservations[1]);
                count = count + seatNo;

                // If number of people is more than capacity, inform reservation request
                if (count > 200){
                    reserveSeats.add(resNo + " Sorry, we do not have enough space for the number of people in your party. Please try the next showing!");
                    count = count - seatNo;
                }
                //Obtain list of seats assigned for the reservation
                else{
                    if (seatNo > 20){
                        if (seatNo%10 != 0)
                            row += 1;
                        for (int x = 0; x < ((seatNo/10)); x++){
                            answer += "," + assignSeat(10).toString().replace("[", "").replace("]","").replace(" ", "").trim();
                        }
                        answer += "," + assignSeat(seatNo%10).toString().replace("[", "").replace("]","").replace(" ", "").trim();
                        reserveSeats.add(resNo + " " + answer.substring(1));
                    }

                    else {
                        reserveSeats.add(resNo + " " + assignSeat(seatNo).toString().replace("[", "").replace("]","").replace(" ", "").trim());
                    }
                }
            }
        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file '" + file + "'");
        } catch(IOException ex){
            System.out.println("Error reading file '" + file + "'");
        }
        //Write reservation seats to output.txt
        try {
            FileWriter fw = new FileWriter("output.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Object obj: reserveSeats){
                bw.write(obj.toString());
                bw.newLine();
            }
            bw.close();
        } catch(IOException ex){
            System.out.println("Error writing to file 'output.txt'");
        }
    }
}