package io.proj3ct.telegramjokebot.controller;

import io.proj3ct.telegramjokebot.model.PuriPuns;
import io.proj3ct.telegramjokebot.service.PunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@Slf4j
@RequestMapping("/api")
public class RestControll {
    private final PunService punService;

    public RestControll(PunService punService) {
        this.punService = punService;
    }

    @GetMapping("/start")
    public ResponseEntity<String> startCommand() {
        int tt = (int) punService.getPunCount();
        String message = "Привет, дорогой Пользователь.\nКоличество доступных шуток: " + tt;
        return ResponseEntity.ok(message);
    }

    @GetMapping("/help")
    public ResponseEntity<String> helpCommand() {
        String message = """
            Эта Система не знает, как может помочь Пользователю.
            У этой Системы нет для тебя совета.\s
            (ᓀ ᓀ)
            Хочешь шутку, Пользователь? /pun""";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/pun")
    public ResponseEntity<String> getPun() {
        String pun = punService.getPunn();
        return ResponseEntity.ok(pun);
    }

    @GetMapping("/allpuns")
    public List<PuriPuns> getAllPuns() {
        return punService.getAllPuns();
    }

    @GetMapping("/pun/{id}")
    public Optional<PuriPuns> getPunById(@PathVariable Long id) {
        return punService.getPunById(id);
    }

    @GetMapping("/randpun")
    public ResponseEntity<String> rand_pun() {
        long pp = punService.getPunCount(); //получаем общее количество
        Random random = new Random();
        long randomNumber = random.nextInt((int) pp);
        String pun = String.valueOf(punService.getPunById(randomNumber)); //передаем и получаем
        return ResponseEntity.ok(pun);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePunById(@RequestBody String id) {
        if (punService.deletePun(Long.valueOf(id))) {
            return ResponseEntity.ok("Шутка с ID " + id + " удалена.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPun(@RequestBody PuriPuns pun) {
        if (punService.registerPun(pun)) {
            return ResponseEntity.ok("Шутка добавлена.");
        } else {
            return ResponseEntity.badRequest().body("Шутка с таким ID уже существует.");
        }
    }

    @GetMapping("/rating")
    public List<PuriPuns> getRating() {
        return punService.ratPan();
    }

}
