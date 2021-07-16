package com.abylsen.rental.ui.prefs;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;

import com.abylsen.rental.ui.RentalUIConstants;

public class RentalColorPage extends FieldEditorPreferencePage implements RentalUIConstants {

	public RentalColorPage() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		addField(new ColorFieldEditor(PREF_CUSTOMER_COLOR, "Customer :", getFieldEditorParent()));
		addField(new ColorFieldEditor(PREF_RENTAL_COLOR, "Rental :", getFieldEditorParent()));
		addField(new ColorFieldEditor(PREF_RENTAL_OBJECT_COLOR, "Rental object :", getFieldEditorParent()));
	}

}
