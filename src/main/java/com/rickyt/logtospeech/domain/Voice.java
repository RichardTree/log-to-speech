/* (C)2022 */
package com.rickyt.logtospeech.domain;

public enum Voice {
  AMERICAN("en-US", "en-US-Wavenet-I"),
  AUSTRALIAN("en-AU", "en-AU-Wavenet-B"),
  BRITISH("en-GB", "en-GB-Wavenet-B");

  private final String languageCode;
  private final String voiceName;

  Voice(String languageCode, String voiceName) {
    this.languageCode = languageCode;
    this.voiceName = voiceName;
  }

  public String languageCode() {
    return languageCode;
  }

  public String voiceName() {
    return voiceName;
  }
}
