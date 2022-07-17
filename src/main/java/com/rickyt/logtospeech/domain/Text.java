/* (C)2022 */
package com.rickyt.logtospeech.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonSerialize(as = ImmutableText.class)
@JsonDeserialize(builder = ImmutableText.Builder.class)
@Value.Immutable
@Wrapped
public abstract class Text extends Wrapper<String> {}
