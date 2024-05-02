package org.transaction.core;

import org.transaction.core.exceptions.InsufficientBalanceException;
import org.transaction.core.exceptions.InvalidAmountException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class _ATM {

    private final Map<Integer,Integer> denominationCount;
    private static final Integer _amountMultiplier = 10;
    private final Lock lock;

    public _ATM() {
        // LinkedHashMap to ensure the order is stored after sorting
        this.denominationCount = new LinkedHashMap<>();
        this.lock = new ReentrantLock();
    }

    public void initDenominations(Map<Integer, Integer> denominations){
        try{
            lock.lock();
            this.denominationCount.clear();
            denominations.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                    .forEach(entry -> this.denominationCount.put(entry.getKey(),entry.getValue()));
        }finally {
            lock.unlock();
        }
    }

    public Map<Integer, Integer> withdrawAmount (int amount) throws InvalidAmountException, InsufficientBalanceException {
        // Validate the amount requested as per the banks denomination multiplier value.
        if (amount % _amountMultiplier != 0){
            throw new InvalidAmountException("Amount must be mutiples of : " + _amountMultiplier + "" );
        }
        // Using of LinkedHashMap to Ensure Order
        Map<Integer, Integer> dispenseDenominations = new LinkedHashMap<>();

        try{
            lock.lock();
            int remainingAmount = amount;
            for(Map.Entry<Integer, Integer> entry : denominationCount.entrySet()){
                int denomination = entry.getKey();
                int count = entry.getValue();

                int notesNeeded = remainingAmount / denomination;
                int notesToDispense = Math.min(notesNeeded,count);

                if(notesToDispense > 0){
                    dispenseDenominations.put(denomination,notesToDispense);
                    remainingAmount -= notesToDispense * denomination;
                    denominationCount.computeIfPresent(denomination, (k,v) -> v - notesToDispense);
                }

                if (remainingAmount == 0){
                    break;
                }
            }
            if (remainingAmount > 0) {
                throw new InsufficientBalanceException("Insufficient Funds In the ATM");
            }
        } finally {
            lock.unlock();
        }
        return dispenseDenominations;
    }
}
