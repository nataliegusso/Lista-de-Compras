package application;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import controler.ExpImpData;
import controler.Operations;
import entities.ListaDeCompras;
import entities.Products;

public class Program {
	
	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Products> list = new ArrayList<>();
		List<ListaDeCompras> lista = new ArrayList<>();

		Operations opcoes = new Operations();
		ExpImpData dados = new ExpImpData();
		
		dados.importar(list);
				
		char answer = 'n';
		do {	
			System.out.println();
			System.out.println("OPÇÕES:");
			System.out.println("1 - Inserir novos produtos; ");
			System.out.println("2 - Atualizar produtos; ");
			System.out.println("3 - Mostrar estoque de produtos;");
			System.out.println("4 - Lista de compras / pedido do cliente;");
			System.out.print("Opção: ");
			int num = sc.nextInt();
			sc.nextLine();
			
			switch(num) {
				case 1: opcoes.inserir(list); break;
				case 2: opcoes.atualizar(list); break;
				case 3: opcoes.mostrarLista(list); break;
				case 4: opcoes.pedidoCliente(lista, list); break;
			}

			System.out.println();
			System.out.print("Gostaria de continuar no programa (y/n)? ");
			answer = sc.next().charAt(0);
			sc.nextLine();

		}while (answer == 'y');	
		sc.close();
	}
}