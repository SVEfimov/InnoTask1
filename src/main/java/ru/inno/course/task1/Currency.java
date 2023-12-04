package ru.inno.course.task1;

public enum Currency {
    RUB("Рубль"),
    USD("Доллар США"),
    EUR("Евро");
    private String name;
    Currency(String name) {
        this.name = name;
    }


}
