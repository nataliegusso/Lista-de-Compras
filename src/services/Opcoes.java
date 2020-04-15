package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.ListaDeCompras;
import entities.Products;

public class Opcoes {
	Scanner sc = new Scanner(System.in);
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

	Dados dados = new Dados();
	Products product = new Products();

	double soma = 0;
	
	public void inserir(List<Products> list) throws ParseException {
		Locale.setDefault(Locale.US);
		char answer = 'n';
		do {
			System.out.println("");
			System.out.print("Produto: ");
			String nome = sc.nextLine().toLowerCase();
			
			for (Products p : list) { //Testar existência
				if (p.getName().equals(nome)) {
					System.out.println("Esse produto já foi registrado");
					return;
				}
			}
			
			System.out.print("Unidade de medida (unid, kg): ");
			String unid = sc.nextLine().toLowerCase();
			System.out.print("Preço: ");
			double preco = sc.nextDouble();
			System.out.print("Data (dd/MM/yy): ");
			Date data = sdf.parse(sc.next()); 
	
			list.add(new Products(nome, unid, preco, preco, preco, data));
			list.sort(new MyComparator());  // Ordena na classe MyComparator

			dados.exportar(list);

			System.out.print("Gostaria de inserir mais algum produto (y/n)? ");
			answer = sc.next().charAt(0);
			sc.nextLine();
		}while (answer == 'y');
	}

	
	public void atualizar(List<Products> list) throws ParseException {
		Locale.setDefault(Locale.US);

		System.out.println("");
		System.out.print("Produto: ");
		String nome = sc.nextLine().toLowerCase();

		int cont = 0;
		for (Products p : list) {		//testar não existência
			if (!p.getName().equals(nome)) {
				cont++;
				if (cont == list.size()) {
					System.out.println("Esse produto nunca foi registrado.");
				return;
				}
			}
		}
		System.out.print("Preço: ");
		double precoUltimo = sc.nextDouble();
		System.out.print("Data (dd/MM/yy): ");
		Date data = sdf.parse(sc.next());//Proibir data passada? Ou alertar?
		sc.nextLine();
		
		for (Products prod : list) {
			if (prod.getName().equals(nome)){
				prod.setName(nome);
				prod.setDateCur(data);
				prod.setPriceCur(precoUltimo);
				
				if (precoUltimo > prod.getPriceMax()){
					prod.setPriceMax(precoUltimo);
				}else if (precoUltimo < prod.getPriceMin()){
					prod.setPriceMin(precoUltimo);
				}
			} 
		}
		dados.exportar(list);
	}

	
	public void mostrarLista(List<Products> list) throws ParseException {
		Locale.setDefault(Locale.US);
		System.out.println();
		System.out.println("LISTA DE PRODUTOS:");
		for (Products p : list) {
			System.out.print(p.getName().substring(0,1).toUpperCase() + p.getName().substring(1));		
			System.out.printf(String.format(", R$%.2f", p.getPriceCur()) +"/"+ p.getUnit() + " (" + sdf.format(p.getDateCur()) + ")" + String.format(",   menor R$%.2f", p.getPriceMin()) + String.format(", maior R$%.2f\n", p.getPriceMax()));
		}	
	}	
	
	
	public void montarLista(List<ListaDeCompras> lista, List<Products> list) throws ParseException {
		char answer = 'n';
		do {
			System.out.println("");
			System.out.print("Produto: ");
			String nome = sc.nextLine();
			nome = nome.toLowerCase();

			int cont = 0;
			for (Products p : list) {		//testar não existência
				if (!p.getName().equals(nome)) {
					cont++;
					if (cont == list.size()) {
						System.out.println("Esse produto nunca foi registrado.");
					return;
					}
				}
			}
			
			System.out.print("Quantidade: ");
			Double qtde = sc.nextDouble();
			String unid = null;
						
			for (Products prod : list) {
				if (prod.getName().equals(nome)){
					soma += prod.getPriceCur() * qtde;
					unid = prod.getUnit();
				} 
			}
			
			lista.add(new ListaDeCompras(nome, qtde, unid));
			
			System.out.print("Gostaria de inserir mais algum produto (y/n)? ");
			answer = sc.next().charAt(0);
			sc.nextLine();
		}while (answer == 'y');
		
		Collections.sort(lista);  //Ordena através de uma implementação de comparable na classe ListaDeCompra
		
		System.out.println();
		System.out.println("LISTA DE COMPRAS:");
		for (ListaDeCompras ldc : lista) {
			System.out.printf(ldc.getQtde() + " " + ldc.getUnit() + " ");
			System.out.printf(ldc.getName().substring(0,1).toUpperCase() + ldc.getName().substring(1) + String.format("\n"));
		}
		System.out.println("Preço total estimado da compra: R$" + String.format("%.2f", soma) + ".");
	}
	
	
	public void zerarLista(List<ListaDeCompras> lista) throws ParseException {
		lista.clear();
		soma = 0.00;
	}
}