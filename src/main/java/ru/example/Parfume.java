package ru.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parfum_store")
public class Parfume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String description;
    private double weight;
    private double price;

    // Конструктор с id
    public Parfume(Long id, String name, String type, String description, double weight, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.weight = weight;
        this.price = price;
    }

    public Parfume() {

    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public double getWeight() { return weight; }
    public double getPrice() { return price; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Парфюм [id=" + id + ", название=" + name + ", тип=" + type +
                ", описание=" + description + ", вес=" + weight + ", цена=" + price + "]";
    }
}
