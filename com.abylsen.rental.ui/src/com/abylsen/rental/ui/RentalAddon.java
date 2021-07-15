 
package com.abylsen.rental.ui;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;

import com.abylsen.rental.core.RentalCoreActivator;
import com.opcoach.training.rental.RentalAgency;

public class RentalAddon {

	@PostConstruct
	public void rentalInit(IEclipseContext context) {
		context.set(RentalAgency.class, RentalCoreActivator.getAgency());
	}

}
