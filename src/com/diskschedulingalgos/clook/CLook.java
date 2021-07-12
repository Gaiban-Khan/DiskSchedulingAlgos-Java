package com.diskschedulingalgos.clook;

import com.diskschedulingalgos.DiskSchedulingAlgorithm;
import com.diskschedulingalgos.DiskSchedulingInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public final class CLook extends DiskSchedulingAlgorithm {

    public CLook(int[] requestQueue) {

        super();

        name = "CLOOK";
        this.requestQueue = requestQueue;
        resultMatrix = new int[2][requestQueue.length];

        algorithm();

    }

    @Override
    public void algorithm() {

        Integer[] requestQueueBox = Arrays.stream(requestQueue).boxed().sorted().toArray(Integer[]::new);
        ArrayList<Integer> requestArrList = new ArrayList<>(Arrays.asList(requestQueueBox));

        boolean taskDone = false;

        for (Integer j:
                requestArrList) {

            if (j >= headPosition) {

                ArrayList<Integer> firstHalf = new ArrayList<>(requestArrList.subList(requestArrList.indexOf(j), requestArrList.size()));

                ArrayList<Integer> secondHalf = new ArrayList<>(requestArrList.subList(0,requestArrList.indexOf(j)));

                ArrayList<Integer> seekSequence = new ArrayList<>();
                seekSequence.addAll(firstHalf);
                seekSequence.addAll(secondHalf);

                resultMatrix[0] = seekSequence.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();

                taskDone = true;

                break;

            }

        }

        if (!taskDone) {

            resultMatrix[0] = requestArrList.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();

        }

        totalSeekOperations = DiskSchedulingInterface.buildResultMatrix(resultMatrix, headPosition);

    }

    @Override
    public void displayResults() {

        System.out.println("\nC-Look algorithm:");
        DiskSchedulingInterface.displayResults(resultMatrix);
        System.out.println("\nTotal seek Operations:  " + totalSeekOperations);

    }

    @Override
    public void displayDetailedResults() {

        System.out.println("\nC-Look algorithm:");
        DiskSchedulingInterface.displayDetailedResults(resultMatrix, headPosition, totalTracks, 0);

    }

}
