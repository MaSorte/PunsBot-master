package io.proj3ct.telegramjokebot.service;

import io.proj3ct.telegramjokebot.model.PuriPuns;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Controller
public interface PunService {

    boolean registerPun(PuriPuns pun); //сохранить шутку

    String getPunn();

    long getPunCount(); //количество шуток в базе

    List<PuriPuns> getAllPuns(); // все шутки

    Optional<PuriPuns> getPunById(Long id); // шутка по id

    List<PuriPuns> ratPan(); //рейтинг шуток

    boolean deletePun(Long id); // удалить шутку
}