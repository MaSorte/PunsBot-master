package io.proj3ct.telegramjokebot.service;

import io.proj3ct.telegramjokebot.model.UserRole;

import java.util.List;

public interface UserService {
    void registration(String username, String password);
    boolean changeus(String username, String ReloU);
    List<UserRole> getManUsers();
}
