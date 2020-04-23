package com.digidworks.primenumbersrestapi.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service with all the operations with prime numbers.
 */
@Service
public class PrimeNumbers {
    private ExecutorService executor = Executors.newCachedThreadPool();
    private volatile boolean foundNotPrime = false;

    /**
     * Find out if a given number is a prime number.
     *
     * @param number the number to check
     * @return true if it's prime, false otherwise.
     */
    public synchronized boolean isPrime(long number) throws ExecutionException, InterruptedException {
        if (number == Integer.MAX_VALUE || number == 2) {
            return true;
        }

        if (number < 2 || number % 2 == 0) {
            return false;
        }

        foundNotPrime = false;

        long upperLimit = (long) Math.ceil(Math.sqrt(number));
        int maxPoolSize = 200;
        long pageSize = upperLimit / maxPoolSize;

        if (pageSize < 1000) {
            return checkIfPrime(number, 3, upperLimit);
        }

        CompletableFuture[] pageResults = new CompletableFuture[maxPoolSize];

        for (int i = 0; i < maxPoolSize; i++) {
            long pageStart = i > 0 ? i * pageSize : 3;
            long pageEnd = i < maxPoolSize - 1 ? (i + 1) * pageSize : upperLimit;

            pageResults[i] = CompletableFuture.supplyAsync(() -> checkIfPrime(number, pageStart, pageEnd), executor);
        }

        CompletableFuture.allOf(pageResults).join();

        for (CompletableFuture future : pageResults) {
            boolean result = (boolean) future.get();

            if (!result) {
                return false;
            }
        }

        return true;
    }

    /**
     * Find out the next prime, that is >=number.
     *
     * @param number the given number.
     * @return the next prime which is >=number.
     */
    public long findNextPrime(long number) throws ExecutionException, InterruptedException {
        long nextProbablePrime = number % 2 == 0 ? number + 1 : number;

        while (nextProbablePrime < Long.MAX_VALUE && !isPrime(nextProbablePrime)) {
            nextProbablePrime += 2;
        }

        if (nextProbablePrime == Long.MAX_VALUE) {
            return 0;
        }

        return nextProbablePrime;
    }

    /**
     * Checks for primality, suitable for parallelization. It breaks when the class flag foundNotPrime is set.
     *
     * @param number the number to check
     * @param start  starting index (inclusive)
     * @param end    final index (exclusive)
     * @return whether or not this number is prime.
     */
    private boolean checkIfPrime(long number, long start, long end) {
        for (long i = start; i < end; i += 2) {
            if (foundNotPrime) {
                return false;
            }

            if (number % i == 0) {
                foundNotPrime = true;
                return false;
            }
        }

        return true;
    }
}
