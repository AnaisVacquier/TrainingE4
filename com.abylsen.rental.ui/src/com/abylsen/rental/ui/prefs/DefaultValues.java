package com.abylsen.rental.ui.prefs;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;
import org.osgi.service.prefs.BackingStoreException;

import com.abylsen.rental.ui.RentalUIConstants;

public class DefaultValues extends AbstractPreferenceInitializer implements RentalUIConstants {

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = DefaultScope.INSTANCE.getNode(PLUGIN_ID);

		if (node != null) {
			node.put(PREF_CUSTOMER_COLOR, StringConverter.asString(new RGB(0, 0, 255)));
			node.put(PREF_RENTAL_COLOR, StringConverter.asString(new RGB(255, 0, 0)));
			node.put(PREF_RENTAL_OBJECT_COLOR, StringConverter.asString(new RGB(0, 255, 0)));
			try {
				node.flush();
			} catch (BackingStoreException e) {
			}
		}

	}

}
