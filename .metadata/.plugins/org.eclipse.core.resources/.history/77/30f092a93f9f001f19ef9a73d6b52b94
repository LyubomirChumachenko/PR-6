package ru.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        // Загрузка контекста Spring
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Получение объекта автомобиля с бензиновым двигателем
        Car car = (Car) context.getBean("car");
        car.showDetails();

        // Получение объекта автомобиля с дизельным двигателем и значением из внешнего файла
        Car carWithDiesel = (Car) context.getBean("carWithDiesel");
        carWithDiesel.showDetails();
    }
}
