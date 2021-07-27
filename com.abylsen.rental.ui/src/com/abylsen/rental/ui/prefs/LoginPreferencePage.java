package com.abylsen.rental.ui.prefs;

import java.io.IOException;

import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.osgi.storage.Storage.StorageException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abylsen.rental.ui.RentalUIConstants;

public class LoginPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage, RentalUIConstants {

	private static final Logger logger = LoggerFactory.getLogger(LoginPreferencePage.class);

	private StringFieldEditor usernameEditor;
	private StringFieldEditor urlEditor;
	private StringFieldEditor passwordEditor;

	public LoginPreferencePage() {
		super(GRID);
		// setPreferenceStore((Activator.getDefault()).getPreferenceStore());
		setDescription("A demonstration of a preference page implementation");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		this.urlEditor = new StringFieldEditor(P_URL, "URL:", getFieldEditorParent());
		this.usernameEditor = new StringFieldEditor(P_USERNAME, "Username:", getFieldEditorParent());
		addField(urlEditor);
		addField(usernameEditor);

		this.passwordEditor = new StringFieldEditor(P_PASSWORD, "Password:", getFieldEditorParent());
		try {
			this.passwordEditor.getTextControl(getFieldEditorParent()).setText(this.getSecureString(P_PASSWORD));
		} catch (org.eclipse.equinox.security.storage.StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.passwordEditor.getTextControl(getFieldEditorParent()).setEchoChar('*');

		Composite container = getFieldEditorParent();
		Button check = new Button(container, SWT.PUSH);
		check.setText("Check connection");
		check.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				this.widgetSelected(arg0);
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (checkConnection(urlEditor.getStringValue(), usernameEditor.getStringValue(),
						passwordEditor.getStringValue())) {
					setMessage("Successfully connected with provided information...", INFORMATION);
				} else {
					setMessage("Cannot connect with provided information. ...", ERROR);
				}
			}
		});
	}

	public String getSecureString(String name) throws org.eclipse.equinox.security.storage.StorageException {
		String value = ""; // default is empty string;
		if (name != null) {
			ISecurePreferences node = getSecurePreferenceNode();
			try {
				value = node.get(name, "");
			} catch (StorageException e) {
				logger.error(getClass().getName() + " Exception!", e);
			}
		}
		return value;
	}

	public void setSecureValue(String name, String value) throws org.eclipse.equinox.security.storage.StorageException {

		if (name != null && value != null) {
			ISecurePreferences node = getSecurePreferenceNode();
			try {
				node.put(name, value, true);
				node.flush();
			} catch (StorageException e) {
				logger.error(getClass().getName() + " StorageException!", e);
			} catch (IOException e) {
				logger.error(getClass().getName() + " IOException!", e);
			}
		}
		return;
	}

	@Override
	public boolean performOk() {
		boolean ok = super.performOk();
		try {
			this.setSecureValue(P_PASSWORD, passwordEditor.getStringValue());
		} catch (org.eclipse.equinox.security.storage.StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}

	private ISecurePreferences getSecurePreferenceNode() {
		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		return preferences.node(FrameworkUtil.getBundle(getClass()).getSymbolicName());
	}

	public void init(IWorkbench workbench) {
	}

	private boolean checkConnection(String url, String user, String pass) {
		return true;
	}

	@Override
	public void init(org.eclipse.ui.IWorkbench workbench) {
		// TODO Auto-generated method stub
	}

}