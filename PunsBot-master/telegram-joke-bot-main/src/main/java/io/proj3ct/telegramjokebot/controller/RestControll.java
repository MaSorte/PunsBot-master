package io.proj3ct.telegramjokebot.controller;

import io.proj3ct.telegramjokebot.model.PuriPuns;
import io.proj3ct.telegramjokebot.model.User;
import io.proj3ct.telegramjokebot.model.UserRole;
import io.proj3ct.telegramjokebot.service.PunService;
import io.proj3ct.telegramjokebot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@Slf4j
@RequestMapping("/api")
public class RestControll {

    private final PunService punService;

    private final UserService userService;

    public RestControll(PunService punService, UserService userService) {
        this.punService = punService;
        this.userService = userService;
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
    @PreAuthorize("hasAuthority('FULL')")
    public List<PuriPuns> getAllPuns() {
        return punService.getAllPuns();
    }

    @GetMapping("/pun/{id}")
    public Optional<PuriPuns> getPunById(@PathVariable Long id) {
        return punService.getPunById(id);
    }

    @GetMapping("/randpun")
    public Optional<PuriPuns> rand_pun() {
        long pp = punService.getPunCount(); //получаем общее количество
        Random random = new Random();
        long randomNumber = random.nextInt((int) pp);
        return punService.getPunById(randomNumber);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ORDERS')")
    public ResponseEntity<String> deletePunById(@RequestBody String id) {
        if (punService.deletePun(Long.valueOf(id))) {
            return ResponseEntity.ok("Шутка с ID " + id + " удалена.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PLACE_ORDERS')")
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

    @PostMapping("/change_user")
    @PreAuthorize("hasAuthority('FULL')")
    public ResponseEntity<String> changeUser(@RequestBody String username, @RequestBody String ReloU) {
        if (userService.changeus(username, ReloU)) {
            return ResponseEntity.ok("Данные пользователя обновлены.");
        } else {
            return ResponseEntity.badRequest().body("Что-то пошло не так.");
        }
    }

    @GetMapping("/manuser")
    @PreAuthorize("hasAuthority('FULL')")
    public List<UserRole> getManUsers() {
        return userService.getManUsers();
    }

}
