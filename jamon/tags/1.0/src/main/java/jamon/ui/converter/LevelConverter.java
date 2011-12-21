package jamon.ui.converter;

import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class LevelConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		if (StringUtils.isNotBlank(string)) {
			return Level.parse(string);
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		return ObjectUtils.toString(o);
	}
}
