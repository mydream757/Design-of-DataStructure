import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class JavaApp{
	
	public static ArrayList<Person> pList = new ArrayList<Person>();
	
	public static void main(String[] args) {
		System.out.println("The Social Graph of Actors and Actress\n");
		Scanner scanner = new Scanner(System.in);
		FileHandler fHandler = new FileHandler();
		NodeHandler nHandler = new NodeHandler();
		OpenApi web = new OpenApi();
		
		System.out.println("Instruction: 1-Get data from OpenAPI 2-Read from text file :");
		int k = Integer.parseInt(scanner.next());
		String str;
		//get data from web
		String[] splt;
		String opstart;
		String opend;
		
		int s;
		int e;
		String tyear;
		switch(k) {
			
			case 1:
				
				System.out.print("Insert the key : ");
				String line = scanner.next();
				web.key = line;
				scanner.nextLine();
				System.out.print("Open year(start and end) : ");
				line = scanner.nextLine();
				splt = line.split(" ");
				opstart = splt[0];
				opend = splt[1];
				s = Integer.parseInt(opstart);
				e = Integer.parseInt(opend);
				
				
				
				for(int years=0; years<=e-s; years++) {
					s = s+years;
					tyear = Integer.toString(s);
					
					pList = new ArrayList<Person>();
					OpenApi.mList = new ArrayList<Movie>();
					
					web.getMovieList(tyear, tyear);
					
					Collections.sort(OpenApi.mList,new Comparator<Movie>(){
						@Override
						public int compare(Movie m1, Movie m2) {
							// TODO Auto-generated method stub
							if (Integer.parseInt((m1.openDt)) < Integer.parseInt(m2.openDt)) {
					            return -1;
					        } else if (Integer.parseInt(m1.openDt) > Integer.parseInt(m2.openDt)) {
					            return 1;
					        }
					        return 0;
							
						}
					});
					str = tyear+"-"+tyear+"_movie.txt";
					fHandler.writeMovie(str);
					
					for(int i=0; i<OpenApi.mList.size(); i++) {
						web.UpdateActors(OpenApi.mList.get(i));
						nHandler.insertEdge(OpenApi.mList.get(i));
					}
					//AdjSort dscending = new AdjSort();
					Collections.sort(pList);
					
					
					str = tyear+"-"+tyear+"_movie.txt";
					fHandler.writeMovie(str);
					str = tyear+"-"+tyear+"_relation.txt";
					fHandler.writeRelation(str);
					str = tyear+"-"+tyear+"_filmos.txt";
					fHandler.writeFilmo(str);
					
				}
				System.out.println("총 배우: "+pList.size());
				System.out.println("Mission Success!");
				
				break;
			//Read from text
			case 2:
				
				scanner.nextLine();
				System.out.print("Open year(start and end) : ");
				line = scanner.nextLine();
				splt = line.split(" ");
				opstart = splt[0];
				opend = splt[1];
				s = Integer.parseInt(opstart);
				e = Integer.parseInt(opend);
				
				for(int years=0; years<=e-s; years++) {
					s = s+years;
					tyear = Integer.toString(s);
					str = tyear+"-"+tyear+"_movie.txt";
					fHandler.readMovie(str);
					str = tyear+"-"+tyear+"_relation.txt";
					fHandler.readRelation(str);
				}
				Person p = new Person("최준영","20112096");
				//p.adj.add(NodeHandler.findbyNameCode("이경영", "10054755"));
				p.adj.add(NodeHandler.findbyNameCode("김원해", "20189746"));
				pList.add(p);

				nHandler.calAverage();
				Collections.sort(pList, new Comparator<Person>() {
		            @Override
		            public int compare(Person p1, Person p2) {
		                if (p1.average < p2.average) {
		                    return -1;
		                } else if (p1.average > p2.average) {
		                    return 1;
		                }
		                return 0;
		            }
		        });

				
				
				System.out.println("Record the results...");
				str = opstart+"-"+opend+"_average.txt";
				fHandler.writeAverage(str);
				
				str = opstart+"-"+opend+"_result.txt";
				fHandler.writeAResult(str);
				str = opstart+"-"+opend+"_year_info.txt";
				fHandler.writeYearInfo(str);
				System.out.println(opstart+"년부터 "+opend+"년 "+"총 영화: "+OpenApi.mList.size());
				System.out.println("총 배우: "+pList.size());
				System.out.println("Mission Success!");
				break;
		}
		
	}
}



