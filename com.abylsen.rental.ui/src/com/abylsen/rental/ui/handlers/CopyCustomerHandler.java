
package com.abylsen.rental.ui.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import com.opcoach.training.rental.Customer;

public class CopyCustomerHandler {

	@Execute
	public void execute() {
		Clipboard clipboard = new Clipboard(Display.getCurrent());
		String textData = "Hello World";
		String rtfData = "{\\rtf1\\b\\i Hello World}";
		TextTransfer textTransfer = TextTransfer.getInstance();
		RTFTransfer rtfTransfer = RTFTransfer.getInstance();
		Transfer[] transfers = new Transfer[] { textTransfer, rtfTransfer };
		Object[] data = new Object[] { textData, rtfData };
		clipboard.setContents(data, transfers);
		clipboard.dispose();
	}

	@CanExecute
	@Evaluate
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object o) {
		return o instanceof Customer;
	}

}