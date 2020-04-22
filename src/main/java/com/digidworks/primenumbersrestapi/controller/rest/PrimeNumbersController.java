
package com.digidworks.primenumbersrestapi.controller.rest;

import com.digidworks.primenumbersrestapi.service.PrimeNumbers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * Prime numbers REST API Controller.
 */
@RestController
@RequestMapping("/api/prime-numbers")
public class PrimeNumbersController {
    private PrimeNumbers primeNumbers;

    public PrimeNumbersController(PrimeNumbers primeNumbers) {
        this.primeNumbers = primeNumbers;
    }

    /**
     * Find out if a given number is a prime number.
     *
     * @param number the number to check
     * @return true if it's prime, false otherwise.
     */
    @GetMapping("/check-primality/{number}")
    public boolean checkPrimality(@PathVariable long number) throws ExecutionException, InterruptedException {
        return primeNumbers.isPrime(number);
    }

    /**
     * Find out the next prime that is >=number.
     *
     * @param number the given number.
     * @return the next prime which is >=number.
     */
    @GetMapping("/find-next-prime/{number}")
    public Integer findNextPrime(@PathVariable Integer number) {
        return primeNumbers.findNextPrime(number);
    }
}
