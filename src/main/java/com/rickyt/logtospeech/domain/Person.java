/* (C)2022 */
package com.rickyt.logtospeech.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonSerialize(as = ImmutablePerson.class)
@JsonDeserialize(builder = ImmutablePerson.Builder.class)
@Value.Immutable
public abstract class Person extends Wrapper<String> {}
