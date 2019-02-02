package com.double0101.learning.cache;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger> {
    public BigInteger compute(String arg) {
        // 
        return new BigInteger(arg);
    }
}