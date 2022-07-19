/* (C)2022 */
package com.rickyt.logtospeech.services;

import static org.assertj.core.api.Assertions.*;

import com.rickyt.logtospeech.domain.Error;
import com.rickyt.logtospeech.domain.File;
import com.rickyt.logtospeech.domain.ImmutableFileName;
import com.rickyt.logtospeech.mappers.JaxbToCoreMapper;
import io.vavr.control.Either;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class XmlParserTest {

  public static final String FILE_NAME = "test-msn-log.xml";

  @Test
  public void shouldParseXmlToFile() throws IOException {
    final var xmlParser = new XmlParser(new JaxbToCoreMapper());

    byte[] bytes = getClass().getClassLoader().getResourceAsStream(FILE_NAME).readAllBytes();

    InputStream myInputStream = new ByteArrayInputStream(bytes);

    Either<Error, File> file = xmlParser.parse(myInputStream, ImmutableFileName.of(FILE_NAME));

    assertThat(file.isRight()).isTrue();
  }
}
