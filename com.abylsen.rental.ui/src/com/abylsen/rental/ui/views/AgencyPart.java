
package com.abylsen.rental.ui.views;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.abylsen.rental.ui.providers.RentalProvider;
import com.opcoach.training.rental.RentalAgency;

public class AgencyPart {

	@Inject
	public AgencyPart() {

	}

	@PostConstruct
	public void createContent(Composite parent, RentalAgency agency) {

		// treeviewer
		TreeViewer treeViewer = new TreeViewer(parent);
		RentalProvider provider = new RentalProvider();

		treeViewer.setContentProvider(provider);
		treeViewer.setLabelProvider(provider);
		treeViewer.setInput(Arrays.asList(agency));
		
		treeViewer.expandAll();

	}

}