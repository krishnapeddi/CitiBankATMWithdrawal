package org.transaction.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.transaction.core.exceptions.InsufficientBalanceException;
import org.transaction.core.exceptions.InvalidAmountException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class _ATMTest {

    private _ATM atm;

    @BeforeEach
    void setUp() {
        atm = new _ATM();
        Map<Integer, Integer> denominations = new HashMap<>();
        denominations.put(50, 10);
        denominations.put(20, 10);
        denominations.put(10, 10);
        atm.initDenominations(denominations);
    }

    @Test
    void testWithdrawAmount() throws InvalidAmountException, InsufficientBalanceException {
        Map<Integer, Integer> result = atm.withdrawAmount(100);
        assertEquals(2, result.get(50).intValue());
    }

    @Test
    void testWithdrawAmountInsufficientBalance() {
        assertThrows(InsufficientBalanceException.class, () -> atm.withdrawAmount(10000));
    }

    @Test
    void testWithdrawAmountInvalidAmount() {
        assertThrows(InvalidAmountException.class, () -> atm.withdrawAmount(15));
    }
}