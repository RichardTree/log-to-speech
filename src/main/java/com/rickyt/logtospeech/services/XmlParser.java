/* (C)2022 */
package com.rickyt.logtospeech.services;

import com.rickyt.logtospeech.domain.Error;
import com.rickyt.logtospeech.domain.File;
import com.rickyt.logtospeech.domain.FileName;
import com.rickyt.logtospeech.mappers.JaxbToCoreMapper;
import generated.*;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.JAXBIntrospector;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import org.springframework.stereotype.Service;

/** Uses jaxB to parse old .xml msn messenger logs to core domain {@link File} */
@Service
public class XmlParser {

  final JaxbToCoreMapper jaxbToCoreMapper;

  public XmlParser(JaxbToCoreMapper jaxbToCoreMapper) {
    this.jaxbToCoreMapper = jaxbToCoreMapper;
  }

  public Either<Error, File> parse(final InputStream inputStream, final FileName fileName) {
    return Try.of(this::loadJaxbContext)
        .mapTry(JAXBContext::createUnmarshaller)
        .mapTry(unmarshaller -> mapToJaxB(inputStream, unmarshaller))
        .map(logType -> jaxbToCoreMapper.map(logType, fileName))
        .toEither(Error.UNABLE_TO_PARSE_XML);
  }

  private LogType mapToJaxB(final InputStream inputStream, final Unmarshaller unmarshaller)
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
