package ru.inno.course.task1;
import java.util.*;

public class Account {
    private String clientName;
    private final HashMap<Currency, Integer> currencyBalance = new HashMap<>();
    private final Deque<Command> undoList = new ArrayDeque<>();
    public Account(String clientName) {
        if (clientName == null || clientName.trim().isEmpty()) {
            throw new IllegalArgumentException("Клиент счета не может быть пустым!");
        }
        this.clientName = clientName;
    }
    public void setClientName(String clientName) {
        Command und = new UndoClientName(this, this.clientName);
        this.clientName = clientName;
        undoList.push(und);
    }
    public String getClientName() {
        return clientName;
    }
    public HashMap<Currency, Integer> getCurrencyBalance() {
        return new HashMap<>(currencyBalance);
    }
    //Установка баланса по счету
    public void setAccBalance(Currency cur, Integer saldo) {
        Integer oldSaldo = null;
        if (saldo==null){
            saldo = 0;
        }
        if (saldo < 0) {
            throw new IllegalArgumentException("Остаток по счету не может быть отрицательным!");
        }
        if (currencyBalance.containsKey(cur)) {
            oldSaldo = currencyBalance.get(cur);
        }
        Command und = new UndoAccountBalance(currencyBalance, cur, oldSaldo);
        currencyBalance.put(cur, saldo);
        undoList.push(und);
    }
    //Откат последней операции по экземпляру класса
    public void undo() {
        if (undoList.isEmpty()) {
            throw new UndoException("Счет находится в первоначальном состоянии, нет шагов для undo.");
        }
        Command und = undoList.pop();
        und.undo();
    }
    //Сохранение
    public Saveable save() {
        return new AccountSave(clientName, currencyBalance);
    }
    static final class UndoClientName extends Command {
        private final Account account;
        private final String oldName;
        public UndoClientName(Account account, String oldName) {
            this.account = account;
            this.oldName = oldName;
        }
        @Override
        public void undo() {
            account.clientName = oldName;
        }
    }
    static final class UndoAccountBalance extends Command {
        private final HashMap<Currency, Integer> currencyBalance;
        private final Currency cur;
        private final Integer oldSaldo;
        public UndoAccountBalance(HashMap<Currency, Integer> currencyBalance, Currency cur, Integer saldo) {
            this.currencyBalance = currencyBalance;
            this.cur = cur;
            this.oldSaldo = saldo;
        }
        @Override
        public void undo() {
            if (oldSaldo == null) {
                currencyBalance.remove(cur);
            }
            else {
                currencyBalance.put(cur, oldSaldo);
            }
        }
    }
    @Override
    public String toString() {
        return "Account{" +
                "clientName='" + clientName + '\'' +
                ", currencyBalance=" + currencyBalance +
                '}';
    }
    private static class AccountSave implements Saveable {
        private final String clientName;
        private final HashMap<Currency, Integer> currencyBalance;
        public AccountSave(String clientName, HashMap<Currency, Integer> currencyBalance) {
            this.clientName = clientName;
            this.currencyBalance = new HashMap<>(currencyBalance);
        }
        public String getClientName() {
            return clientName;
        }
        public HashMap<Currency, Integer> getCurrencyBalance() {
            return new HashMap<>(currencyBalance);
        }
        @Override
        public String toString() {
            return "Account{" +
                    "clientName='" + clientName + '\'' +
                    ", currencyBalance=" + currencyBalance +
                    '}';
        }
    }

}
