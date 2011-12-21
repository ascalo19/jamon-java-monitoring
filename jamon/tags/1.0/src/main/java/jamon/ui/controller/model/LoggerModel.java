package jamon.ui.controller.model;

import jamon.ui.controller.LogLevelController;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerModel {

	private String name;
	private Level level;
	private Level effectiveLevel;
	private LogLevelController ctrl;

	public LoggerModel() {
	}

	public LoggerModel(Logger logger,LogLevelController ctrl) {
		name = logger.getName();
		level = logger.getLevel();
		effectiveLevel = logger.getLevel();
		for (Logger parent = logger; (parent = parent.getParent()) != null && effectiveLevel == null;) {
			effectiveLevel = parent.getLevel();
		}
		// Handle root logger level
		if (effectiveLevel == null && (name == null || name.isEmpty())){
			
		}
		this.ctrl = ctrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		if (level != this.level && name != null) {
			ctrl.changeLoggerLevel(name, level);
		}
		this.level = level;
	}

	public Level getEffectiveLevel() {
		return effectiveLevel;
	}

	public void setEffectiveLevel(Level effectiveLevel) {
		this.effectiveLevel = effectiveLevel;
	}
}
