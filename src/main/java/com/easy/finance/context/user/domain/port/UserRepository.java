package com.easy.finance.context.user.domain.port;

import com.easy.finance.context.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public List<User> findAll();
    public Optional<User> findById(Long id);
    public Optional<User> findByEmail(String email);
    public User create(User user);
    public User update(User user);
    public void deleteById(Long id);

}
