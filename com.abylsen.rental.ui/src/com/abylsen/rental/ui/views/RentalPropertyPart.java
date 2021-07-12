 
package com.abylsen.rental.ui.views;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.abylsen.rental.core.RentalCoreActivator;
import com.opcoach.training.rental.Rental;

public class RentalPropertyPart {
	
	private Label rentedObjectLabel;
	
	@PostConstruct
	public void createContent(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setText("Informations");
		infoGroup.setLayout(new GridLayout(2, false));
		rentedObjectLabel = new Label(infoGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);
		
		setRental(RentalCoreActivator.getAgency().getRentals().get(0));
	}
	
	public void setRental(Rental rental) {
		rentedObjectLabel.setText(rental.getRentedObject().getName());
	}
	
}