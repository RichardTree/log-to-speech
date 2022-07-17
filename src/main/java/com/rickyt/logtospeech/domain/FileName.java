/* (C)2022 */
package com.rickyt.logtospeech.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonSerialize(as = ImmutableFileName.class)
@JsonDeserialize(builder = ImmutableFileName.Builder.class)
@Value.Immutable
@Wrapped
public abstract class FileName extends Wrapper<String> {}
