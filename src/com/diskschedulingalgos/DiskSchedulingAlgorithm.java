package com.diskschedulingalgos;

public abstract class DiskSchedulingAlgorithm implements DiskSchedulingInterface{

    protected String name;
    protected int totalSeekOperations; // Holds the total number of seek operations done.
    protected int[] requestQueue; // Array consisting of requests in their order of arrival.
    protected static int headPosition; // Initial position of the head.
    protected static int totalTracks; // Total number of tracks.

    /*
    Result matrix obtained from algorithm() method.
    resultMatrix[2][requestQueue],
    where
        resultMatrix[0] = Seek Sequence.
        resultMatrix[1] = Seek operations performed to get to the track.
            i.e, resultMatrix[1][0] = Math.abs(resultMatrix[0][0] - headPosition (first request))
                 resultMatrix[1][i] = Math.abs(resultMatrix[0][i] - resultMatrix[0][i - 1] (for the rest of the requests))
    */

    protected int[][] resultMatrix;

    @Override
    public abstract void algorithm();

    public abstract void displayResults();

    public abstract void displayDetailedResults();

}
