package ru.mescat.security.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegDto {
    private String username;
    private String password;
}
