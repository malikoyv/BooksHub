package com.malikoyv.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogsController {

    private static final String LOG_FILE_PATH = "logs/application.log";

    @GetMapping
    public List<String> getLogs(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "20") int size) throws IOException {
        List<String> allLines = Files.readAllLines(Path.of(LOG_FILE_PATH));
        int fromIndex = Math.min(page * size, allLines.size());
        int toIndex = Math.min(fromIndex + size, allLines.size());
        return allLines.subList(fromIndex, toIndex);
    }
}
