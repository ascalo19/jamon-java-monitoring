package jamon.logging;

import java.net.InetAddress;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class JDK14EmailHandler extends Handler {

	private String smtpServer;
	private String emailRecipient;
	private Level level;

	public JDK14EmailHandler(String smtpServer, String emailRecipient, Level level) {
		this.smtpServer = smtpServer;
		this.emailRecipient = emailRecipient;
		this.level = level;
	}

	public static void init(String smtpServer, String emailRecipient, Level level) {
		// Make sure previous handlers are destroyed
		destroy();
		// Add new EmailHandler
		getRootLogger().addHandler(new JDK14EmailHandler(smtpServer, emailRecipient, level));
	}

	public static void destroy() {
		Logger root = getRootLogger();
		Handler[] handlers = root.getHandlers();
		// Remove previous EmailHandler
		if (handlers != null) {
			for (int i = 0; i < handlers.length; i++) {
				if (handlers[i] instanceof JDK14EmailHandler) {
					root.removeHandler(handlers[i]);
				}
			}
		}
	}

	private static Logger getRootLogger() {
		return LogManager.getLogManager().getLogger("");
	}

	@Override
	public void publish(LogRecord record) {
		try {
			if (record.getLevel().intValue() >= level.intValue()) {
				String hostName = InetAddress.getLocalHost().getCanonicalHostName();
				Email email = new SimpleEmail();
				email.setHostName(smtpServer);
				email.setFrom("jamon@" + hostName);
				email.setSubject("Java log " + record.getLevel() + " on " + hostName);
				StringBuilder msg = new StringBuilder();
				msg.append(email.getSubject());
				msg.append(" at ");
				msg.append(DateFormatUtils.format(record.getMillis(), "EEE, d MMM yyyy HH:mm:ss.S Z", Locale.ENGLISH));
				msg.append("\n\nSource: ");
				msg.append(record.getLoggerName());
				msg.append("\n\nMessage: ");
				msg.append(record.getMessage());
				msg.append("\n\n");
				try {
					// Sometimes we get a NPE
					msg.append(ExceptionUtils.getFullStackTrace(record.getThrown()));
				} catch (Exception e) {
					// Ignore
				}
				email.setMsg(msg.toString());
				email.addTo(emailRecipient);
				email.send();
			}
		} catch (Exception e) {
			// Ignore
		}
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {
	}
}
