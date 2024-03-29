/* (C)2022 */
package com.rickyt.logtospeech.domain;

import org.immutables.value.Value;

public abstract class Wrapper<T> {
  @Value.Parameter
  public abstract T value();

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" + value() + ")";
  }
}
