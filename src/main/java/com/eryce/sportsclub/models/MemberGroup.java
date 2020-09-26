package com.eryce.sportsclub.models;

import com.eryce.sportsclub.dto.MemberGroupDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberGroup{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;

    public MemberGroupDto convertToDto() {
        return MemberGroupDto.builder()
                .id(id)
                .name(name)
                .build();
    }
}
