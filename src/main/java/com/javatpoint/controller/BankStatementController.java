package com.javatpoint.controller;

import com.javatpoint.model.BankStatement;
import com.javatpoint.service.BankStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//creating RestController
@RestController
public class BankStatementController
{
//autowired the BankStatementService class
@Autowired
BankStatementService bankStatementService;
@GetMapping(value = "/account",
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
ResponseEntity<ByteArrayResource> all() throws IOException {
    List<String[]> dataLines = new ArrayList<>();
    dataLines.add(new String[]
            { "John", "Doe", "38", "Comment Data\nAnother line of comment data" });
    dataLines.add(new String[]
            { "Jane", "Doe, Jr.", "19", "She said \"I'm being quoted\"" });
    //Writing/Creating a csv file
    File csvOutputFile = new File("asdf.csv");
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
        dataLines.stream()
                .map(this::convertToCSV)
                .forEach(pw::println);
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }

    ByteArrayResource resource = new ByteArrayResource(read(csvOutputFile));


   String filename = "file.csv";
    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .contentLength(resource.contentLength())
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(resource);
}

    public byte[] read(File file) throws IOException {

        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        }finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
    // end::get-aggregate-root[]
//    public static String TYPE = "text/csv";
//    public static boolean hasCSVFormat(MultipartFile file) {
//
//        if (!TYPE.equals(file.getContentType())) {
//            return false;
//        }
//
//        return true;
//    }
    @PostMapping("/accounts")
    BankStatement newEmployee(@RequestBody BankStatement test) {

        bankStatementService.saveOrUpdate(test);
        return test;
    }
}
