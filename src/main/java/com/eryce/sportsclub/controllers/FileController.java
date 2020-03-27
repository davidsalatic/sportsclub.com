package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Authorize;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.FileRequestDTO;
import com.eryce.sportsclub.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(Routes.FILE_BASE)
@PreAuthorize(Authorize.HAS_COACH_OR_MANAGER_ROLE)
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String parseCSV(@RequestBody FileRequestDTO fileRequestDTO)
    {
        return fileService.parseCsv(fileRequestDTO);
    }
}
