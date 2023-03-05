package com.ecore.roles.web.rest;

import com.ecore.roles.web.dto.TeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ecore.roles.web.dto.TeamDto.fromModel;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/teams")
public class TeamsRestController  {

    private final TeamsService teamsService;

    @PostMapping(
            produces = {"application/json"})
    public ResponseEntity<List<TeamDto>> getTeams() {
        return ResponseEntity
                .status(200)
                .body(teamsService.getTeams().stream()
                        .map(TeamDto::fromModel)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    @PostMapping(
            path = "/{teamId}",
            produces = {"application/json"})
    public ResponseEntity<TeamDto> getTeam(
            @PathVariable UUID teamId) {

       var dataModel = fromModel(teamsService.getTeam(teamId));

       if(dataModel == null){
           return ResponseEntity
                   .notFound()
                   .build();
       }

        return ResponseEntity
                .status(200)
                .body(dataModel);
    }

}
