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
    static int [][] rowPriority = {{10, 20},
                                 {9, 20},
                                 {7, 20},
                                 {5, 20},
                                 {3, 20},
                                 {2, 20},
                                 {1, 20},
                                 {4, 20},
                                 {6, 20},
                                 {8, 20}};
    //Array created to get row letters for writing to the file
    static char [] rowLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    
    public static List<Object> assignSeat(int seats){
        List<Object> resSeats = new ArrayList<Object>();
        int thisRow = 0, j = 1;
        for (int x = 0; x < rows; x++){
            if (seats > rowPriority[x][1] || rowPriority[x][1] == 0){
                j++;
            }
        }
        for(int x = 0; x < rows; x++){
            if (rowPriority[x][0] == j)
                thisRow = x;
        }
        for (int y = cols; seats > 0; y--){
            if (seats == 0){
                for (int i = 0; i < rows; i++)
                    rowPriority[i][0] -= 1;
                return(resSeats);
            }
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
                int count = 0, row = 0;
                String answer = "";
                String[] reservations = reserves.split("\\s+");
                resNo = reservations[0];
                boolean isFirst = true;
                int seatNo = Integer.parseInt(reservations[1]);
                
                count = count + seatNo;
                // If number of people is more than capacity, inform reservation request
                if (count > 200){
                    System.out.println("Sorry, we do not have enough space for the number of people in your party. Please try the next showing!");
                    count = count - seatNo;
                }
                //Obtain list of seats assigned for the reservation
                if (seatNo > 20){
                    if (seatNo%10 != 0)
                        row += 1;
                    for (int x = 0; x < ((seatNo/10)+1); x++){
                        answer += "," + assignSeat(10).toString().replace("[", "").replace("]","").replace(" ", "").trim();
                    }
                    reserveSeats.add(resNo + " " + answer.substring(1));
                }
                
                else {
                    reserveSeats.add(resNo + " " + assignSeat(seatNo).toString().replace("[", "").replace("]","").replace(" ", "").trim());
                }
            }
        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file '" + file + "'");
        } catch(IOException ex){
            System.out.println("Error reading file '" + file + "'");
        }
        try {
            FileWriter fw = new FileWriter("output.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (Object obj: reserveSeats){
                //System.out.println(obj);
                bw.write(obj.toString());
                bw.newLine();
            }
            bw.close();
        } catch(IOException ex){
            System.out.println("Error reading file '" + file + "'");
        }
    }
}