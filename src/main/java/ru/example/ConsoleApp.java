package ru.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp implements CommandLineRunner {

    @Autowired
    private ParfumeRepository parfumeRepository;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println("\n1. Добавить парфюм\n2. Показать все позиции\n3. Редактировать по ID\n4. Удалить по ID\n5. Искать по типу\n6. Выход");
            
            // Безопасное чтение ввода с проверкой
            String input = scanner.nextLine().trim();
            int choice;
            
            // Проверка на пустой ввод и корректное преобразование
            try {
                if (input.isEmpty()) {
                    System.out.println("Ввод не может быть пустым! Пожалуйста, выберите опцию (1-6).");
                    continue; // Вернуться к началу цикла
                }
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число от 1 до 6.");
                continue; // Вернуться к началу цикла
            }

            try {
                switch (choice) {
                    case 1 -> addParfume();
                    case 2 -> showAllParfume();
                    case 3 -> editParfume();
                    case 4 -> deleteParfume();
                    case 5 -> searchParfumeByType();
                    case 6 -> {
                        System.out.println("Выход из программы...");
                        return; // Выход из метода run (и соответственно из программы)
                    }
                    default -> System.out.println("Неправильный выбор! Пожалуйста, выберите опцию от 1 до 6.");
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void addParfume() {
        try {
            System.out.print("Введите название: ");
            String name = scanner.nextLine();
            System.out.print("Введите тип парфюма: ");
            String type = scanner.nextLine();
            System.out.print("Введите описание: ");
            String description = scanner.nextLine();
            System.out.print("Введите вес: ");
            double weight = Double.parseDouble(scanner.nextLine());
            System.out.print("Введите цену: ");
            double price = Double.parseDouble(scanner.nextLine());

            // Получаем максимальный существующий id из базы данных
            long nextId = parfumeRepository.getMaxId() + 1;

            // Если записей нет (максимальный id = 0), устанавливаем id = 1
            if (nextId < 1) {
                nextId = 1;
            }

            // Создаем новый объект Parfume с определенным id
            Parfume parfume = new Parfume(nextId, name, type, description, weight, price);
            parfumeRepository.save(parfume);
            System.out.println("Запись о парфюме добавлена: " + parfume);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат числа");
        } catch (Exception e) {
            System.out.println("Произошла ошибка при добавлении парфюма: " + e.getMessage());
        }
    }

    private void showAllParfume() {
        List<Parfume> parfumeList = parfumeRepository.findAll();
        parfumeList.forEach(System.out::println);
    }

    private void editParfume() {
        System.out.print("Введите ID парфюма для редактирования: ");
        Long id = Long.parseLong(scanner.nextLine());

        Parfume parfume = parfumeRepository.findById(id).orElse(null);
        if (parfume == null) {
            System.out.println("Парфюм не найден!");
            return;
        }

        System.out.print("Введите новое название парфюма: ");
        parfume.setName(scanner.nextLine());
        System.out.print("Введите новый тип парфюма: ");
        parfume.setType(scanner.nextLine());
        System.out.print("Введите новое описание парфюма: ");
        parfume.setDescription(scanner.nextLine());
        System.out.print("Введите новый вес парфюма: ");
        parfume.setWeight(Double.parseDouble(scanner.nextLine()));
        System.out.print("Введите новую цену парфюма: ");
        parfume.setPrice(Double.parseDouble(scanner.nextLine()));

        parfumeRepository.save(parfume);
        System.out.println("Информация о парфюме обновлена: " + parfume);
    }

    private void deleteParfume() {
        System.out.print("Введите ID для удаления парфюма: ");
        Long id = Long.parseLong(scanner.nextLine());
        parfumeRepository.deleteById(id);
        System.out.println("Парфюм удален: " + id);
    }

    private void searchParfumeByType() {
        System.out.print("Введите тип парфюма для поиска: ");
        String type = scanner.nextLine();
        List<Parfume> results = parfumeRepository.findByType(type);
        results.forEach(System.out::println);
    }
}
