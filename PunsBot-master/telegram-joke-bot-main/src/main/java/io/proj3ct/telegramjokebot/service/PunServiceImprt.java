package io.proj3ct.telegramjokebot.service;

import io.proj3ct.telegramjokebot.service.repos.PunRepository;
import io.proj3ct.telegramjokebot.model.PuriPuns;

import java.util.List;
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
            return "Извини, но у меня нет шуток!";
        } else if (currentPunIndex >= puns.size()) {
            currentPunIndex = 0; // если конец списка
        }
        PuriPuns pun = puns.get(currentPunIndex);
        pun.setRating(pun.getRating() + 1);
        punRepository.save(pun);

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
        Optional<PuriPuns> pun = punRepository.findById(id);
        if (pun.isPresent()) {
            pun.get().setRating(pun.get().getRating() + 1); // Изменяем значение
            punRepository.save(pun.get());
        }
        return pun;
    }

    @Override
    public List<PuriPuns> ratPan() {
        return punRepository.Top5ByRating();
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

}
