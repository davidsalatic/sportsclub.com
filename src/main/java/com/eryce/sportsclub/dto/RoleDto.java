package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {

    private Integer id;
    private String name;

    public Role convertToEntity() {
        return Role.builder()
                .id(id)
                .name(name)
                .build();
    }
}
