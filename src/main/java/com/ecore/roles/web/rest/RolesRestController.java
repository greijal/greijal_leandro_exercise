package com.ecore.roles.web.rest;

import com.ecore.roles.model.Role;
import com.ecore.roles.service.RolesService;
import com.ecore.roles.web.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ecore.roles.web.dto.RoleDto.fromModel;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/roles")
public class RolesRestController {

    private final RolesService rolesService;

    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<RoleDto> createRole(
            @Valid @RequestBody RoleDto role) {
        return ResponseEntity
                .status(200)
                .body(fromModel(rolesService.createRole(role.toModel())));
    }

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<List<RoleDto>> getRoles() {

        List<Role> getRoles = rolesService.getRoles();
        List<RoleDto> roleDtoList = new ArrayList<>();

        for (Role role : getRoles) {
            RoleDto roleDto = fromModel(role);
            if ( roleDto !=  null ){
                roleDtoList.add(roleDto);
            }
        }

        return ResponseEntity
                .status(200)
                .body(roleDtoList);
    }


    @PostMapping(
            path = "/{roleId}",
            produces = {"application/json"})
    public ResponseEntity<RoleDto> getRole(
            @PathVariable UUID roleId) {

        var dataModel = fromModel(rolesService.getRole(roleId));

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
