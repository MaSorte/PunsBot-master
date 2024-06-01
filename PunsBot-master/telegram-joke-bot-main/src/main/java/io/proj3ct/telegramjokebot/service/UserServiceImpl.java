package io.proj3ct.telegramjokebot.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.proj3ct.telegramjokebot.exceptions.UsernameAlreadyExistsException;
import io.proj3ct.telegramjokebot.model.User;
import io.proj3ct.telegramjokebot.model.UserAuthority;
import io.proj3ct.telegramjokebot.model.UserRole;
import io.proj3ct.telegramjokebot.service.repos.UserRepository;
import io.proj3ct.telegramjokebot.service.repos.UserRolesRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRolesRepository userRolesRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager manager;

    @Override
    public void registration(String username, String password) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = userRepository.save(
                    new User()
                            .setId(null)
                            .setUsername(username)
                            .setPassword(passwordEncoder.encode(password))
                            .setLocked(false)
                            .setExpired(false)
                            .setEnabled(true)
            );
            userRolesRepository.save(new UserRole(null, UserAuthority.PLACE_ORDERS, user));
        } else {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public boolean changeus(String username, String ReloU) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<UserRole> role = userRolesRepository.findByRole(ReloU);

        if (user.isPresent() && role.isPresent()) {
            Long userId = user.get().getId(); // Получить id
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaUpdate<UserRole> update = cb.createCriteriaUpdate(UserRole.class);
            Root<UserRole> root = update.from(UserRole.class);

            update.set("userAuthority", role.get().getUserAuthority());
            update.where(cb.equal(root.get("id_user"), userId));

            manager.createQuery(update).executeUpdate();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<UserRole> getManUsers() {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<UserRole> query = cb.createQuery(UserRole.class);
        Root<UserRole> root = query.from(UserRole.class);

        query.select(root).where(cb.equal(root.get("userAuthority"), UserAuthority.MANAGE_ORDERS));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}