package com.emre.konumnot.context;

import com.emre.konumnot.model.User;

/**
 * Mevcut istekteki kullanıcıyı tutar. UserFilter tarafından set edilir.
 */
public final class UserContext {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void setUser(User user) {
        currentUser.set(user);
    }

    public static User getUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
