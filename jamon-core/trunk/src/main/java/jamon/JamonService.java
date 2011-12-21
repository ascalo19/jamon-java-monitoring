package jamon;

import jamon.logging.JDK14EmailHandler;
import java.net.InetAddress;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JamonService {

	public static final String CUSTOM_CONFIG_FILE = "jamon-custom.properties";
	private static final Logger log = LoggerFactory.getLogger(JamonService.class);
	private String smtpServer = null;
	private String emailRecipient = null;
	private Level level = null;

	public void init() {
		log.info("Initializing Jamon ...");
		try {

			InetAddress localhost = InetAddress.getLocalHost();
			String canonicalHostName = localhost.getCanonicalHostName();
			String dnsDomain = StringUtils.substringAfter(canonicalHostName, ".");
			String mailDomain = dnsDomain;
			if (StringUtils.endsWith(mailDomain, ".local")) {
				mailDomain = StringUtils.replace(mailDomain, ".local", ".ch");
			}

			try {
				Properties config = new Properties();
				config.load(getClass().getResourceAsStream("/" + CUSTOM_CONFIG_FILE));
				smtpServer = config.getProperty("smtpServer");
				emailRecipient = config.getProperty("emailRecipient");
				if (config.getProperty("level") != null) {
					level = Level.parse(config.getProperty("level"));
				}
				log.info("Jamon config loaded from file " + CUSTOM_CONFIG_FILE);
			} catch (Exception e) {
				// Ignore
			}

			if (StringUtils.isBlank(smtpServer)) {
				smtpServer = "smtp";
			}
			if (StringUtils.isBlank(emailRecipient)) {
				emailRecipient = "logmaster@" + mailDomain;
			}
			if (level == null) {
				level = Level.SEVERE;
			}

			JDK14EmailHandler.init(smtpServer, emailRecipient, level);

		} catch (Exception e) {
			log.error("Unexpected exception during Jamon init", e);
		}
	}

	public void destroy() {
		log.info("Destroying Jamon ...");
		try {
			JDK14EmailHandler.destroy();
		} catch (Exception e) {
			log.error("Unexpected exception during Jamon destroy", e);
		}
	}

	public String getEmailRecipient() {
		return emailRecipient;
	}

	public void setEmailRecipient(String emailRecipient) {
		this.emailRecipient = emailRecipient;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}
}
