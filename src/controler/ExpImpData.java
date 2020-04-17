package controler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import entities.Products;
import utilities.MyComparator;

public class ExpImpData {
	Scanner sc = new Scanner(System.in);
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

	Products product = new Products();
	List<Products>lines = new ArrayList<>();

	
	public void exportar(List<Products> list) {
		try (PrintWriter bw = new PrintWriter(new FileWriter("c:\\temp\\lista.txt"))) { 
			list.sort(new MyComparator()); 
			
			for (int i=0; i<list.size() ; i++) {
				bw.printf("%s%n", list.get(i));
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}
	}
	
	
	public void importar(List<Products> list) throws ParseException {
		list.sort(new MyComparator());
		
		try (BufferedReader br = new BufferedReader(new FileReader("c:\\temp\\lista.txt"))) { 
			String itens = br.readLine(); 
			while (itens != null) {  
				String[] fields = itens.split(",");
				String nome = fields[0];
				double qty = Double.parseDouble(fields[1]);
		       	String unit = fields[2];
		       	double precoMin = Double.parseDouble(fields[3]);
		       	double precoMax = Double.parseDouble(fields[4]);
		       	double preco = Double.parseDouble(fields[5]);
		       	Date data = sdf.parse(fields[6]);
		       	list.add(new Products(nome, qty, unit, precoMin, precoMax, preco, data));
		       	itens = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
}
