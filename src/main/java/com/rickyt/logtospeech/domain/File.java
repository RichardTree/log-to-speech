/* (C)2022 */
package com.rickyt.logtospeech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.immutables.value.Value;

@JsonSerialize(as = ImmutableFile.class)
@JsonDeserialize(builder = ImmutableFile.Builder.class)
@Value.Immutable
public abstract class File {

  @JsonProperty()
  public abstract FileName fileName();

  @JsonProperty()
  public abstract Origin origin();

  @JsonProperty()
  public abstract FileId fileId();

  @JsonProperty()
  public abstract List<Message> messages();

  @JsonIgnore
  private Set<Person> generateUniquePeople() {
    final var senderStream = messages().stream().map(Message::sender);

    final var recipientStream =
        messages().stream().flatMap(message -> message.recipients().stream());

    return Stream.concat(senderStream, recipientStream).collect(Collectors.toSet());
  }

  @JsonIgnore
  private Voice generateRandomVoice() {
    return Arrays.stream(Voice.values()).toList().get(new Random().nextInt(Voice.values().length));
  }

  @JsonIgnore
  public Map<Person, Voice> generateVoiceMap() {
    return generateUniquePeople().stream()
        .collect(Collectors.toMap(t -> t, y -> generateRandomVoice()));
  }
}
