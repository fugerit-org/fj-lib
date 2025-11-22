package test.org.fugerit.java.core.db.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class TestDAORuntimeException {
	
	@Test
	void testApply() {
		Assertions.assertThrows( DAORuntimeException.class ,() -> DAORuntimeException.apply( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}

	@Test
	void testApplySilent() {
		// Create a custom appender to capture log events
		List<LogEvent> logEvents = new ArrayList<>();

		AbstractAppender testAppender = new AbstractAppender("TestAppender", null, null, true, Property.EMPTY_ARRAY) {
			@Override
			public void append(LogEvent event) {
				logEvents.add(event.toImmutable());
			}
		};
		testAppender.start();

		// Get the logger and add our appender
		Logger logger = (Logger) LogManager.getLogger(DAORuntimeException.class);
		logger.addAppender(testAppender);
		logger.setAdditive(true);

		String errorMessage = "junit test scenario apply silent";

		try {
			// Execute the method under test
			DAORuntimeException.applySilent(() -> {
				throw new SQLException(errorMessage);
			});

			// Verify log event was captured
			assertEquals(1, logEvents.size());

			LogEvent logEvent = logEvents.get(0);

			// Verify it's a WARN level
			assertEquals(org.apache.logging.log4j.Level.WARN, logEvent.getLevel());

			// Verify the message
			String message = logEvent.getMessage().getFormattedMessage();
			assertTrue(message.contains("Exception on DAORuntimeException.applySilent()"));
			assertTrue(message.contains(errorMessage));

			// Verify the throwable
			assertNotNull(logEvent.getThrown());
			assertTrue(logEvent.getThrown() instanceof SQLException);

		} finally {
			// Clean up
			logger.removeAppender(testAppender);
			testAppender.stop();
		}
	}

	@Test
	void testGet() {
		Assertions.assertThrows( DAORuntimeException.class ,() -> DAORuntimeException.get( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}
	
	@Test
	void testEx1() {
		Assertions.assertNotNull( new DAORuntimeException() );
	}
	
	@Test
	void testEx2() {
		Assertions.assertNotNull( new DAORuntimeException( "a" ) );
	}
	
	@Test
	void testEx3() {
		Assertions.assertNotNull( new DAORuntimeException( new SQLException( "b" ) ) );
	}
	
	@Test
	void testEx4() {
		Assertions.assertNotNull( new DAORuntimeException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	void testEx5() {
		Assertions.assertNotNull( DAORuntimeException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	void testEx6() {
		Assertions.assertNotNull( DAORuntimeException.convertEx( "g" , new DAORuntimeException( "g" ) ) );
	}
	
	@Test
	void testEx7() {
		Assertions.assertNotNull( DAORuntimeException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}

}
