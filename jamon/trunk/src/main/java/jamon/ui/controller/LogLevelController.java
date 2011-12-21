package jamon.ui.controller;

import jamon.ui.controller.model.LoggerModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanComparator;

@ManagedBean(name = "logLevelCtrl")
@ViewScoped
public class LogLevelController implements Serializable {

	private transient List<LoggerModel> loggers;

	public List<LoggerModel> getLoggers() {
		if (loggers == null) {
			loggers = new ArrayList<LoggerModel>();
			LogManager logManager = LogManager.getLogManager();
			Enumeration<String> loggerNames = logManager.getLoggerNames();
			while (loggerNames.hasMoreElements()) {
				Logger logger = logManager.getLogger(loggerNames.nextElement());
				if (logger != null) {
					loggers.add(new LoggerModel(logger, this));
				}
			}
			Collections.sort(loggers, new BeanComparator("name"));
		}
		return loggers;
	}

	public void changeLoggerLevel(String name, Level level) {
		Logger logger = LogManager.getLogManager().getLogger(name);
		logger.setLevel(level);
		loggers = null;
	}
}
