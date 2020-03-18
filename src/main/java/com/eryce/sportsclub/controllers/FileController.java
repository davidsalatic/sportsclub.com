package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.FileRequestDTO;
import com.eryce.sportsclub.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(Routes.FILE_BASE+"/upload")
    public String parseCSV(@RequestBody FileRequestDTO fileRequestDTO)
    {
        return fileService.parseCsv(fileRequestDTO);
    }
}
