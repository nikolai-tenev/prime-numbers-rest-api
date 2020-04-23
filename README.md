# REST API for running primality checks on numbers.

The method I chose is the so called naive approach and is adapted from the sieve of Eratosthenes.
 
 For the sake of simplicity this service works only with numbers <= `Long.MAX_VALUE`.

There are a couple of ways this can be implemented (and/or further optimized), but every single one of them has it's drawbacks and depends on the use cases.

## Possible optimizations

- **Add cache**. This can be easily achieved by adding a BitSet (or a HashSet or some other data structure) to the PrimeNumbers service and putting every calculated result 
(is/isn't a prime) in it. Drawback is the bigger memory footprint and the possibility to have very few cache hits.
- **Use `BigInteger.isProbablePrime`**. This is a really good approach since it's really fast and simple to implement, but the drawback is the possibility (although tiny) to give a false positive.
- **Use a DB with a list of all primes**. Since this service deals with numbers up to `Long.MAX_VALUE` it could be implemented in such a way that all primes <= `Long.MAX_VALUE` 
are stored in a DB and the service only looks them up. This would again be very fast, but it would require creating a DB and filling it with primes.
