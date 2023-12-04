package ru.inno.course.task1;


public class Main {
    public static void main(String[] args) {
        Account acc = new Account("Вася");
        System.out.println(acc);
        acc.setClientName("Петя");
        System.out.println(acc);
        acc.setClientName("Ваня");
        System.out.println(acc);
        acc.setAccBalance(Currency.RUB,20);
        System.out.println(acc);
        acc.setAccBalance(Currency.RUB,21);
        System.out.println(acc);
        acc.setAccBalance(Currency.EUR,10);
        System.out.println(acc);
        acc.setClientName("Дима");
        System.out.println(acc);
        acc.setAccBalance(Currency.EUR,15);
        System.out.println(acc + "<-Save")
        ;
        Saveable save = acc.save();
        System.out.println("--------------UNDO------------------");
        acc.undo();
        System.out.println(acc);
        acc.undo();
        System.out.println(acc);
        acc.undo();
        System.out.println(acc);
        acc.undo();
        System.out.println(acc);
        acc.undo();
        System.out.println(acc);
        acc.undo();
        System.out.println(acc);
        acc.undo();
        System.out.println(acc);
        acc.undo();
        System.out.println("-------------------Save result:");
       System.out.println(save.toString());
    }
}
