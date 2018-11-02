package pmf.phy.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDemo {

	public static void main(String[] args) {
		
		/*
		 * zaboravio sam ovo staviti u metode, no to je piece of cake, stavit cu zadnju cisto da se vidi
		 */
		
		Path path = Paths.get("C:\\Users\\barto\\Desktop\\studenti.txt");
		try{
			List<String> lines = Files.readAllLines(path);
			List<StudentRecord> records = new ArrayList<StudentRecord>();
			for(int i = 0; i < lines.size() ; i++) {
				String[] str = lines.get(i).split("\t");
				StudentRecord sr = new StudentRecord(str[0], str[1], str[2],  Double.parseDouble(str[3]), Double.parseDouble(str[4]),Double.parseDouble(str[5]), Integer.parseInt(str[6]));
				records.add(sr);
			}
			
			for(StudentRecord sr : records) {
				System.out.println(sr.toString());
			}
			
			long broj = records.stream().filter(p -> (p.getBodoviLA()+ p.getBodoviMI() + p.getBodoviZI()) > 25).count();
			System.out.println("Broj studenata koji imaju vise od 25 bodova ;" + broj );
			
			long broj5 = records.stream().filter(p -> p.getOcjena() == 5).count();
			System.out.println("Broj studenata koji su dobili 5 : " +broj5);
			
			List<StudentRecord> odlikasi = records.stream().filter(p -> p.getOcjena() == 5).collect(Collectors.toList());
			for(StudentRecord sr : odlikasi) {
				System.out.println(sr);
			}
			System.out.println();
			
			List<StudentRecord> odlikasiSortirano = records.stream().filter(p -> p.getOcjena() == 5)
					.sorted((p1,p2) -> { 
						if((p1.getBodoviLA() + p1.getBodoviMI() + p1.getBodoviZI()) > (p2.getBodoviLA() + p2.getBodoviMI() + p2.getBodoviZI()) ) {
							return -1;
						}
						else 
							return 1;
							})
					.collect(Collectors.toList());
			for(StudentRecord sr : odlikasiSortirano) {
				System.out.println(sr);	
			}
			
			
			
			List<String> nepolozeniJMBAGovi = records.stream().filter(p -> p.getOcjena() == 1).map(p -> String.valueOf(p.getJmbag())).collect(Collectors.toList());
			
			for(String s : nepolozeniJMBAGovi) {
				System.out.println(s);
			}
			
		
			//group.by automatski vraca hashmapu,zasto lista, zato sto svaki ocjenu moze imati vise studenata
			Map<Integer,List<StudentRecord>> mapaPoOcjenama = records.stream().collect(Collectors.groupingBy(p -> p.getOcjena()));
			System.out.println();																								//kljuc -> rezultat ove gore operacije
																											//value -> lista studenata sa istom ocjenom
		
			
			Map<Integer,Integer> mapaPoOcjenama2 = records.stream().collect(Collectors.toMap(p -> Integer.valueOf(p.getOcjena()), p-> 1, (o1,o2) -> o1+o2));
			
			Map<Boolean, List<StudentRecord>> map = razvrstajProlazPad(records);
			System.out.println(map.get(true)); //koji su prosli
			System.out.println(map.get(false));//koji su pali
			
		}catch(IOException e) {
			System.out.println(e);
		}
		
		
	}
	
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> list) {
		
		return list.stream().collect(Collectors.partitioningBy(student -> student.getOcjena() > 1));
	}
}
