package org.transaction.core;

import org.transaction.core.exceptions.InsufficientBalanceException;
import org.transaction.core.exceptions.InvalidAmountException;

import java.util.HashMap;
import java.util.Map;

public class ATMTransactionApplication {
    public static void main(String[] args) {

        _ATM atm = new _ATM();
        Map<Integer, Integer> denomination = new HashMap<>();
        denomination.put(10,10);
        denomination.put(100,25);
        denomination.put(20,5);
        denomination.put(50,10);
        // Load ATM with Initial denominations
        atm.initDenominations(denomination);

        try {
            Map<Integer, Integer> dispensedDenominations_2000 = atm.withdrawAmount(2000);
            System.out.println("Dispensed denominations for 2000 :" + dispensedDenominations_2000);

            Map<Integer, Integer> dispensedDenominations_80 = atm.withdrawAmount(80);
            System.out.println("Dispensed denominations for 80 :" + dispensedDenominations_80);

            Map<Integer, Integer> dispensedDenominations_250 = atm.withdrawAmount(250);
            System.out.println("Dispensed denominations for 250 :" + dispensedDenominations_250);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Map<Integer, Integer> dispensedDenominations_251 = atm.withdrawAmount(251);

        } catch (InvalidAmountException | InsufficientBalanceException e){
            System.out.println(e.getMessage());
        }
        try{
            Map<Integer, Integer> dispensedDenominations_10000 = atm.withdrawAmount(10000);

        } catch (InvalidAmountException | InsufficientBalanceException e){
            System.out.println(e.getMessage());
        }
    }
}