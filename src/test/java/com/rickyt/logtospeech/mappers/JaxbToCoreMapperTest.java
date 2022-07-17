/* (C)2022 */
package com.rickyt.logtospeech.mappers;

import static org.assertj.core.api.Assertions.*;

import com.rickyt.logtospeech.domain.File;
import com.rickyt.logtospeech.domain.ImmutableFileName;
import generated.*;
import io.vavr.control.Try;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.JAXBIntrospector;
import jakarta.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class JaxbToCoreMapperTest {

  @Test
  void shouldMapJaxbToCore() throws IOException {
    JaxbToCoreMapper jaxbToCoreMapper = new JaxbToCoreMapper();

    byte[] bytes =
        Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream("charlestan883329723576.xml"))
            .readAllBytes();

    InputStream inputStream = new ByteArrayInputStream(bytes);

    LogType logType =
        Try.of(this::loadJaxbContext)
            .mapTry(JAXBContext::createUnmarshaller)
            .mapTry(unmarshaller -> mapToJaxB(inputStream, unmarshaller))
            .getOrElseThrow(() -> new IllegalStateException("Couldnt parse xml to jaxb"));

    File file = jaxbToCoreMapper.map(logType, ImmutableFileName.of("charlestan883329723576.xml"));

    assertThat(file.messages()).hasSize(8276);
  }

  private LogType mapToJaxB(InputStream inputStream, Unmarshaller unmarshaller)
      throws JAXBException {
    return (LogType) JAXBIntrospector.getValue(unmarshaller.unmarshal(inputStream));
  }

  private JAXBContext loadJaxbContext() throws JAXBException {
    return JAXBContext.newInstance(
        FromType.class,
        InvitationResponseType.class,
        InvitationType.class,
        JoinType.class,
        LeaveType.class,
        LogType.class,
        MessageType.class,
        ObjectFactory.class,
        TextType.class,
        ToType.class,
        UserType.class);
  }
}
