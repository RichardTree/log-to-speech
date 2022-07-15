package com.rickyt.logtospeech.controllers;


import generated.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.JAXBIntrospector;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class FileInputController {

    @RequestMapping(name = "/fileUpload", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    ResponseEntity  fileUpload(@RequestParam("file") MultipartFile file) throws IOException, JAXBException {

        JAXBContext jc = JAXBContext.newInstance(FromType.class, InvitationResponseType.class, InvitationType.class, JoinType.class, LeaveType.class, LogType.class, MessageType.class, ObjectFactory.class, TextType.class, ToType.class, UserType.class);
//        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
//
        Unmarshaller unmarshaller = jc.createUnmarshaller();
//
        LogType ts = (LogType) JAXBIntrospector.getValue(unmarshaller.unmarshal(file.getInputStream()));

        String filePath = "PATH_HERE";
        InputStream inputStream = new FileInputStream(filePath);
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(Files.size(Paths.get(filePath)));
        return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);

    }

    @Operation(  // Swagger/OpenAPI 3.x annotation to describe the endpoint
            summary = "Small summary of the end-point",
            description = "A detailed description of the end-point"
    )
    @PostMapping(
            value = "/uploads",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}  // Note the consumes in the mapping
    )
    public void uploadMultipleFiles (

            // ------------ Multipart Object ------------

//            @Parameter(description = "Additional request data")  // Swagger/OpenAPI annotation
//            @RequestParam("req") RequestDTO request,             // Spring annotation

            // ------------ File uploads go next ------------

            @Parameter(
                    description = "Files to be uploaded",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)  // Won't work without OCTET_STREAM as the mediaType.
            )
            @RequestParam("files") MultipartFile file  // Spring annotation
    ){

    }

    @PostMapping("/custom")
    public String custom() {
        return "custom";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        return "";
    }


}
