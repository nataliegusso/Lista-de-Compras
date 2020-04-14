package services;

import java.util.Comparator;

import entities.Products;

public class MyComparator implements Comparator<Products> {

	@Override
	public int compare(Products p1, Products p2) {
		return p1.getName().toUpperCase().compareTo(p2.getName().toUpperCase());
	}

}
