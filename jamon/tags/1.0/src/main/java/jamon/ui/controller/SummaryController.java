package jamon.ui.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.FileUtils;

@ManagedBean(name = "summaryCtrl")
@ViewScoped
public class SummaryController implements Serializable {

	public int getAvailableProcessors() {
		return Runtime.getRuntime().availableProcessors();
	}

	public String getFreeMemory() {
		return FileUtils.byteCountToDisplaySize(Runtime.getRuntime().freeMemory());
	}

	public String getMaxMemory() {
		return FileUtils.byteCountToDisplaySize(Runtime.getRuntime().maxMemory());
	}

	public String getTotalMemory() {
		return FileUtils.byteCountToDisplaySize(Runtime.getRuntime().totalMemory());
	}
}
