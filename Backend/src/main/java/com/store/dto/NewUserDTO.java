package com.store.dto;

import com.store.models.User;

public record NewUserDTO(
    String name,
    String password,
    String storeName
) {
    public static User toUser(NewUserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name);
        user.setPassword(userDTO.password);
        user.getStore().setName(userDTO.storeName);

        return user;
    }
}
