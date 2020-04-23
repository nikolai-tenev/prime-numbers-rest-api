
package com.digidworks.primenumbersrestapi.controller.rest;

import com.digidworks.primenumbersrestapi.service.PrimeNumbers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * Prime numbers REST API Controller.
 */
@Api(tags = {"Prime Numbers"})
@RestController
@RequestMapping("/api/prime-numbers")
@CrossOrigin(
        origins = {"http://localhost/", "https://domain.com/"},
        methods = {RequestMethod.GET}
)
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
    @ApiOperation(value = "Find out if a given number is a prime number.", produces = "application/json")
    @GetMapping("/check-primality/{number}")
    public boolean checkPrimality(@ApiParam(value = "the number to check", example = "123456789") @PathVariable long number) throws ExecutionException, InterruptedException {
        return primeNumbers.isPrime(number);
    }

    /**
     * Find out the next prime that is >=number.
     *
     * @param number the given number.
     * @return the next prime which is >=number.
     */
    @ApiOperation(value = "Find out the next prime that is >=number.", produces = "application/json")
    @GetMapping("/find-next-prime/{number}")
    public long findNextPrime(@ApiParam(value = "the given number", example = "123456789") @PathVariable long number) throws ExecutionException, InterruptedException {
        return primeNumbers.findNextPrime(number);
    }
}
