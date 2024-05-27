package io.proj3ct.telegramjokebot.service;

import io.proj3ct.telegramjokebot.config.BotConfig;
import io.proj3ct.telegramjokebot.model.PunRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.*;

@Component
@Slf4j
public abstract class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "начало работы"));
        listOfCommands.add(new BotCommand("/pun", "выведет шутку"));
        listOfCommands.add(new BotCommand("/random_pun", "выведет рандомную шутку"));
        listOfCommands.add(new BotCommand("/all_pun", "выведет все шутки"));
        listOfCommands.add(new BotCommand("/add", "шутку писать за командой"));
        listOfCommands.add(new BotCommand("/pun_id", "id писать за командой"));
        listOfCommands.add(new BotCommand("/help", "ничего такого"));
        listOfCommands.add(new BotCommand("/del", "удалить шутку"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
