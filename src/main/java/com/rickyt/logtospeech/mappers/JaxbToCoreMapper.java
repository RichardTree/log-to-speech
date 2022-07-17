/* (C)2022 */
package com.rickyt.logtospeech.mappers;

import com.rickyt.logtospeech.domain.*;
import generated.LogType;
import generated.MessageType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class JaxbToCoreMapper {

  public File map(final LogType logType, final FileName fileName) {
    return ImmutableFile.builder()
        .fileName(fileName)
        .origin(Origin.XML)
        .fileId(ImmutableFileId.of(UUID.randomUUID()))
        .addAllMessages(mapMessages(logType))
        .build();
  }

  private List<Message> mapMessages(final LogType logType) {
    return logType.getMessageOrLeaveOrJoin().stream()
        .filter(y -> y instanceof MessageType)
        .map(s -> (MessageType) s)
        .map(this::mapMessage)
        .collect(Collectors.toList());
  }

  private Message mapMessage(final MessageType messageType) {
    return ImmutableMessage.builder()
        .sender(ImmutablePerson.of(messageType.getFrom().getUser().getFriendlyName()))
        .text(ImmutableText.of(messageType.getText().getValue()))
        // TODO - Rich - Timestamp doesnt work, always defaults
        .timeStamp(ImmutableTimeStamp.of(LocalDateTime.now()))
        .addAllRecipients(mapRecipients(messageType))
        .build();
  }

  private Iterable<? extends Person> mapRecipients(MessageType messageType) {
    return messageType.getTo().getUser().stream()
        .map(t -> ImmutablePerson.of(t.getFriendlyName()))
        .collect(Collectors.toList());
  }
}
