package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Products;
import services.Dados;
import services.Opcoes;

public class Program {
	
	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

		List<Products> list = new ArrayList<>();
		Products product = new Products();
		Opcoes opcoes = new Opcoes();
		Dados dados = new Dados();
		
		dados.importar(list);
				
		System.out.println(list); //APAGAR DEPOIS, SÓ P TESTE
		
		char answer = 'n';
		do {	
			System.out.println();
			System.out.println("LISTA DE COMPRAS");
			System.out.println("1 - Inserir novos produtos; ");
			System.out.println("2 - Atualizar produtos; ");
			System.out.println("3 - Mostrar lista de produtos");
			System.out.println("4 - Montar a lista de compras;");
			System.out.print("Opção: ");
			int num = sc.nextInt();
			sc.nextLine();
	
			switch(num) {
			case 1: opcoes.inserir(list); break;
			case 2: opcoes.atualizar(list); break;
			case 3: opcoes.mostrarLista(list); break;

			
			}
			System.out.println();
			System.out.print("Gostaria de continuar no programa (y/n)? ");
			answer = sc.next().charAt(0);
			sc.nextLine();

		}while (answer == 'y');	
	}
}
