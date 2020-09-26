package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.RoleDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public RoleDto convertToDto() {
        return RoleDto.builder()
                .id(id)
                .name(name)
                .build();
    }
}
