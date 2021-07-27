
package com.abylsen.rental.ui.views;

import java.util.Arrays;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.abylsen.rental.ui.RentalUIConstants;
import com.abylsen.rental.ui.providers.RentalProvider;
import com.opcoach.training.rental.RentalAgency;

public class AgencyPart implements RentalUIConstants {

	private TreeViewer treeViewer;

	@Inject
	public AgencyPart(Composite parent, RentalAgency agency, IEclipseContext contexte,
			ESelectionService selectionService, EMenuService menuService) {

		treeViewer = new TreeViewer(parent);

		RentalProvider provider = ContextInjectionFactory.make(RentalProvider.class, contexte);

		treeViewer.setContentProvider(provider);
		treeViewer.setLabelProvider(provider);
		treeViewer.setInput(Arrays.asList(agency));

		treeViewer.expandAll();

		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				selectionService
						.setSelection(selection.size() == 1 ? selection.getFirstElement() : selection.toArray());
			}
		});
		
		menuService.registerContextMenu(treeViewer.getControl(), "com.abylsen.rental.ui.popupmenu.helloworld");

	}

	@Inject
	@Optional
	public void changeColorTree(@Preference(value = PREF_CUSTOMER_COLOR) String customerColorChanged,
			@Preference(value = PREF_RENTAL_COLOR) String rentalColorChanged,
			@Preference(value = PREF_RENTAL_OBJECT_COLOR) String rentalObjectColorChanged) {
		treeViewer.refresh();
	}

	@Inject
	@Optional
	public void setLoginPage(@Preference(value = P_URL) String url, 
			@Preference(value = P_USERNAME) String username,
			@Preference(value = P_PASSWORD) String password) {
		treeViewer.refresh();
	}

}