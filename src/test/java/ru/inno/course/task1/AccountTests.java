package ru.inno.course.task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTests {
    private Account testAccount;

    @BeforeEach
    public void setUpTests() {
        testAccount = new Account("Тестовый Клиент");
    }

    // Проверка исключения при создании Account с пустым именем клиента
    @Test
    public void testEmptyClientNameException() {
        boolean exceptionThrown = false;
        try {
            Account testAcc = new Account("  ");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    //Проверка исключения при добавлении отрицательного остатка валюты
    @Test
    public void testNegativeSaldoException(){
        boolean exceptionThrown = false;
        try {
            testAccount.setAccBalance(Currency.RUB, -800);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    //Проверка UNDO && SAVE
    @Test
    public void testUndAndSave(){
        testAccount.setAccBalance(Currency.USD, 20);
        Saveable saveAccount = testAccount.save();
        testAccount.setAccBalance(Currency.USD, 15);
        testAccount.setClientName("Измененный тестовый клиент");
        testAccount.setAccBalance(Currency.RUB, 1000);
        testAccount.undo();
        testAccount.setClientName("Измененный тестовый клиент2");
        testAccount.setAccBalance(Currency.RUB, 2000);
        testAccount.undo();
        testAccount.undo();
        testAccount.undo();
        testAccount.undo();
        assertEquals(testAccount.toString(), saveAccount.toString());
    }

    //Проверка исключения при добавлении отрицательного остатка валюты
    @Test
    public void testUndoException(){
        boolean exceptionThrown = false;
        try {
            testAccount.setAccBalance(Currency.RUB, 20000);
            testAccount.undo();
            testAccount.undo();
        } catch (UndoException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }
}
