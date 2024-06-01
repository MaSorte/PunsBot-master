package io.proj3ct.telegramjokebot.service.repos;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import io.proj3ct.telegramjokebot.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}