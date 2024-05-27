package io.proj3ct.telegramjokebot.service;

import io.proj3ct.telegramjokebot.model.PunRepository;
import io.proj3ct.telegramjokebot.model.PuriPuns;

import java.util.List;
import java.util.Random;
import java.util.Optional;

public class PunServiceImprt implements PunService{

    private PunRepository punRepository;

    @Override
    public boolean registerPun(PuriPuns pun) {
        punRepository.save(pun);
        return true;
    }

    private int currentPunIndex = 0;
    @Override
    public String getPunn() {
        List<PuriPuns> puns = punRepository.findAll();
        if (puns.isEmpty()) {
            return "Извини, но у меня пока нет шуток!";
        } else if (currentPunIndex >= puns.size()) {
            currentPunIndex = 0; // если конец списка
        }
        PuriPuns pun = puns.get(currentPunIndex); // получаем шутку. почему не функция с айди??? не забыть!
        currentPunIndex++;
        return String.valueOf(pun);
    }

    @Override
    public long getPunCount() {
        return punRepository.count();
    }

    @Override
    public List<PuriPuns> getAllPuns() {
        return punRepository.findAll();
    }

    @Override
    public Optional<PuriPuns> getPunById(Long id) {
        return punRepository.findById(id);
    }

    @Override
    public void ratPan(PuriPuns pun) {

    }

    @Override
    public boolean deletePun(Long id) {
        Optional<PuriPuns> punToDelete = punRepository.findById(id);
        if (punToDelete.isPresent()) {
            punRepository.delete(punToDelete.get());
            return true;
        } else {
            return false;
        }
    }

    //private final Random random = new Random();
    @Override
    public void rand_pun(int pp){
        PuriPuns pun = new PuriPuns();
        int randomIndex = new Random().nextInt(pp);
    }

}