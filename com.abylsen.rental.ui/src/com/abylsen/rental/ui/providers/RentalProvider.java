package com.abylsen.rental.ui.providers;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

public class RentalProvider extends LabelProvider implements ITreeContentProvider {

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

	public class Node {
		private static final String OBJETSALOUER = "objets à louer";
		private static final String LOCATIONS = "locations";
		private static final String CUSTOMERS = "customers";

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

}
