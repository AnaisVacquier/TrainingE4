package com.abylsen.rental.ui;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommand;

public class AffichageCommandes {

	public AffichageCommandes() {

	}

	@Execute
	public void process(MApplication application) {
		for (MCommand c : application.getCommands()) {
			System.out.println(c.getCommandName());
		}
	}

}
