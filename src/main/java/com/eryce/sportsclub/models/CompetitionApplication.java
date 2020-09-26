package com.eryce.sportsclub.models;


import com.eryce.sportsclub.dto.CompetitionApplicationDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    private String message;

    public CompetitionApplicationDto convertToDto() {
        return CompetitionApplicationDto.builder()
                .competition(competition.convertToDto())
                .id(id)
                .message(message)
                .appUser(appUser.convertToDto())
                .build();
    }
}
