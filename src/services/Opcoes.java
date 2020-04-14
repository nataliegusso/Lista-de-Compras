package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Products;
import services.Dados;

public class Opcoes {
	Scanner sc = new Scanner(System.in);
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

	Dados dados = new Dados();
	Products product = new Products();
	List<Products>lines = new ArrayList<>();

	public void inserir(List<Products> list) throws ParseException {
		Locale.setDefault(Locale.US);

		char answer = 'n';
		do {
			System.out.println("");
			System.out.print("Produto: ");
			String nome = sc.nextLine().toLowerCase();
			//nome = nome.substring(0,1).toUpperCase() + nome.substring(1);
			
			for (Products p : list) { //Testar existência
				if (p.getName().equals(nome)) {
					System.out.println("Esse produto já foi registrado");
					return;
				}
			}
			System.out.print("Preço: ");
			double preco = sc.nextDouble();   //Corrigir erro de digitação , . (https://www.guj.com.br/t/trocar-virgula-por-ponto-em-tempo-de-execucao/235677/3)
			System.out.print("Data (dd/MM/yy): ");
			Date data = sdf.parse(sc.next()); //Proibir data passada? Ou alertar?
	
			list.add(new Products(nome, preco, preco, preco, data));
			list.sort(new MyComparator());  //ordena

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
		//nome = nome.substring(0,1).toUpperCase() + nome.substring(1);

		for (Products p : list) { //testar não existência
			if (!p.getName().equals(nome)) {
				System.out.println("Esse produto nunca foi registrado.");
				return;
			}
		}
		System.out.print("Preço: ");
		double precoUltimo = sc.nextDouble();
		System.out.print("Data (dd/MM/yy): ");
		Date data = sdf.parse(sc.next());//Proibir data passada? Ou alertar?

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
		System.out.println("");
		System.out.println(list); //SÓ P TESTAR
							
		dados.exportar(list);
	}
	
	public void mostrarLista(List<Products> list) throws ParseException {
		Locale.setDefault(Locale.US);
		System.out.println();
		System.out.println("LISTA DE PRODUTOS:");
		for (Products p : list) {
			System.out.printf(p.getName() + String.format(", R$%.2f ", p.getPriceCur()) + "(" + sdf.format(p.getDateCur()) + ")" + String.format(", menor R$%.2f", p.getPriceMin()) + String.format(", maior R$%.2f\n", p.getPriceMax()));
		}	
	}	
}
