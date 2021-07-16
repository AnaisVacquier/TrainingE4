package com.abylsen.rental.ui.providers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.abylsen.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

public class RentalProvider extends LabelProvider implements ITreeContentProvider, IColorProvider, RentalUIConstants {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List<?>) {
			return ((List<?>) inputElement).toArray();
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RentalAgency) {
			RentalAgency a = (RentalAgency) parentElement;
			return new Node[] { new Node(Node.CUSTOMERS, a), new Node(Node.LOCATIONS, a),
					new Node(Node.OBJETSALOUER, a) };
		} else if (parentElement instanceof Node) {
			return ((Node) parentElement).getChildren();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return (element instanceof RentalAgency || element instanceof Node);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof RentalAgency) {
			return ((RentalAgency) element).getName();
		} else if (element instanceof Customer) {
			return ((Customer) element).getDisplayName();
		} else if (element instanceof RentalObject) {
			return ((RentalObject) element).getName();
		}
		return super.getText(element);
	}

	@Inject
	@Named(RENTAL_UI_IMG_REGISTRY)
	private ImageRegistry imgRegistry;

	@Override
	public Image getImage(Object element) {
		if (element instanceof RentalAgency) {
			return imgRegistry.get(IMG_AGENCY);
		} else if (element instanceof Customer) {
			return imgRegistry.get(IMG_CUSTOMER);
		} else if (element instanceof RentalObject) {
			return imgRegistry.get(IMG_RENTAL_OBJECT);
		}
		return super.getImage(element);
	}

	public class Node {
		private static final String OBJETSALOUER = "Objets à louer";
		private static final String LOCATIONS = "Locations";
		private static final String CUSTOMERS = "Clients";

		private String label;
		private RentalAgency agency;

		public Node(String label, RentalAgency agency) {
			super();
			this.label = label;
			this.agency = agency;
		}

		public Object[] getChildren() {
			if (label == CUSTOMERS) {
				return agency.getCustomers().toArray();
			} else if (label == LOCATIONS) {
				return agency.getRentals().toArray();
			} else if (label == OBJETSALOUER) {
				return agency.getObjectsToRent().toArray();
			}
			return null;
		}

		@Override
		public String toString() {
			return label;
		}

	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof Customer) {
			return getPrefColor(PREF_CUSTOMER_COLOR);
		} else if (element instanceof RentalObject) {
			return getPrefColor(PREF_RENTAL_OBJECT_COLOR);
		} else if (element instanceof Rental) {
			return getPrefColor(PREF_RENTAL_COLOR);
		}
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Inject
	@Named(RENTAL_UI_PREF_STORE)
	private IPreferenceStore pref;

	/**
	 * A private methode to get a color in the preference store
	 * 
	 * @param key the preference key to get the rgb value
	 */
	private Color getPrefColor(String key) {
		ColorRegistry colorRegistry = JFaceResources.getColorRegistry();

		String rgbKey = pref.getString(key);
		Color result = colorRegistry.get(rgbKey);
		if (result == null) {
			// Get value in pref store
			colorRegistry.put(rgbKey, StringConverter.asRGB(rgbKey));
			result = colorRegistry.get(rgbKey);
		}
		return result;

	}

}
