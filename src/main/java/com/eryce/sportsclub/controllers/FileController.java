package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.FileDto;
import com.eryce.sportsclub.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.eryce.sportsclub.constants.Authorize.HAS_COACH_OR_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.FILE_BASE;

@RestController
@CrossOrigin
@RequestMapping(FILE_BASE)
@PreAuthorize(HAS_COACH_OR_MANAGER_ROLE)
@AllArgsConstructor
public class FileController {

    private FileService fileService;

    @PostMapping("/upload")
    public String parseCSV(@RequestBody FileDto fileDto)
    {
        return fileService.parseCsv(fileDto);
    }
}
