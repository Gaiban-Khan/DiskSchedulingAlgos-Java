package com.diskschedulingalgos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public interface DiskSchedulingInterface {

    // This function has the implementation of the algorithm.
    void algorithm ();

    // For FCFS, LOOK, CLOOK, SSTF algorithms.
    static int buildResultMatrix(int[][] resultMatrix, int headPosition) {

        int totalSeekOperations = 0;

        for (int m = 0; m < resultMatrix[0].length; m++) {

            if (m == 0) {

                totalSeekOperations += resultMatrix[1][m] = Math.abs(resultMatrix[0][m] - headPosition);

            } else {

                totalSeekOperations += resultMatrix[1][m] = Math.abs(resultMatrix[0][m] - resultMatrix[0][m - 1]);

            }

        }

        return totalSeekOperations;
    }

    // For SCAN, CSCAN algorithms.
    static int buildResultMatrix(int[][] resultMatrix, int headPosition, ArrayList<Integer> requestArrList, int totalTracks, int type) {

        int totalSeekOperations = DiskSchedulingInterface.buildResultMatrix(resultMatrix,headPosition);

        int startTraversedCount = 0;
        int endTraversedCount = 0;
        if (resultMatrix[0].length != requestArrList.size()) {

            Integer[] resultMat0Boxed = Arrays.stream(resultMatrix[0]).boxed().toArray(Integer[]::new);
            ArrayList<Integer> resultMatList0 = new ArrayList<>(Arrays.asList(resultMat0Boxed));
            Integer[] resultMat1Boxed = Arrays.stream(resultMatrix[1]).boxed().toArray(Integer[]::new);
            ArrayList<Integer> resultMatList1 = new ArrayList<>(Arrays.asList(resultMat1Boxed));

            for (int i :
                    resultMatrix[0]) {

                if (!requestArrList.contains(i) || (((i == totalTracks - 1) && endTraversedCount == 0) || ((i == 0) && startTraversedCount == 0) && type == 2)) {

                    int index = resultMatList0.indexOf(i);
                    if (index != resultMatList1.size() - 1){

                        resultMatList1.set(index + 1, resultMatList1.get(index) + resultMatList1.get(index + 1));

                    }
                    resultMatList1.remove(index);
                    resultMatList0.remove(index);

                    if (i == totalTracks - 1) endTraversedCount++;
                    if (i == 0) startTraversedCount++;

                }

            }

            resultMatrix[0] = resultMatList0.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();
            resultMatrix[1] = resultMatList1.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();

        }

        return totalSeekOperations;
    }

    // Takes integer as input and returns the number of digits.
    static int getDigits(int i) {

        int j = 1;
        while(i/10 != 0) {

            i = i /10;
            j++;

        }

        return j;

    }

    // This function displays results in detail.
    static void displayDetailedResults (int[][] resultMatrix, int headPosition, int totalTracks, int type) {

        System.out.println("\n⚠️ Looks good for tracks < 15.");

        ArrayList<Integer> requestArrList = new ArrayList<>(Arrays.asList(Arrays.stream(resultMatrix[0]).boxed().toArray(Integer[]::new)));
        ArrayList<Integer> unfilteredArrList = new ArrayList<>();
        boolean goodList = true;

        if (type == 1 || type == 2) {

            if (requestArrList.size() == 1) {

                if (requestArrList.get(0) < headPosition){

                    unfilteredArrList.add(totalTracks - 1);
                    if (type == 2) unfilteredArrList.add(0);
                    unfilteredArrList.add(requestArrList.get(0));
                    resultMatrix[0] = new int[unfilteredArrList.size()];
                    resultMatrix[0] = unfilteredArrList.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();

                }

                resultMatrix[1] = new int[resultMatrix[0].length];
                buildResultMatrix(resultMatrix, headPosition);
                goodList = true;

            }
            else {

                for (Integer i :
                        requestArrList) {

                    if ((requestArrList.indexOf(i) != 0 && i < requestArrList.get(requestArrList.indexOf(i) - 1)) || (requestArrList.indexOf(i) == 0 && i < headPosition)) {

                        ArrayList<Integer> firstHalf = new ArrayList<>(requestArrList.subList(0, requestArrList.indexOf(i)));
                        ArrayList<Integer> secondHalf = new ArrayList<>(requestArrList.subList(requestArrList.indexOf(i), requestArrList.size()));

                        if (!requestArrList.contains(totalTracks - 1)) firstHalf.add(totalTracks - 1);
                        if (type == 2 && !requestArrList.contains(0)) firstHalf.add(0);

                        unfilteredArrList.addAll(firstHalf);
                        unfilteredArrList.addAll(secondHalf);
                        goodList = false;
                        break;
                    }

                }

                if (!goodList) {
                    resultMatrix[0] = unfilteredArrList.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();
                    resultMatrix[1] = new int[resultMatrix[0].length];
                    buildResultMatrix(resultMatrix, headPosition);
                }

            }

        }


        int[][] displayMatrix = new int[resultMatrix[0].length][3];

        for (int i = 0; i < resultMatrix[0].length; i++) {

            if (i == 0) {

                displayMatrix[i][0] = (headPosition * 2) + 1;

            }
            else {

                displayMatrix[i][0] = (resultMatrix[0][i - 1] * 2) + 1;

            }

            displayMatrix[i][2] = (resultMatrix[0][i] * 2) + 1;
            if(displayMatrix[i][0] > displayMatrix[i][2]) displayMatrix[i][1] = displayMatrix[i][2] + resultMatrix[1][i];
            else displayMatrix[i][1] = displayMatrix[i][0] + resultMatrix[1][i];

        }

        int displayRows = (totalTracks * 2) - 1;
        boolean skipLoop = false;
        System.out.print("\n  Request ->    ");

        int factor;
        if (type == 1) {

            if(requestArrList.contains(totalTracks - 1) || goodList) {

                factor = 0;

            }else {

                factor = 1;

            }

        }else if (type == 2) {

            if((requestArrList.contains(totalTracks - 1) && requestArrList.contains(0)) || goodList) {

                factor = 0;

            }else if (requestArrList.contains(totalTracks - 1) || requestArrList.contains(0)) {

                factor = 1;

            }else {

                factor = 2;

            }

        }else {

            factor = 0;

        }

        for (int i = 1; i <= resultMatrix[0].length - factor; i++) {

            System.out.print(i + "    ");

            if (i != 0 && !requestArrList.contains(resultMatrix[0][i - 1])) {

                if (skipLoop) continue;
                if (type == 2 && !requestArrList.contains(totalTracks - 1) && !requestArrList.contains(0)) System.out.print("     ");
                System.out.print("     ");
                skipLoop = true;

            }

        }
        System.out.println();
        System.out.println("  Tracks");

        for (int i = 1; i <= displayRows; i++) {

            if (i % 2 == 0) {

                System.out.print(" ".repeat(12));

            }else {

                System.out.print(" ".repeat(4 - getDigits((i - 1)/2)) + ((i - 1)/2) + "        ");

            }

            for (int j = 1; j <= resultMatrix[0].length; j++) {

                int startTrackH = displayMatrix[j - 1][0];
                int midPointH = displayMatrix[j - 1][1];
                int endTrackH = displayMatrix[j - 1][2];
                int seekCost = resultMatrix[1][j - 1];

                boolean inTheRange = (i < startTrackH && i > endTrackH) || (i > startTrackH && i < endTrackH);

                if (i % 2 == 0) {

                    if (i == midPointH) {

                        System.out.print(" ".repeat(5 - getDigits(seekCost)) + seekCost);

                    }else {

                        if (inTheRange) {

                            System.out.print("    |");

                        }else  {

                            System.out.print("     ");

                        }

                    }

                }else {
                    if ((i == startTrackH || i == endTrackH) && !requestArrList.contains((i-1)/2) && (i-1)/2 != headPosition) {

                        System.out.print("   --");

                    }
                    else if (i == startTrackH) {

                        System.out.print("   <I");

                    }else if (i == endTrackH) {

                        System.out.print("   <F");

                    }else if (i == midPointH) {

                        System.out.print(" ".repeat(5 - getDigits(seekCost)) + seekCost);

                    }else {

                        if (inTheRange) {

                            System.out.print("    |");

                        }else  {

                            System.out.print("     ");

                        }

                    }

                }

            }

            System.out.println();

        }

        System.out.println();

    }

    // This function displays the output of the algorithm.
    static void displayResults(int[][] resultMatrix) {

        System.out.println("\nThe seek sequence:  " + Arrays.toString(resultMatrix[0]));
        System.out.println("\nThe cost sequence:  " + Arrays.toString(resultMatrix[1]));

    }

}