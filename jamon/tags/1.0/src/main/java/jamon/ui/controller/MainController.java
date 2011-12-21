package jamon.ui.controller;

import java.net.InetAddress;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "mainCtrl")
@ViewScoped
public class MainController {

	Logger log = LoggerFactory.getLogger(MainController.class);

	public String getDebug() throws Exception {
		return InetAddress.getLocalHost().getCanonicalHostName();
	}
}
