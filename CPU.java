
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class CPU {

    public static void main(String[] args) throws IOException {
        try {
            int PC = 0;
            int SP = 1000;
            int IR = 0;
            int AC = 0;
            int X = 0;
            int Y = 0;
            int op = 0;
            int timer = Integer.parseInt(args[1]);
            boolean interrupt = true;

            //forking
            Runtime runtime = Runtime.getRuntime();

            Process proc = runtime.exec("java Memory " + args[0]);

            InputStream is = proc.getInputStream();
            OutputStream os = proc.getOutputStream();
            Scanner inst = new Scanner(is);
            PrintWriter pw = new PrintWriter(os);

            boolean execute = true;

            while (execute) {
                pw.printf("%d ", PC);
                pw.flush();
                IR = inst.nextInt();

                if (IR != -1) {
                    //Instruction set
                    switch (IR) {
                        case 1:
                            PC++;
                            pw.printf("%d ", PC);
                            pw.flush();
                            AC = inst.nextInt();
                            PC++;
                            break;
                        case 2:
                            PC++;
                            pw.printf("%d ", PC);
                            pw.flush();
                            op = inst.nextInt();
                            if (op > 999) {
                                if (interrupt == true) {
                                    System.out.println("Error! Restricted access at location " + op + " in user mode");
                                    System.exit(0);
                                }
                            }
                            pw.printf("%d ", op);
                            pw.flush();
                            AC = inst.nextInt();
                            PC++;
                            // System.out.println("PC: " + PC);
                            break;
                        case 3:
                            PC++;
                            pw.printf("%d ", PC);
                            pw.flush();
                            pw.printf("%d ", inst.nextInt());
                            pw.flush();
                            pw.printf("%d ", inst.nextInt());
                            pw.flush();
                            AC = inst.nextInt();
                            PC++;
                            break;
                        case 4:
                            PC++;
                            pw.printf("%d ", PC);
                            pw.flush();
                            pw.printf("%d ", (inst.nextInt() + X));
                            pw.flush();
                            AC = inst.nextInt();
                            PC++;
                            break;
                        case 5:
                            PC++;
                            pw.printf("%d ", PC);
                            pw.flush();
                            pw.printf("%d ", (inst.nextInt() + Y));
                            pw.flush();
                            AC = inst.nextInt();
                            PC++;
                            break;
                        case 6:
                            int x = SP + X;
                            pw.printf("%d ", x);
                            pw.flush();
                            AC = inst.nextInt();
                            PC++;
                            break;
                        case 7:
                            PC++;
                            pw.printf("%d ", PC);
                            pw.flush();
                            pw.printf("%d ", ((inst.nextInt())*-1));
                            pw.flush();
                            pw.printf("%d ", (AC*-1));
                            pw.flush();
                            PC++;
                            break;

                        case 8:
                            Random random = new Random();
                            AC = random.nextInt(100 - 1 + 1) + 1;
                            System.out.println("Random number: " + AC);
                            PC++;
                            break;
                        case 9:
                            PC++;
                            pw.printf("%d ", PC);
                            pw.flush();
                            op = inst.nextInt();
                            if (op == 1) {
                                System.out.print(AC);
                            } else if (op == 2) {
                                System.out.print((char) AC);
                            } else {
                                System.out.println("invalid port number");
                            }
                            PC++;
                            break;
                        case 10:
                            AC = AC + X;
                            PC++;
                            break;
                        case 11:
                            AC = AC + Y;
                            PC++;
                            break;
                        case 12:
                            AC = AC - X;
                            PC++;
                            break;
                        case 13:
                            AC = AC - Y;
                            PC++;
                            break;
                        case 14:
                            X = AC;
                            PC++;
                            break;
                        case 15:
                            AC = X;
                            PC++;
                            break;
                        case 16:
                            Y = AC;
                            PC++;
                            break;
                        case 17:
                            AC = Y;
                            PC++;
                            break;
                        case 18:
                            SP = AC;
                            PC++;
                            break;
                        case 19:
                            AC = SP;
                            PC++;
                            break;
                        case 20:
                            PC++;
                            pw.printf("%d ", PC);
                            pw.flush();
                            PC = inst.nextInt();
                            break;
                        case 21:
                            if (AC == 0) {
                                PC++;
                                pw.printf("%d ", PC);
                                pw.flush();
                                PC = inst.nextInt();
                            } else {
                                PC = PC + 2;
                            }
                            break;
                        case 22:
                            if (AC != 0) {
                                PC++;
                                pw.printf("%d ", PC);
                                pw.flush();
                                PC = inst.nextInt();
                            } else {
                                PC = PC + 2;
                            }
                            break;
                        case 23:
                            if (SP > 500) {
                                PC = PC + 2;
                                SP--;
                                pw.printf("%d ", (SP * -1));
                                pw.flush();
                                pw.printf("%d ", (PC * -1));
                                pw.flush();
                                PC = PC - 1;
                                pw.printf("%d ", PC);
                                pw.flush();
                                PC = inst.nextInt();
                                break;
                            } else {
                                System.out.println("Cannot perform call!");
                                proc.destroy();
                                System.exit(1);
                            }
                        case 24:
                            pw.printf("%d ", SP);
                            pw.flush();
                            PC = inst.nextInt();
                            SP++;
                            break;

                        case 25:
                            X++;
                            PC++;
                            break;
                        case 26:
                            X--;
                            PC++;
                            break;
                        case 27:
                            if (SP > 500) {
                                SP--;
                                pw.printf("%d ", (SP * -1));
                                pw.flush();
                                pw.printf("%d ", (AC * -1));
                                pw.flush();
                                PC++;
                                break;
                            } else {
                                System.out.println("Cannot perform call!");
                                proc.destroy();
                                System.exit(1);
                            }
                        case 28:
                            pw.printf("%d ", SP);
                            pw.flush();
                            SP++;
                            AC = inst.nextInt();
                            PC++;
                            break;
                        case 29:
                            if (interrupt == true) {
                                pw.printf("%d ", -1999);
                                pw.flush();
                                pw.printf("%d ", (SP * -1));
                                pw.flush();
                                pw.printf("%d ", -1998);
                                pw.flush();
                                PC = PC + 1;
                                pw.printf("%d ", (PC * -1));
                                pw.flush();
                                PC = 1500;
                                SP = 1998;
                                interrupt = false;
                            } else {
                                PC++;
                            }
                            break;
                        case 30:
                            interrupt = true;
                            if (SP < 2000) {
                                //restore system state
                                pw.printf("%d ", SP);
                                pw.flush();
                                PC = inst.nextInt();
                                SP++;
                                pw.printf("%d ", SP);
                                pw.flush();
                                SP = inst.nextInt();
                            } else {
                                System.out.println("Error: unable to restore system state. System stack empty!");
                                proc.destroy();
                                System.exit(1);
                            }
                            break;

                        case 50:
                            execute = false;
                            PC = 3000;
                            break;
                    }
                    timer--;
                    if (timer == 0) {
                        if (interrupt == true) {
                            pw.printf("%d ", -1999);
                            pw.flush();
                            pw.printf("%d ", (SP * -1));
                            pw.flush();
                            pw.printf("%d ", -1998);
                            pw.flush();
                            pw.printf("%d ", (PC*-1));
                            PC = 1000;
                            SP = 1998;
                            interrupt = false;

                        }
                        timer = Integer.parseInt(args[1]);
                    }
                }
            }
            pw.printf("%d ", PC);
            pw.flush();
            proc.waitFor();
            int exitVal = proc.exitValue();
            System.out.println("Process exited: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
