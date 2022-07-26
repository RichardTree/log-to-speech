/* (C)2022 */
package com.rickyt.logtospeech.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import java.util.UUID;
import org.immutables.value.Value;

@JsonSerialize(as = ImmutableFileId.class, contentUsing = UUIDSerializer.class)
@JsonDeserialize(
    builder = ImmutableFileId.Builder.class,
    contentUsing = com.fasterxml.jackson.databind.deser.std.UUIDDeserializer.class)
@Value.Immutable
@Wrapped
public abstract class FileId extends Wrapper<UUID> {

  @Override
  public String toString() {
    return this.value().toString();
  }

}
