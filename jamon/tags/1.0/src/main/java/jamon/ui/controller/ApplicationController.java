package jamon.ui.controller;

import jamon.JamonService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "appCtrl", eager = true)
@ApplicationScoped
public class ApplicationController {

	Logger log = LoggerFactory.getLogger(ApplicationController.class);
	private List<SelectItem> levels = null;
	private JamonService jamon = null;

	@PostConstruct
	public void init() {
		if (jamon == null) {
			jamon = new JamonService();
		}
		jamon.init();
	}

	@PreDestroy
	public void destroy() {
		if (jamon != null) {
			jamon.destroy();
		}
		jamon = null;
	}

	public void reset() {
		if (jamon == null) {
			jamon = new JamonService();
		}
		jamon.destroy();
		jamon.init();
	}

	public void testError() {
		log.error("Test error", new Exception("Test exception"));
	}

	public void testInfo() {
		log.info("Test info");
	}

	public void testDebug() {
		log.debug("Test debug");
	}

	public List<SelectItem> getLevels() {
		levels = null;
		if (levels == null) {
			levels = new ArrayList<SelectItem>();
			levels.add(new SelectItem(null, "INHERITED"));
			levels.add(new SelectItem(Level.OFF));
			levels.add(new SelectItem(Level.SEVERE));
			levels.add(new SelectItem(Level.WARNING));
			levels.add(new SelectItem(Level.INFO));
			levels.add(new SelectItem(Level.CONFIG));
			levels.add(new SelectItem(Level.FINE));
			levels.add(new SelectItem(Level.FINER));
			levels.add(new SelectItem(Level.FINEST));
		}
		return levels;
	}

	public JamonService getJamon() {
		return jamon;
	}
}
