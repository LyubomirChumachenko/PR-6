package ru.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParfumeService {

    @Autowired
    private ParfumeRepository parfumeRepository;

    public List<Parfume> getAllParfume() {
        return parfumeRepository.findAll();
    }

    public Parfume getParfumeById(Long id) {
        return parfumeRepository.findById(id).orElse(null);
    }

    public void saveParfume(Parfume parfume) {
        parfumeRepository.save(parfume);
    }

    public void deleteParfume(Long id) {
        parfumeRepository.deleteById(id);
    }

    public List<Parfume> searchByType(String type) {
        return parfumeRepository.findByType(type);
    }

    public void updateParfume(Long id, Parfume parfumeDetails) {
        Optional<Parfume> existingParfume = parfumeRepository.findById(id);

        if (existingParfume.isPresent()) {
            Parfume parfume = existingParfume.get();

            // Проверяем длину имени
            if (parfumeDetails.getName().length() > 20) {
                // Выводим ошибку на экран
                throw new IllegalArgumentException("Имя не должно превышать 20 символов.");
            }else {
                parfume.setName(parfumeDetails.getName());
            }
            // Проверяем длину поля "price"
            if (parfumeDetails.getPrice() < 0 || parfumeDetails.getPrice() > 100000) {
                throw new IllegalArgumentException("Цена должна быть в диапазоне от 0 до 100000.");
            }else {
                parfume.setPrice(parfumeDetails.getPrice());
            }

            parfume.setType(parfumeDetails.getType());
            parfume.setDescription(parfumeDetails.getDescription());
            parfume.setWeight(parfumeDetails.getWeight());


            parfumeRepository.save(parfume);
        } else {
            // Обработка случая, когда parfume не найден
        }
    }
}