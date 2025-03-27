package ru.example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParfumeRepository extends JpaRepository<Parfume, Long> {
    List<Parfume> findByType(String type);

    @Query("SELECT COALESCE(MAX(p.id), 0) FROM Parfume p")
    Long getMaxId();
}