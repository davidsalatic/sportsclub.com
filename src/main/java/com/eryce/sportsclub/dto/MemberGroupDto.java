package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.MemberGroup;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberGroupDto {

    private Integer id;
    private String name;

    public MemberGroup convertToEntity() {
        return MemberGroup.builder()
                .id(id)
                .name(name)
                .build();
    }
}
