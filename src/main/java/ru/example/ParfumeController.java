package ru.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/parfume")
public class ParfumeController {

    @Autowired
    private ParfumeService parfumeService;

    @GetMapping("/new")
    public String showAddParfumeForm(Model model) {
        model.addAttribute("parfume", new Parfume());
        return "parfume-add";
    }

    @PostMapping("/new")
    public String addParfume(@ModelAttribute Parfume parfume) {
        parfumeService.saveParfume(parfume);
        return "redirect:/parfume";
    }

    @GetMapping("/edit/{id}")
    public String showEditParfumeForm(@PathVariable Long id, Model model) {
        Parfume parfume = parfumeService.getParfumeById(id);
        if (parfume != null) {
            model.addAttribute("parfume", parfume);
            return "parfume-edit";
        } else {
            return "redirect:/parfume";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateParfume(@PathVariable Long id, @ModelAttribute Parfume parfumeDetails, Model model) {
        try {
            parfumeService.updateParfume(id, parfumeDetails);
            return "redirect:/parfume";
        } catch (IllegalArgumentException e) {
            // Если возникла ошибка, добавляем её в модель и возвращаем представление для редактирования
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("parfume", parfumeDetails);
            return "parfume-edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteParfume(@PathVariable Long id) {
        parfumeService.deleteParfume(id);
        return "redirect:/parfume";
    }

    @GetMapping
    public String showAllParfume(Model model) {
        model.addAttribute("parfumes", parfumeService.getAllParfume());
        return "parfume-list";
    }

    @GetMapping("/search")
    public String searchParfumeByType(@RequestParam(value = "type", required = false) String type, Model model) {
        List<Parfume> parfumeList = new ArrayList<>();

        if (type != null && !type.isEmpty()) {
            parfumeList = parfumeService.searchByType(type);
        }

        model.addAttribute("parfumeList", parfumeList);
        model.addAttribute("searchType", type);
        return "parfume-search";
    }

    @GetMapping("/exit")
    public String exitApplication() {
        // Завершаем работу приложения
        System.exit(0);

        return "exit";
    }
}