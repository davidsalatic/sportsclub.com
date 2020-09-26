package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.RoleDto;
import com.eryce.sportsclub.models.Role;
import com.eryce.sportsclub.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public List<RoleDto> getAll() {
        return convertToDto(roleRepository.findAll());
    }

    private List<RoleDto> convertToDto(List<Role> roles) {
        List<RoleDto> rolesDto = new ArrayList<>();
        for (Role role : roles) {
            rolesDto.add(role.convertToDto());
        }
        return rolesDto;
    }

    public RoleDto getByName(String name) {
        Role role = roleRepository.findByNameIgnoreCase(name);
        if (role == null) {
            throw new EntityNotFoundException();
        }
        return role.convertToDto();
    }

    public RoleDto insert(RoleDto roleDto) {
        Role role = roleRepository.save(roleDto.convertToEntity());
        return role.convertToDto();
    }
}
