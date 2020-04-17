package entities;

public class ListaDeCompras{ 

	private String name;
	private Double qty;
	private String unit;
	
	public ListaDeCompras() {
	}

	public ListaDeCompras(String name, Double qty, String unit) {
		this.name = name;
		this.qty = qty;
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}