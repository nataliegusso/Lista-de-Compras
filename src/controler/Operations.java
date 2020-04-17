package controler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.ListaDeCompras;
import entities.Products;
import utilities.MyComparator;

public class Operations {
	Scanner sc = new Scanner(System.in);
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

	ExpImpData dados = new ExpImpData();
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
			System.out.print("Quantidade: ");
			double qtde = sc.nextDouble();
			System.out.print("Preço: ");
			double preco = sc.nextDouble();
			System.out.print("Data (dd/MM/yy): ");
			Date data = sdf.parse(sc.next()); 
			
			Date dataHoje = new Date(System.currentTimeMillis()); 
			SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yy"); 
			if (data.after(dataHoje)) {
				System.out.println("Erro: data futura.");
				return;
			}
			
			list.add(new Products(nome, qtde, unid, preco, preco, preco, data));
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
		System.out.print("Quantidade total: ");
		double qtde = sc.nextDouble();
		System.out.print("Preço: ");
		double precoUltimo = sc.nextDouble();
		System.out.print("Data (dd/MM/yy): ");
		Date data = sdf.parse(sc.next());//Proibir data passada? Ou alertar?
		sc.nextLine();
		
		Date dataHoje = new Date(System.currentTimeMillis()); 
		SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yy"); 
		if (data.after(dataHoje)) {
			System.out.println("Erro: data futura.");
			return;
		}
		
		for (Products prod : list) {
			if (prod.getName().equals(nome)){
				prod.setName(nome);
				prod.setQty(qtde);
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
		System.out.println("ESTOQUE DE PRODUTOS:");
		for (Products p : list) {
			System.out.print(p.getName().substring(0,1).toUpperCase() + p.getName().substring(1));		
			System.out.printf(", " + String.format(p.getQty() + " " + p.getUnit() + ", R$%.2f", p.getPriceCur()) +"/"+ p.getUnit() + " (" + sdf.format(p.getDateCur()) + ")" + String.format(",   menor R$%.2f", p.getPriceMin()) + String.format(", maior R$%.2f\n", p.getPriceMax()));
		}	
	}	
	
	
	public void pedidoCliente(List<ListaDeCompras> lista, List<Products> list) throws ParseException {
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
						System.out.println("Não existe esse produto.");
					return;
					}
				}
			}
			System.out.print("Quantidade: ");
			Double qtdePedido = sc.nextDouble();
			String unid = null;
			
			for (Products prod : list) {
				if (prod.getName().equals(nome)){
					if (qtdePedido <= prod.getQty()) {
						soma += prod.getPriceCur() * qtdePedido;
						unid = prod.getUnit();
						prod.setQty(prod.getQty() - qtdePedido);
					}else if (qtdePedido > prod.getQty()) {
						System.out.println("Quantidade máxima: " + prod.getQty() + " " + prod.getUnit() + ".");
						System.out.print("Quantidade: ");
						qtdePedido = sc.nextDouble();
						sc.nextLine();
						if (qtdePedido <= prod.getQty()) {
							soma += prod.getPriceCur() * qtdePedido;
							unid = prod.getUnit();
							prod.setQty(prod.getQty() - qtdePedido);
						}else if(qtdePedido > prod.getQty()) {
							System.out.println("Valor inválido.");
							return;
						}
					}
				} 
			}
			lista.add(new ListaDeCompras(nome, qtdePedido, unid));
			
			System.out.print("Gostaria de inserir mais algum produto (y/n)? ");
			answer = sc.next().charAt(0);
			sc.nextLine();
		}while (answer == 'y');
		
		lista.sort((p1, p2) -> p1.getName().toLowerCase().compareTo(p2.getName().toLowerCase())); //Ordena com lambda
				
		dados.exportar(list);
		
		System.out.println();
		System.out.println("PEDIDO DO CLIENTE:");
		for (ListaDeCompras ldc : lista) {
			System.out.printf(ldc.getQty() + " " + ldc.getUnit() + " ");
			System.out.printf(ldc.getName().substring(0,1).toUpperCase() + ldc.getName().substring(1) + String.format("\n"));
		}
		System.out.println("Preço total estimado da compra: R$" + String.format("%.2f", soma) + ".");
		lista.clear();
		soma = 0.00;
	}
}