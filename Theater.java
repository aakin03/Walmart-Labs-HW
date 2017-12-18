import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Theater{
    static int r = 10, c = 20;
    //Create Theater
    static int [][] seating = new int[r][c];
    
    public static int assignSeat(int seats){
        Random rand = new Random();
        int row = rand.nextInt(r) + 1;
        int col = rand.nextInt(c-seats) + 1;
        return col;
    }
    
    //Open and read file input if possible
    public static void main(String[] args){
        File file = new File(args[0]);
        String reserves;
        
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            while ((reserves = br.readLine()) != null){
                String[] reservations = reserves.split("\\s+");
                int resNo = Integer.parseInt(reservations[0]);
                int seats = Integer.parseInt(reservations[1]);
                //Assigns seats to specific reservations
                assignSeat(seats);
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("Unable to open file '" + file + "'");
        }
        catch(IOException ex){
            System.out.println("Error reading file '" + file + "'");
        }
    }
}