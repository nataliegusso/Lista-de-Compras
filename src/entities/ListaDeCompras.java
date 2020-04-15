package entities;

public class ListaDeCompras implements Comparable<ListaDeCompras>{ 

	private String name;
	private Double qtde;
	private String unit;
	
	public ListaDeCompras() {
	}

	public ListaDeCompras(String name, Double qtde, String unit) {
		this.name = name;
		this.qtde = qtde;
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getQtde() {
		return qtde;
	}

	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public int compareTo(ListaDeCompras ldc) {
		return name.toLowerCase().compareTo(ldc.getName().toLowerCase());
	}
}