package com.emre.konumnot.service;

import com.emre.konumnot.model.User;
import com.emre.konumnot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service("konumnotUserService")
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User getOrCreateByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid)
                .orElseGet(() -> {
                    User user = new User();
                    user.setUuid(uuid);
                    User saved = userRepository.save(user);
                    log.info("Yeni kullanıcı oluşturuldu: uuid={}", uuid);
                    return saved;
                });
    }
}
