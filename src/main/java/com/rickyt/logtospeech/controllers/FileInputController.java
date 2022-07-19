/* (C)2022 */
package com.rickyt.logtospeech.controllers;

import static com.rickyt.logtospeech.domain.Error.*;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rickyt.logtospeech.domain.*;
import com.rickyt.logtospeech.domain.Error;
import com.rickyt.logtospeech.domain.File;
import com.rickyt.logtospeech.services.SpeechParser;
import com.rickyt.logtospeech.services.XmlParser;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileInputController {

  private static final Logger LOGGER = Logger.getLogger(FileInputController.class.getName());

  private final XmlParser xmlParser;
  private final SpeechParser speechParser;

  public FileInputController(final XmlParser xmlParser, final SpeechParser speechParser) {
    this.xmlParser = xmlParser;
    this.speechParser = speechParser;
  }

  @RequestMapping(path = "/domainJsonExample", method = GET)
  public ResponseEntity<File> domainJsonExample() {
    return ResponseEntity.ok(generateDomainJsonExample());
  }

  private ImmutableFile generateDomainJsonExample() {
    final var message =
        ImmutableMessage.builder()
            .text(ImmutableText.of("Test"))
            .sender(ImmutablePerson.of("Rich"))
            .addRecipients(ImmutablePerson.of("John"), ImmutablePerson.of("Tom"))
            .timeStamp(ImmutableTimeStamp.of(now()))
            .build();

    return ImmutableFile.builder()
        .fileId(ImmutableFileId.of(UUID.randomUUID()))
        .fileName(ImmutableFileName.of("Test.txt"))
        .origin(Origin.JSON)
        .addAllMessages(List.of(message, message, message))
        .build();
  }

  @RequestMapping(path = "/lol", method = GET)
  public ResponseEntity<File> kek() throws IOException {

    final var mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    final var inputStream =
        requireNonNull(
            getClass().getClassLoader().getResourceAsStream("response_1657996946594.json"));

    final var file = mapper.readValue(inputStream, File.class);

    return ResponseEntity.ok(file);
  }

  @RequestMapping(
      path = "/fileUpload/xml",
      name = "XML FileUpload",
      method = POST,
      consumes = {"multipart/form-data"})
  public ResponseEntity<byte[]> fileUploadXml(@RequestPart() MultipartFile inputFile) {

    return extractFileAndFileName(inputFile)
        .peek(this::logFileReceived)
        .flatMap(
            inputStreamAndFilename ->
                xmlParser.parse(inputStreamAndFilename._1, inputStreamAndFilename._2))
        .flatMap(speechParser::parse)
        .map(ResponseEntity::ok)
        .getOrElseGet(this::handleError);
  }

  @RequestMapping(
      path = "/fileUpload/jsonValidator",
      name = "JSON Validator",
      method = POST,
      consumes = {"application/json"})
  public ResponseEntity<byte[]> jsonValidator(@RequestBody File file) {

    return ResponseEntity.ok().build();
  }

  private void logFileReceived(
      final Tuple2<InputStream, ImmutableFileName> inputStreamAndFilename) {
    LOGGER.info(
        format("File received at: %s . Filename: %s", now(), inputStreamAndFilename._2.value()));
  }

  private Either<Error, Tuple2<InputStream, ImmutableFileName>> extractFileAndFileName(
      final MultipartFile file) {
    return Try.of(
            () ->
                new Tuple2<>(
                    file.getInputStream(),
                    ImmutableFileName.of(requireNonNull(file.getOriginalFilename()))))
        .toEither(UNABLE_TO_EXTRACT_FILENAME_OR_FILE);
  }

  private <T> ResponseEntity<T> handleError(final Error error) {
    return switch (error) {
      case UNABLE_TO_GENERATE_MP3 -> internalServerError().build();
      case UNABLE_TO_EXTRACT_FILENAME_OR_FILE, UNABLE_TO_PARSE_XML -> badRequest().build();
    };
  }
}
