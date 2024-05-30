package io.proj3ct.telegramjokebot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PunRepository extends JpaRepository<PuriPuns, Long> {

    @Query("SELECT p FROM PuriPuns p ORDER BY p.rating DESC LIMIT 5")
    List<PuriPuns> Top5ByRating();
}
