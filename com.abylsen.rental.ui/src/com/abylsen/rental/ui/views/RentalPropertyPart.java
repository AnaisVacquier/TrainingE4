
package com.abylsen.rental.ui.views;

import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.nebula.visualization.widgets.figures.ThermometerFigure;
import org.eclipse.nebula.visualization.xygraph.util.XYGraphMediaFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;

public class RentalPropertyPart {

	private Label rentedObjectLabel;
	private Label customerLabel;
	private Label startRentalDateText;
	private Label endRentalDateText;

	private static int counter = 0;

	@PostConstruct
	public void createContent(Composite parent, RentalAgency agency, EMenuService menuService) {
		parent.setLayout(new GridLayout(2, false));

		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		infoGroup.setText("Informations");
		infoGroup.setLayout(new GridLayout(2, false));

		rentedObjectLabel = new Label(infoGroup, SWT.NONE);
		GridData gd = new GridData();

		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;

		rentedObjectLabel.setLayoutData(gd);

		Label labelLocationA = new Label(infoGroup, SWT.NONE);
		labelLocationA.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelLocationA.setText("Lou\u00E9 \u00E0 :");

		customerLabel = new Label(infoGroup, SWT.NONE);

		// ESSAI TEMPERATURE
		Group grpTemprature = new Group(parent, SWT.NONE);
		grpTemprature.setLayout(new GridLayout(1, false));
		GridData gd_grpTemprature = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2);
		gd_grpTemprature.heightHint = 190;
		grpTemprature.setLayoutData(gd_grpTemprature);
		grpTemprature.setText("Temp\u00E9rature");

		Canvas canvas = new Canvas(grpTemprature, SWT.NONE);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final LightweightSystem lws = new LightweightSystem(canvas);

		Group grpDatesDeLocation = new Group(parent, SWT.NONE);
		grpDatesDeLocation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpDatesDeLocation.setText("Dates de location");
		grpDatesDeLocation.setLayout(new GridLayout(2, false));

		Label labelDebutLocation = new Label(grpDatesDeLocation, SWT.NONE);
		labelDebutLocation.setText("du :");

		startRentalDateText = new Label(grpDatesDeLocation, SWT.NONE);

		Label labelFinLocation = new Label(grpDatesDeLocation, SWT.NONE);
		labelFinLocation.setText("au :");

		endRentalDateText = new Label(grpDatesDeLocation, SWT.NONE);

		setRental(agency.getRentals().get(0));

		// Create widget
		final ThermometerFigure thermo = new ThermometerFigure();

		// Init widget
		thermo.setBackgroundColor(XYGraphMediaFactory.getInstance().getColor(255, 255, 255));

		thermo.setBorder(new SchemeBorder(SchemeBorder.SCHEMES.ETCHED));

		thermo.setRange(-100, 100);
		thermo.setLoLevel(-50);
		thermo.setLoloLevel(-80);
		thermo.setHiLevel(60);
		thermo.setHihiLevel(80);
		thermo.setShowHi(false);
		thermo.setMajorTickMarkStepHint(50);

		lws.setContents(thermo);

		// Update the widget in another thread.
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(new Runnable() {

			public void run() {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						thermo.setValue(Math.sin(counter++ / 10.0) * 100);
					}
				});
			}
		}, 100, 100, TimeUnit.MILLISECONDS);

		future.cancel(true);
		scheduler.shutdown();
		
		menuService.registerContextMenu(infoGroup, "com.abylsen.rental.ui.popupmenu.helloinfo");
		menuService.registerContextMenu(grpDatesDeLocation, "com.abylsen.rental.ui.popupmenu.hellodate");

	}

	@Inject
	@Optional
	public void setRental(@Named(IServiceConstants.ACTIVE_SELECTION) Rental rental) {
		if (rentedObjectLabel == null) {
			return;
		}
		rentedObjectLabel.setText(rental.getRentedObject().getName());
		customerLabel.setText(rental.getCustomer().getDisplayName());

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

		startRentalDateText.setText(dateFormatter.format(rental.getStartDate()));
		endRentalDateText.setText(dateFormatter.format(rental.getEndDate()));
	}
}