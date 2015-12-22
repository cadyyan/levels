package com.cadyyan.levels.utils;

import com.cadyyan.levels.Levels;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.Message;

// Inspired by Pahimar's LogHelper class
// https://github.com/pahimar/Equivalent-Exchange-3/blob/master/src/main/java/com/pahimar/ee3/util/LogHelper.java

public class LogUtility
{
	public static final Marker MOD_MARKER = MarkerManager.getMarker(Levels.MOD_ID);
	public static final Logger LOGGER = LogManager.getLogger(Levels.MOD_ID);

	// Generic logging

	public static void log(Level level, Marker marker, Message message)
	{
		LOGGER.log(level, marker, message);
	}

	public static void log(Level level, Marker marker, Object object)
	{
		LOGGER.log(level, marker, object);
	}

	public static void log(Level level, Marker marker, String message)
	{
		LOGGER.log(level, marker, message);
	}

	public static void log(Level level, Marker marker, String format, Object... params)
	{
		LOGGER.log(level, marker, format, params);
	}

	// Mod specific logging

	public static void log(Level level, Message message)
	{
		log(level, MOD_MARKER, message);
	}

	public static void log(Level level, Object object)
	{
		log(level, MOD_MARKER, object);
	}

	public static void log(Level level, String message)
	{
		log(level, MOD_MARKER, message);
	}

	public static void log(Level level, String format, Object... params)
	{
		log(level, MOD_MARKER, format, params);
	}

	// All level logging

	public static void all(Marker marker, Message message)
	{
		log(Level.ALL, marker, message);
	}

	public static void all(Marker marker, Object object)
	{
		log(Level.ALL, marker, object);
	}

	public static void all(Marker marker, String message)
	{
		log(Level.ALL, marker, message);
	}

	public static void all(Marker marker, String format, Object... params)
	{
		log(Level.ALL, marker, format, params);
	}

	public static void all(Message message)
	{
		log(Level.ALL, message);
	}

	public static void all(Object object)
	{
		log(Level.ALL, object);
	}

	public static void all(String message)
	{
		log(Level.ALL, message);
	}

	public static void all(String format, Object... params)
	{
		log(Level.ALL, format, params);
	}

	// Debug level logging

	public static void debug(Marker marker, Message message)
	{
		log(Level.DEBUG, marker, message);
	}

	public static void debug(Marker marker, Object object)
	{
		log(Level.DEBUG, marker, object);
	}

	public static void debug(Marker marker, String message)
	{
		log(Level.DEBUG, marker, message);
	}

	public static void debug(Marker marker, String format, Object... params)
	{
		log(Level.DEBUG, marker, format, params);
	}

	public static void debug(Message message)
	{
		log(Level.DEBUG, message);
	}

	public static void debug(Object object)
	{
		log(Level.DEBUG, object);
	}

	public static void debug(String message)
	{
		log(Level.DEBUG, message);
	}

	public static void debug(String format, Object... params)
	{
		log(Level.DEBUG, format, params);
	}

	// Error level logging

	public static void error(Marker marker, Message message)
	{
		log(Level.ERROR, marker, message);
	}

	public static void error(Marker marker, Object object)
	{
		log(Level.ERROR, marker, object);
	}

	public static void error(Marker marker, String message)
	{
		log(Level.ERROR, marker, message);
	}

	public static void error(Marker marker, String format, Object... params)
	{
		log(Level.ERROR, marker, format, params);
	}

	public static void error(Message message)
	{
		log(Level.ERROR, message);
	}

	public static void error(Object object)
	{
		log(Level.ERROR, object);
	}

	public static void error(String message)
	{
		log(Level.ERROR, message);
	}

	public static void error(String format, Object... params)
	{
		log(Level.ERROR, format, params);
	}

	// Fatal level logging

	public static void fatal(Marker marker, Message message)
	{
		log(Level.FATAL, marker, message);
	}

	public static void fatal(Marker marker, Object object)
	{
		log(Level.FATAL, marker, object);
	}

	public static void fatal(Marker marker, String message)
	{
		log(Level.FATAL, marker, message);
	}

	public static void fatal(Marker marker, String format, Object... params)
	{
		log(Level.FATAL, marker, format, params);
	}

	public static void fatal(Message message)
	{
		log(Level.FATAL, message);
	}

	public static void fatal(Object object)
	{
		log(Level.FATAL, object);
	}

	public static void fatal(String message)
	{
		log(Level.FATAL, message);
	}

	public static void fatal(String format, Object... params)
	{
		log(Level.FATAL, format, params);
	}

	// Info level logging

	public static void info(Marker marker, Message message)
	{
		log(Level.INFO, marker, message);
	}

	public static void info(Marker marker, Object object)
	{
		log(Level.INFO, marker, object);
	}

	public static void info(Marker marker, String message)
	{
		log(Level.INFO, marker, message);
	}

	public static void info(Marker marker, String format, Object... params)
	{
		log(Level.INFO, marker, format, params);
	}

	public static void info(Message message)
	{
		log(Level.INFO, message);
	}

	public static void info(Object object)
	{
		log(Level.INFO, object);
	}

	public static void info(String message)
	{
		log(Level.INFO, message);
	}

	public static void info(String format, Object... params)
	{
		log(Level.INFO, format, params);
	}

	// Off level logging

	public static void off(Marker marker, Message message)
	{
		log(Level.OFF, marker, message);
	}

	public static void off(Marker marker, Object object)
	{
		log(Level.OFF, marker, object);
	}

	public static void off(Marker marker, String message)
	{
		log(Level.OFF, marker, message);
	}

	public static void off(Marker marker, String format, Object... params)
	{
		log(Level.OFF, marker, format, params);
	}

	public static void off(Message message)
	{
		log(Level.OFF, message);
	}

	public static void off(Object object)
	{
		log(Level.OFF, object);
	}

	public static void off(String message)
	{
		log(Level.OFF, message);
	}

	public static void off(String format, Object... params)
	{
		log(Level.OFF, format, params);
	}

	// Trace level logging

	public static void trace(Marker marker, Message message)
	{
		log(Level.TRACE, marker, message);
	}

	public static void trace(Marker marker, Object object)
	{
		log(Level.TRACE, marker, object);
	}

	public static void trace(Marker marker, String message)
	{
		log(Level.TRACE, marker, message);
	}

	public static void trace(Marker marker, String format, Object... params)
	{
		log(Level.TRACE, marker, format, params);
	}

	public static void trace(Message message)
	{
		log(Level.TRACE, message);
	}

	public static void trace(Object object)
	{
		log(Level.TRACE, object);
	}

	public static void trace(String message)
	{
		log(Level.TRACE, message);
	}

	public static void trace(String format, Object... params)
	{
		log(Level.TRACE, format, params);
	}

	// Warn level logging

	public static void warn(Marker marker, Message message)
	{
		log(Level.TRACE, marker, message);
	}

	public static void warn(Marker marker, Object object)
	{
		log(Level.TRACE, marker, object);
	}

	public static void warn(Marker marker, String message)
	{
		log(Level.TRACE, marker, message);
	}

	public static void warn(Marker marker, String format, Object... params)
	{
		log(Level.TRACE, marker, format, params);
	}

	public static void warn(Message message)
	{
		log(Level.TRACE, message);
	}

	public static void warn(Object object)
	{
		log(Level.TRACE, object);
	}

	public static void warn(String message)
	{
		log(Level.TRACE, message);
	}

	public static void warn(String format, Object... params)
	{
		log(Level.TRACE, format, params);
	}

	private LogUtility()
	{
	}
}
