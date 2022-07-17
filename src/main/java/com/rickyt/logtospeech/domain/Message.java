/* (C)2022 */
package com.rickyt.logtospeech.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import org.immutables.value.Value;

@JsonSerialize(as = ImmutableMessage.class)
@JsonDeserialize(builder = ImmutableMessage.Builder.class)
@Value.Immutable
public abstract class Message {
  @JsonProperty()
  public abstract Person sender();

  @JsonProperty()
  public abstract List<Person> recipients();

  @JsonProperty()
  public abstract Text text();

  @JsonProperty()
  public abstract TimeStamp timeStamp();
}
