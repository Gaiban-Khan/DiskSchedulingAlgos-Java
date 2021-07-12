package com.diskschedulingalgos.fcfs;

import com.diskschedulingalgos.DiskSchedulingAlgorithm;
import com.diskschedulingalgos.DiskSchedulingInterface;

public final class FirstComeFirstServe extends DiskSchedulingAlgorithm {

    public FirstComeFirstServe(int[] requestQueue) {

        super();

        name = "FCFS";
        this.requestQueue = requestQueue;
        resultMatrix = new int[2][requestQueue.length];

        algorithm();

    }

    @Override
    public void algorithm() {

        resultMatrix[0] = requestQueue;

        totalSeekOperations = DiskSchedulingInterface.buildResultMatrix(resultMatrix, headPosition);

    }

    @Override
    public void displayResults() {

        System.out.println("\nFirst Come First Serve algorithm:");
        DiskSchedulingInterface.displayResults(resultMatrix);
        System.out.println("\nTotal seek Operations:  " + totalSeekOperations);

    }

    @Override
    public void displayDetailedResults() {

        System.out.println("\nFirst Come First Serve algorithm:");
        DiskSchedulingInterface.displayDetailedResults(resultMatrix, headPosition, totalTracks, 0);

    }

}
