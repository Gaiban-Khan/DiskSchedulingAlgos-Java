package com.diskschedulingalgos.scan;

import com.diskschedulingalgos.DiskSchedulingAlgorithm;
import com.diskschedulingalgos.DiskSchedulingInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public final class Scan extends DiskSchedulingAlgorithm {

    public Scan(int[] requestQueue) {

        super();

        name = "SCAN";
        this.requestQueue = requestQueue;
        resultMatrix = new int[2][requestQueue.length + 1];

        algorithm();

    }

    @Override
    public void algorithm() {

        Integer[] requestQueueBox = Arrays.stream(requestQueue).boxed().sorted().toArray(Integer[]::new);
        ArrayList<Integer> requestArrList = new ArrayList<>(Arrays.asList(requestQueueBox));
        ArrayList<Integer> seekSequence = new ArrayList<>();

        if (requestArrList.get(0) >= headPosition && requestArrList.get(requestArrList.size() - 1) <= totalTracks - 1){

            resultMatrix[0] = Arrays.stream(requestQueueBox).filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();
            resultMatrix[1] = new int[resultMatrix[0].length];
            totalSeekOperations = DiskSchedulingInterface.buildResultMatrix(resultMatrix, headPosition);
            return;

        }
        boolean taskDone = false;
        for (Integer j:
             requestArrList) {

            if (j >= headPosition) {

                ArrayList<Integer> firstHalf = new ArrayList<>(requestArrList.subList(requestArrList.indexOf(j), requestArrList.size()));
                firstHalf.add(totalTracks - 1);

                ArrayList<Integer> secondHalf = new ArrayList<>(requestArrList.subList(0,requestArrList.indexOf(j)));
                Collections.reverse(secondHalf);


                seekSequence.addAll(firstHalf);
                seekSequence.addAll(secondHalf);

                resultMatrix[0] = seekSequence.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();

                taskDone = true;

                break;

            }

        }

        if (!taskDone) {

            seekSequence.addAll(requestArrList);
            seekSequence.add(totalTracks - 1);
            Collections.reverse(seekSequence);
            resultMatrix[0] = seekSequence.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();

        }

        totalSeekOperations = DiskSchedulingInterface.buildResultMatrix(resultMatrix, headPosition, requestArrList, totalTracks, 1);

    }

    @Override
    public void displayResults() {

        System.out.println("\nScan algorithm:");
        DiskSchedulingInterface.displayResults(resultMatrix);
        System.out.println("\nTotal seek Operations:  " + totalSeekOperations);

    }

    @Override
    public void displayDetailedResults() {

        System.out.println("\nScan algorithm:");
        int[][] tempResultMatrix =  new int[2][resultMatrix[0].length];
        System.arraycopy(resultMatrix[0],0,tempResultMatrix[0],0, resultMatrix[0].length);
        System.arraycopy(resultMatrix[1],0,tempResultMatrix[1],0, resultMatrix[0].length);
        DiskSchedulingInterface.displayDetailedResults(tempResultMatrix, headPosition, totalTracks, 1);

    }

}
