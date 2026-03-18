package ru.mescat.auth.dto;

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
