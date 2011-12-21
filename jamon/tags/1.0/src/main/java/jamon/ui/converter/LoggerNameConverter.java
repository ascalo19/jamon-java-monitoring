package jamon.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class LoggerNameConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		return "Root".equalsIgnoreCase(string) ? "" : string;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		return (o == null || o.toString().isEmpty()) ? "Root" : o.toString();
	}
}
