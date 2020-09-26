package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.CompetitionApplication;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionApplicationDto {

    private Integer id;
    private CompetitionDto competition;
    private AppUserDto appUser;
    private String message;

    public CompetitionApplication convertToEntity() {
        return CompetitionApplication.builder()
                .competition(competition.convertToEntity())
                .id(id)
                .message(message)
                .appUser(appUser.convertToEntity())
                .build();
    }
}
