package com.store.dto;

import com.store.models.User;

public record NewUserDTO(
    String name,
    String password,
    String storeName
) {
    public static boolean isDTOInvalid(NewUserDTO newUserDTO) {
        return newUserDTO == null ||
                newUserDTO.name == null || newUserDTO.name.isEmpty() || newUserDTO.name.isBlank() ||
                newUserDTO.password == null ||newUserDTO.password.isEmpty() || newUserDTO.password.isBlank() ||
                newUserDTO.storeName == null  || newUserDTO.storeName.isBlank() || newUserDTO.storeName.isEmpty();
    }

    public static User toUser(NewUserDTO newUserDTO) {
        return new User(newUserDTO.name, newUserDTO.password, newUserDTO.storeName);
    }
}
