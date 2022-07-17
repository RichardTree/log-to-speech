/* (C)2022 */
package com.rickyt.logtospeech.services;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import com.rickyt.logtospeech.clients.TextToSpeech;
import com.rickyt.logtospeech.domain.*;
import com.rickyt.logtospeech.domain.Error;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/** Generates .mp3 speech vocalisation of the input {@link File}. */
@Service
public class SpeechParser {

  private static final Logger LOGGER = Logger.getLogger(SpeechParser.class.getName());

  private final TextToSpeech textToSpeech;

  public SpeechParser(TextToSpeech textToSpeech) {
    this.textToSpeech = textToSpeech;
  }

  public Either<Error, byte[]> parse(final File file) {
    return Try.of(() -> generateAudioFrom(file)).toEither(Error.UNABLE_TO_GENERATE_MP3);
  }

  private byte[] generateAudioFrom(final File file) {
    return textToSpeech.generateAudioFrom(file.messages(), file.generateVoiceMap()).stream()
        .map(SynthesizeSpeechResponse::getAudioContent)
        .map(ByteString::toByteArray)
        .collect(ByteArrayOutputStream::new, (b, e) -> b.write(e, 0, e.length), (a, b) -> {})
        .toByteArray();
  }
}
