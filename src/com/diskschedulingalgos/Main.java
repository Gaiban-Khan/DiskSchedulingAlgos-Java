package com.diskschedulingalgos;

import com.diskschedulingalgos.clook.CLook;
import com.diskschedulingalgos.cscan.CScan;
import com.diskschedulingalgos.fcfs.FirstComeFirstServe;
import com.diskschedulingalgos.look.Look;
import com.diskschedulingalgos.scan.Scan;
import com.diskschedulingalgos.sstf.ShortestSeekTimeFirst;

import java.util.*;

public class Main {

    static int requestQueueSize;
    static int[] requestQueue;
    static int totalTracks;
    static int headPosition;

    static FirstComeFirstServe fcfs;
    static ShortestSeekTimeFirst sstf;
    static CLook cLook;
    static Look look;
    static Scan scan;
    static CScan cScan;

    static Scanner in = new Scanner(System.in);

    // Clears screen.
    static void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    static void header1() {

        System.out.println();
        System.out.println("-".repeat(15) + " 💾 Disk Scheduling Algorithms 💾 " + "-".repeat(15));
        System.out.println();

    }

    static void header2() {

        header1();
        System.out.println("Total number of tracks:  " + totalTracks);
        System.out.println();
        System.out.println("Initial head position:  " + headPosition);
        System.out.println();


    }

    static void header3 () {

        header2();
        System.out.println("Length of the request queue:  " + requestQueueSize);
        System.out.println();

    }

    static void header4 () {

        header3();
        System.out.println("Request queue:  " + Arrays.toString(requestQueue));
        System.out.println();

    }

    static void header5 (int choice) {

        header4();
        if (choice == 0) {

            System.out.println("You have selected \"Compare all\"!");
            System.out.println();

        }
    }

    // Reads total track number and initial head position.
    static void phase1() {

        header1();
        while (true) {

            try {

                System.out.print("\n🟢 Enter the total number of tracks: ");
                totalTracks = Integer.parseInt(in.nextLine());
                if (totalTracks < 1) throw new InvalidTotalTracks();

                break;

            } catch (NumberFormatException | NoSuchElementException e) {

                System.out.println("\n⚠️ Please enter an integer!");

            } catch (InvalidTotalTracks e) {

                System.out.println();
                System.out.println(e.getMessage());

            }

        }

        clearScreen();

        subPhase1();

        DiskSchedulingAlgorithm.totalTracks = totalTracks;

    }

    // Reads the head position.
    static void subPhase1 () {

        header1();
        System.out.println("Total number of tracks:  " + totalTracks);
        System.out.println();
        System.out.println("-".repeat(58));

        while (true) {

            try {

                System.out.print("\n🟢 Enter the initial head position: ");
                headPosition = Integer.parseInt(in.nextLine());
                if (headPosition < 0 || headPosition >= totalTracks) throw new InvalidHeadPosition();
                break;
            }catch (NumberFormatException | NoSuchElementException e) {

                System.out.println("\n⚠️ Please enter an integer!");

            } catch (InvalidHeadPosition e) {

                System.out.println();
                System.out.println(e.getMessage());

            }

        }

        DiskSchedulingAlgorithm.headPosition = headPosition;
        clearScreen();
    }

    // Reads the requestQueueSize.
    static void phase2 () {

        header2();
        System.out.println("-".repeat(58));

        while (true) {

            try {

                System.out.print("\n🟢 Enter the length of the request queue: ");
                requestQueueSize = Integer.parseInt(in.nextLine());
                if (requestQueueSize < 1) throw new InvalidRequestQueueSize();
                break;

            } catch (NumberFormatException | NoSuchElementException e) {

                System.out.println("\n⚠️ Please enter an integer!");

            } catch (InvalidRequestQueueSize e) {

                System.out.println();
                System.out.println(e.getMessage());

            }

        }

        clearScreen();
    }

    // Reads the requestQueue.
    static void phase3 () {

        header3();
        System.out.println("-".repeat(58));

        requestQueue = new int[requestQueueSize];

        for (int i = 0; i < requestQueueSize; i++) {

            while (true) {

                try{

                    System.out.print("\n🟢 Enter the " + (i + 1) + "th element (Index: " + i + "):  ");
                    int request = Integer.parseInt(in.nextLine());
                    if (request >= totalTracks || request < 0) throw new InvalidRequest();
                    requestQueue[i] = request;
                    break;

                }catch (NumberFormatException | NoSuchElementException e) {

                    System.out.println("\n⚠️ Please enter an integer!");

                }catch (InvalidRequest e) {

                    System.out.println();
                    System.out.println(e.getMessage());

                }

            }

        }

        clearScreen();

    }

    // Reads choice.
    static int phase4 () {

        header4();
        System.out.println();
        System.out.println("-".repeat(58));
        System.out.println();

        int choice;
        System.out.println("-".repeat(15) + " Menu " + "-".repeat(15));
        System.out.println();
        System.out.println("1️⃣. Execute a disk scheduling algorithm.\n\n2️⃣. Execute all and compare.");
        System.out.println();
        System.out.println("-".repeat(36));

        while (true){

            try {
                System.out.print("\n🟢 Enter your choice:  ");
                choice = Integer.parseInt(in.nextLine());
                if(choice != 1 && choice != 2) throw new InvalidChoice();
                break;

            } catch (NumberFormatException | NoSuchElementException e) {

                System.out.println("\n⚠️ Please enter valid choice!");

            }catch (InvalidChoice e) {

                System.out.println();
                System.out.println(e.getMessage());

            }

        }

        clearScreen();

        header4();
        System.out.println("-".repeat(58));
        System.out.println();

        int choice1 = 0;
        if (choice == 1) {

            System.out.println("-".repeat(15) + " Menu " + "-".repeat(15));
            System.out.println("\n1️⃣. First Come First Serve.\n\n2️⃣. Shortest Seek Time First.\n\n3️⃣. C-Scan.\n\n4️⃣. Scan.\n\n5️⃣. Look.\n\n6️⃣. C-Look.\n");
            System.out.println("-".repeat(36));

            while (true) {

                try {

                    System.out.print("\n🟢 Enter your choice:  ");
                    choice1 = Integer.parseInt(in.nextLine());
                    if (choice1 < 0 || choice1 > 6) throw new InvalidChoice1();
                    break;
                }catch (NumberFormatException | NoSuchElementException e) {

                    System.out.println("\n⚠️ Please enter an integer!");

                }catch (InvalidChoice1 e) {

                    System.out.println();
                    System.out.println(e.getMessage());

                }

            }

        }

        clearScreen();

        return choice1;

    }

    // Gives display options..
    static void phase5 (int choice) {

        header5(choice);
        System.out.println("-".repeat(58));
        System.out.println();

        if (choice == 0) {

            ArrayList<Integer> totalSeeks = new ArrayList<>();
            totalSeeks.add(fcfs.totalSeekOperations);
            totalSeeks.add(sstf.totalSeekOperations);
            totalSeeks.add(cLook.totalSeekOperations);
            totalSeeks.add(look.totalSeekOperations);
            totalSeeks.add(scan.totalSeekOperations);
            totalSeeks.add(cScan.totalSeekOperations);

            int maxSeekOperations = Collections.max(totalSeeks);
            int indexMax = totalSeeks.indexOf(maxSeekOperations);
            int minSeekOperations = Collections.min(totalSeeks);
            int indexMin = totalSeeks.indexOf(minSeekOperations);

            Collections.sort(totalSeeks);

            boolean fcfsDone = false;
            boolean sstfDone = false;
            boolean clookDone = false;
            boolean lookDone = false;
            boolean scanDone = false;

            for (Integer i :
                    totalSeeks) {

                if ((i == fcfs.totalSeekOperations) && !fcfsDone) {

                    fcfs.displayResults();
                    System.out.println();
                    fcfsDone = true;

                }else if ((i == sstf.totalSeekOperations) && !sstfDone) {

                    sstf.displayResults();
                    System.out.println();
                    sstfDone = true;

                }else if ((i == cLook.totalSeekOperations) && !clookDone) {

                    cLook.displayResults();
                    System.out.println();
                    clookDone = true;

                }else if ((i == look.totalSeekOperations) && !lookDone) {

                    look.displayResults();
                    System.out.println();
                    lookDone = true;

                }else if ((i == scan.totalSeekOperations) && !scanDone) {

                    scan.displayResults();
                    System.out.println();
                    scanDone = true;

                }else {

                    cScan.displayResults();
                    System.out.println();

                }

            }

            System.out.println();

            switch (indexMin) {

                case 0 -> System.out.println("✅ Best performing algorithm:  First Come First serve ( " + fcfs.totalSeekOperations + " Operations ).");
                case 1 -> System.out.println("✅ Best performing algorithm:  Shortest Seek Time First ( " + sstf.totalSeekOperations + " Operations ).");
                case 2 -> System.out.println("✅ Best performing algorithm:  CLook ( " + cLook.totalSeekOperations + " Operations ).");
                case 3 -> System.out.println("✅ Best performing algorithm:  Look ( " + look.totalSeekOperations + " Operations ).");
                case 4 -> System.out.println("✅ Best performing algorithm:  Scan ( " + scan.totalSeekOperations + " Operations ).");
                case 5 -> System.out.println("✅ Best performing algorithm:  CScan ( " + cScan.totalSeekOperations + " Operations ).");

            }

            System.out.println();

            switch (indexMax) {

                case 0 -> System.out.println("❌ Worst performing algorithm:  First Come First serve ( " + fcfs.totalSeekOperations + " Operations ).");
                case 1 -> System.out.println("❌ Worst performing algorithm:  Shortest Seek Time First ( " + sstf.totalSeekOperations + " Operations ).");
                case 2 -> System.out.println("❌ Worst performing algorithm:  CLook ( " + cLook.totalSeekOperations + " Operations ).");
                case 3 -> System.out.println("❌ Worst performing algorithm:  Look ( " + look.totalSeekOperations + " Operations ).");
                case 4 -> System.out.println("❌ Worst performing algorithm:  Scan ( " + scan.totalSeekOperations + " Operations ).");
                case 5 -> System.out.println("❌ Worst performing algorithm:  CScan ( " + cScan.totalSeekOperations + " Operations ).");

            }

            System.out.println();

        }else {

            System.out.println("-".repeat(58));
            System.out.println();

            if (choice == 1) {
                fcfs.displayResults();
                System.out.println();


                while (true){

                    try {
                        System.out.print("\n🟢 Enter 1️⃣ for detailed result 📊 or 2️⃣ to go to the menu:  ");
                        int response = Integer.parseInt(in.nextLine());
                        clearScreen();
                        if (response == 1) {

                            header5(choice);
                            System.out.println("-".repeat(58));
                            System.out.println();
                            fcfs.displayDetailedResults();

                        }else if (response == 2) {

                            int m = phase4();
                            phase5(m);

                        }
                        break;

                    } catch (NumberFormatException | NoSuchElementException e) {

                        System.out.println("\n⚠️ Please enter an integer!");

                    }

                }

            }else if (choice == 2) {
                sstf.displayResults();
                System.out.println();

                while (true){

                    try {

                        System.out.print("\n🟢 Enter 1️⃣ for detailed result 📊 or 2️⃣ to go to the menu:  ");

                        int response = Integer.parseInt(in.nextLine());
                        clearScreen();
                        if (response == 1) {

                            header5(choice);
                            System.out.println("-".repeat(58));
                            System.out.println();
                            sstf.displayDetailedResults();

                        }else if (response == 2) {

                            int m = phase4();
                            phase5(m);

                        }
                        break;

                    } catch (NumberFormatException | NoSuchElementException e) {

                        System.out.println("\n⚠️ Please enter an integer!");

                    }

                }

            }else if (choice == 3) {

                cScan.displayResults();
                System.out.println();

                while (true){

                    try {

                        System.out.print("\n🟢 Enter 1️⃣ for detailed result 📊 or 2️⃣ to go to the menu:  ");

                        int response = Integer.parseInt(in.nextLine());
                        clearScreen();
                        if (response == 1) {

                            header5(choice);
                            System.out.println("-".repeat(58));
                            System.out.println();
                            cScan.displayDetailedResults();

                        }else if (response == 2) {

                            int m = phase4();
                            phase5(m);

                        }
                        break;

                    } catch (NumberFormatException | NoSuchElementException e) {

                        System.out.println("\n⚠️ Please enter an integer!");

                    }

                }

            }else if (choice == 4) {
                scan.displayResults();
                System.out.println();

                while (true){

                    try {

                        System.out.print("\n🟢 Enter 1️⃣ for detailed result 📊 or 2️⃣ to go to the menu:  ");
                        int response = Integer.parseInt(in.nextLine());
                        clearScreen();
                        if (response == 1) {

                            header5(choice);
                            System.out.println("-".repeat(58));
                            System.out.println();
                            scan.displayDetailedResults();

                        }else if (response == 2) {

                            int m = phase4();
                            phase5(m);

                        }
                        break;

                    } catch (NumberFormatException | NoSuchElementException e) {

                        System.out.println("\n⚠️ Please enter an integer!");

                    }

                }

            }else if (choice == 5) {
                look.displayResults();
                System.out.println();

                while (true){

                    try {

                        System.out.print("\n🟢 Enter 1️⃣ for detailed result 📊 or 2️⃣ to go to the menu:  ");
                        int response = Integer.parseInt(in.nextLine());
                        clearScreen();
                        if (response == 1) {

                            header5(choice);
                            System.out.println("-".repeat(58));
                            System.out.println();
                            look.displayDetailedResults();

                        }else if (response == 2) {

                            int m = phase4();
                            phase5(m);

                        }
                        break;

                    } catch (NumberFormatException | NoSuchElementException e) {

                        System.out.println("\n⚠️ Please enter an integer!");

                    }

                }

            }else  {
                cLook.displayResults();
                System.out.println();

                while (true){

                    try {

                        System.out.print("\n🟢Enter 1️⃣ for detailed result 📊 or 2️⃣ to go to the menu:  ");
                        int response = Integer.parseInt(in.nextLine());
                        clearScreen();
                        if (response == 1) {

                            header5(choice);
                            System.out.println("-".repeat(58));
                            System.out.println();
                            cLook.displayDetailedResults();

                        }else if (response == 2) {

                            int m = phase4();
                            phase5(m);

                        }
                        break;

                    } catch (NumberFormatException | NoSuchElementException e) {

                        System.out.println("\n⚠️ Please enter an integer!");

                    }

                }

            }

        }

        while (true){
            try {

                System.out.print("🟢 Enter 1️⃣ to continue, 2️⃣ for Change menu, or any other int to exit:  ");
                int n  =  Integer.parseInt(in.nextLine());
                clearScreen();
                if (n == 1) {
                    int m = phase4();
                    phase5(m);
                }
                else if (n == 2) phase6();
                else return;
                break;

            }catch (NumberFormatException | NoSuchElementException e) {

                System.out.println("\n⚠️ Please enter an integer.");

            }

        }

        clearScreen();

    }

    // Edit.
    static void phase6() {

        header4();
        System.out.println("-".repeat(58));
        System.out.println();
        System.out.println("-".repeat(10) + " Menu " + "-".repeat(10));
        System.out.println();
        System.out.println("1️⃣. Change total tracks.");
        System.out.println("2️⃣. Change initial head position.");
        System.out.println("3️⃣. Change request queue.");
        System.out.println();
        System.out.println("-".repeat(26));

        int choice;

        while (true){
            try {

                System.out.print("\nEnter your choice:  ");
                choice = Integer.parseInt(in.nextLine());
                if (choice != 1 && choice != 2 && choice != 3) throw new InvalidChoice();
                break;

            }catch (NumberFormatException | NoSuchElementException e) {

                System.out.println();
                System.out.println("⚠️ Please enter an integer.");

            }catch (InvalidChoice e) {

                System.out.println();
                System.out.println("⚠️ Entered choice must be 1 or 2 or 3.");

            }
        }

        clearScreen();

        switch (choice) {

            case 1 -> {

                phase1();
                phase2();
                phase3();
                init();

            }

            case 2 -> {

                subPhase1();
                init();
            }

            case 3 -> {

                phase2();
                phase3();
                init();
            }

        }

        int n = phase4();
        phase5(n);

    }

    static void init() {

        fcfs = new FirstComeFirstServe(requestQueue);
        sstf = new ShortestSeekTimeFirst(requestQueue);
        cLook = new CLook(requestQueue);
        look = new Look(requestQueue);
        scan = new Scan(requestQueue);
        cScan = new CScan(requestQueue);

    }

    public static void main(String[] args) {

        clearScreen();
        phase1();
        phase2();
        phase3();
        init();
        int choice = phase4();
        phase5(choice);

    }

}


class InvalidRequestQueueSize extends Exception {

    public InvalidRequestQueueSize () {

        super("⚠️ Size of the request queue must be greater than 1.");

    }

}

class InvalidTotalTracks extends Exception {

    public InvalidTotalTracks () {

        super("⚠️ Total tracks must be greater than 1.\n✅Tip -> Consider something greater than 10.");

    }

}

class InvalidRequest extends Exception {

    public InvalidRequest () {

        super("⚠️ The request size must be greater than or equal to zero and less than the total number of tracks.");

    }

}

class InvalidChoice extends Exception {

    public InvalidChoice () {

        super("⚠️ ️Please enter either 1 or 2.");

    }

}

class InvalidChoice1 extends Exception {

    public InvalidChoice1 () {

        super("⚠️ Please enter a number in the range 1 - 6.");

    }

}

class InvalidHeadPosition extends Exception {

    public InvalidHeadPosition() {

        super("⚠️ The initial head position must be greater than or equal to zero and less the total number of tracks.");

    }

}