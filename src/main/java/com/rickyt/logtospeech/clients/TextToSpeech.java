/* (C)2022 */
package com.rickyt.logtospeech.clients;

import com.google.cloud.texttospeech.v1.*;
import com.rickyt.logtospeech.domain.Message;
import com.rickyt.logtospeech.domain.Person;
import com.rickyt.logtospeech.domain.Text;
import com.rickyt.logtospeech.domain.Voice;
import io.vavr.control.Try;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Facade of {@link TextToSpeechClient}. Asynchronously submits all {@link Message}'s to the <a
 * href="https://cloud.google.com/text-to-speech/docs/reference/rest">Cloud Text-to-Speech API</a>
 */
@Component
public class TextToSpeech {

  private static final Logger LOGGER = Logger.getLogger(TextToSpeech.class.getName());

  private TextToSpeechClient textToSpeechClient;

  @PostConstruct
  public void init() {
    textToSpeechClient =
        Try.of(TextToSpeechClient::create)
            .andThen(() -> LOGGER.info("TextToSpeechClient created successfully"))
            .getOrElseThrow(
                throwable ->
                    new IllegalStateException(
                        "Unable to initialise TextToSpeechClient", throwable));
  }

  @PreDestroy
  public void shutdown() {
    textToSpeechClient.shutdown();
    LOGGER.info("TextToSpeechClient shutdown successfully");
  }

  public List<SynthesizeSpeechResponse> generateAudioFrom(
      final List<Message> messages, final Map<Person, Voice> personVoiceMap) {
    return messages.stream()
        .map(message -> generateAudioFrom(message, personVoiceMap))
        .toList()
        .stream()
        .map(CompletableFuture::join)
        .toList();
  }

  @Async
  public CompletableFuture<SynthesizeSpeechResponse> generateAudioFrom(
      final Message message, final Map<Person, Voice> personVoiceMap) {

    LOGGER.info(String.format("Attempting call with message: [%s]", message.text().value()));
    return CompletableFuture.completedFuture(
        textToSpeechClient.synthesizeSpeech(
            generateSynthesisInput(message.text()),
            configureVoiceSettings(personVoiceMap.get(message.sender())),
            audioConfig()));
  }

  private AudioConfig audioConfig() {
    return AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();
  }

  private VoiceSelectionParams configureVoiceSettings(final Voice voice) {
    return VoiceSelectionParams.newBuilder()
        .setLanguageCode(voice.languageCode())
        .setName(voice.voiceName())
        .setSsmlGender(SsmlVoiceGender.MALE)
        .build();
  }

  private SynthesisInput generateSynthesisInput(final Text text) {
    return SynthesisInput.newBuilder().setText(text.value()).build();
  }
}
