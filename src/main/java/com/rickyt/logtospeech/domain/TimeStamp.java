/* (C)2022 */
package com.rickyt.logtospeech.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import org.immutables.value.Value;

@JsonSerialize(as = ImmutableTimeStamp.class, contentUsing = LocalDateTimeSerializer.class)
@JsonDeserialize(
    builder = ImmutableTimeStamp.Builder.class,
    contentUsing = LocalDateTimeDeserializer.class)
@Value.Immutable
@Wrapped
public abstract class TimeStamp extends Wrapper<LocalDateTime> {}
