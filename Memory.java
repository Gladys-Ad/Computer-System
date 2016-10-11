
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Memory {

    public static void main(String[] args) throws FileNotFoundException {
        //initialize memory size
        int[] memory = new int[2000];
        int address = 0;
        BufferedReader buffer = null;
        String fileLine = null;
        buffer = new BufferedReader(new FileReader(args[0]));

        try {
            while ((fileLine = buffer.readLine()) != null) {
                if (fileLine.matches("\\d+.*")) {
                    fileLine = fileLine.replaceAll("[^0-9][\\s0-9]*", "");
                    memory[address] = Integer.parseInt(fileLine);
                    address++;
                } else {
                    if (fileLine.matches("\\.\\d+[\\s0-9]*")) {
                        fileLine = fileLine.replaceAll("[^\\.\\d+][\\s0-9]*", "").replace(".", "");
                        address = Integer.parseInt(fileLine);
                    } 
                
                }
            }
        } catch (IOException readException) {
            System.out.println(readException.getMessage());
        }
        int y = 0;
        Scanner sc = new Scanner(System.in);
        int PC = sc.nextInt();

        while (PC != 3000) {
            if (PC >= 0) {
                y = memory[PC];
                System.out.printf("%d\n", y);
            } else if (PC < 0) {
                memory[Math.abs(PC)] = Math.abs(sc.nextInt());
            }

            PC = sc.nextInt();
        }

    }

}
