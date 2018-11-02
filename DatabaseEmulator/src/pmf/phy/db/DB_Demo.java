package pmf.phy.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import pmf.phy.db.parser.*;

public class DB_Demo {
	/*
	 * U system.in se upisuje upit, dobiva se povratna informacija , UPIT ZAVRSAVA SA ';' ZNAKOM!!!!!!!!!!!!;
	 */
	public static void main(String[] args) throws FileNotFoundException,
			DatabaseException {

		ArrayList<String> list = new ArrayList<String>();
		Scanner sc = new Scanner(new File(
				"C:\\Users\\barto\\Desktop\\database.txt"));
		Scanner query = new Scanner(System.in);

		while (sc.hasNextLine()) {
			list.add(sc.nextLine());
		}
		StudentDatabase db = new StudentDatabase(list);

		while (true) {

			System.out.print("query> ");
			Parser parsaner = null;
			long count = 0;
			StringBuilder sb_query = new StringBuilder();

			sb_query.append("query ");
			sb_query.append(query.nextLine());
			count = sb_query.toString().chars().filter(ch -> ch == ';').count();

			
			if (sb_query.toString().contains(";") && count == 1) { // valjani
					if(sb_query.toString().contains("break")) {
						System.out.println("GOTOVO");
						System.exit(-1);;
					}
				int i = sb_query.toString().indexOf(';');
				parsaner = new Parser(sb_query.toString().substring(0, i)); 
				executionQuery(parsaner, db);
				continue; // prosao upit, idemo opet, vracam se na prvi while

			} else {
				
				System.out.println("Nije dobar upit pokusaj ponovno");
				continue; // vracam se na drugi while
			}
		}

	}

	public static void executionQuery(Parser parser, StudentDatabase db) {
		if (parser.isDirectQuery()) {
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			// za jedan jmbag mogu dobiti ime, prezime i ocjenu
			String[] direct = new String[4];
			StringBuilder sb = new StringBuilder();
			direct[0] = r.getJmbag();
			direct[1] = r.getLastName();
			direct[2] = r.getFirstName();
			sb.append(r.getFinalGrade());
			direct[3] = sb.toString();

			System.out.println(Arrays.toString(direct));

		} else {
			ArrayList<StudentRecord> sr = new ArrayList<>();
			short recordsSelected = 0;
			for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) { 
				//System.out.println(db.finalList.get(recordsSelected).toString());
				System.out.println(r);
				recordsSelected++;
			}
			System.out.println("Records selected : " + recordsSelected);

		}
	}

}
