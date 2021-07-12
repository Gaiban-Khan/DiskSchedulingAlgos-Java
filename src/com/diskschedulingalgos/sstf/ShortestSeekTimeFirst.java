package com.diskschedulingalgos.sstf;

import com.diskschedulingalgos.DiskSchedulingAlgorithm;
import com.diskschedulingalgos.DiskSchedulingInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public final class ShortestSeekTimeFirst extends DiskSchedulingAlgorithm {

    public ShortestSeekTimeFirst(int[] requestQueue) {

        super();
        name = "SSTF";
        this.requestQueue = requestQueue;
        resultMatrix = new int[2][requestQueue.length];

        algorithm();

    }

    @Override
    public void algorithm() {

        Integer[] requestQueueBox = Arrays.stream(requestQueue).boxed().sorted().toArray(Integer[]::new);
        ArrayList<Integer> requestArrList = new ArrayList<>(Arrays.asList(requestQueueBox));
        ArrayList<Integer> seekSequence = new ArrayList<>();
        int currentPosition = headPosition;

        boolean foundSplit = false;

        for (Integer j :
                requestArrList) {

            if (j >= headPosition && requestArrList.indexOf(j) != 0) {

                int diff1 = headPosition - requestArrList.get(requestArrList.indexOf(j) - 1);
                int diff2 = Math.abs(headPosition - j);

                if (diff1 >= diff2) {
                    currentPosition = j;
                }
                else {
                    currentPosition = requestArrList.get(requestArrList.indexOf(j) - 1);
                }


                seekSequence.add(currentPosition);
                foundSplit = true;
                break;

            }

        }

        if (!foundSplit) {

            currentPosition = requestArrList.get(requestArrList.size() - 1);
            seekSequence.add(currentPosition);

        }

        ArrayList<Integer> firstHalf = new ArrayList<>(requestArrList.subList(0,requestArrList.indexOf(currentPosition)));
        Collections.reverse(firstHalf);
        ArrayList<Integer> secondHalf = new ArrayList<>(requestArrList.subList(requestArrList.indexOf(currentPosition) + 1, requestArrList.size()));

        while(!firstHalf.isEmpty() || !secondHalf.isEmpty()) {

            if (firstHalf.isEmpty()) {

                currentPosition = secondHalf.get(0);
                secondHalf.remove(0);

            }
            else if (secondHalf.isEmpty()) {

                currentPosition = firstHalf.get(0);
                firstHalf.remove(0);

            }
            else {

                int diff1 = Math.abs(currentPosition - firstHalf.get(0));
                int diff2 = Math.abs(currentPosition - secondHalf.get(0));

                if (diff1 >= diff2) {

                    currentPosition = secondHalf.get(0);
                    secondHalf.remove(0);

                }
                else {

                    currentPosition = firstHalf.get(0);
                    firstHalf.remove(0);

                }


            }

            seekSequence.add(currentPosition);
        }

        resultMatrix[0] = seekSequence.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();
        totalSeekOperations = DiskSchedulingInterface.buildResultMatrix(resultMatrix, headPosition);

    }

    @Override
    public void displayResults() {

        System.out.println("\nShortest Seek Time First algorithm:");
        DiskSchedulingInterface.displayResults(resultMatrix);
        System.out.println("\nTotal seek Operations:  " + totalSeekOperations);
    }

    @Override
    public void displayDetailedResults() {

        System.out.println("\nShortest Seek Time First algorithm:\n");
        DiskSchedulingInterface.displayDetailedResults(resultMatrix, headPosition, totalTracks, 0);

    }

}
