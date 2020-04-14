package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Products {

	private String name;
	private Double priceMin;
	private Double priceMax;
	private Double priceCur;
	private Date dateCur;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	
	public Products() {
	}
	
	public Products(String name, Double priceMin, Double priceMax, Double priceCur, Date dateCur) {
		this.name = name;
		this.priceMin = priceMin;
		this.priceMax = priceMax;
		this.priceCur = priceCur;
		this.dateCur = dateCur;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPriceMin() {
		return priceMin;
	}
	
	public void setPriceMin(Double priceMin) {
		this.priceMin = priceMin;
	}
	
	public Double getPriceMax() {
		return priceMax;
	}
	
	public void setPriceMax(Double priceMax) {
		this.priceMax = priceMax;
	}
	
	public Double getPriceCur() {
		return priceCur;
	}
	
	public void setPriceCur(Double priceCur) {
		this.priceCur = priceCur;
	}
	
	public Date getDateCur() {
		return dateCur;
	}
	
	public void setDateCur(Date dateCur) {
		this.dateCur = dateCur;
	}

	public String toString() {
		return getName() + String.format(", %.2f", priceMin) + String.format(", %.2f", priceMax) + String.format(", %.2f, ", priceCur) + sdf.format(dateCur);
	}
}