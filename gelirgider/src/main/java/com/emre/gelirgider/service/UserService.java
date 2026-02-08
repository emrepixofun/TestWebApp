package com.emre.gelirgider.service;

import com.emre.gelirgider.model.User;
import com.emre.gelirgider.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service("gelirgiderUserService")
public class UserService {

    private final UserRepository userRepository;

    /**
     * UUID ile kullanıcı getirir; yoksa oluşturur. Bir kez oluşturulduktan sonra UUID asla değişmez.
     */
    @Transactional
    public User getOrCreateByUuid(String uuid) {
        return userRepository.findByUuid(uuid)
                .orElseGet(() -> {
                    User user = new User();
                    user.setUuid(uuid);
                    return userRepository.save(user);
                });
    }
}
