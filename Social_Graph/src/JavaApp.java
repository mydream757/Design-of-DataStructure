import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class JavaApp {
	
	public static ArrayList<Movie> mList = new ArrayList<Movie>();
	public static ArrayList<Person> pList = new ArrayList<Person>();
	
	public static void main(String[] args) {
		System.out.println("The Social Graph of Actors and Actress\n");
		FileHandler fHandler = new FileHandler();
		NodeHandler nHandler = new NodeHandler();
		OpenApi web = new OpenApi();
		Scanner scanner = new Scanner(System.in);
		System.out.print("Insert the key : ");
		String line = scanner.next();
		web.key = line;
		scanner.nextLine();
		System.out.print("Open year(start and end) : ");
		line = scanner.nextLine();
		String[] splt = line.split(" ");
		String opstart = splt[0];
		String opend = splt[1];
		
		/*	영화 목록 불러오기
		web.getMovieList(opstart, opend);
		openDtSort Discending = new openDtSort();
		Collections.sort(mList,Discending);
		String str = opstart+"-"+opend+"_movie.txt";
		fHandler.writeMovie(str);
		*/
		
		String str = opstart+"-"+opend+"_movie.txt";
		fHandler.readMovie(str);
		
		for(int i=0; i<mList.size(); i++) {
			web.Connect(mList.get(i));
			nHandler.insertEdge(mList.get(i));
		}
		/*	영화의 배우목록을 봄	
		for(int i=0; i<movie.getActors().size(); i++) {
		System.out.println(movie.getActors().get(i));
		}
		*/
		/* pList Sort */
		//NameSort ascending = new NameSort();
		//AdjSort ascending = new AdjSort();
		//Collections.sort(pList,ascending);
		
		/*
		ArrayList<Person> path = new ArrayList<Person>();
		path = Person.Bfs("A", "I");
		Collections.reverse(path);
		System.out.println("path is....");
		for(Person n:path) {
			System.out.println(n.getName());
		}
		System.out.println("Distance: "+path.size());
		*/
		// 평균지수 구하기, 출력
		for(int i=0; i<pList.size(); i++) {
			float average = NodeHandler.calAverage(pList.get(i));
			pList.get(i).average = average;
			//System.out.println(NodeHandler.pList.get(i).getName()+"의 인접노드: "+NodeHandler.pList.get(i).adjSize() +" 평균 중심지수: "+average);
		}
		System.out.println("Record the results...");
		str = opstart+"-"+opend+"_average.txt";
		fHandler.writeAverage(str);
		str = opstart+"-"+opend+"_relation.txt";
		fHandler.writeInfo(str);
		
		System.out.println("Mission Success!");
		
	}
}

class NameSort implements Comparator<Person> {
	 
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName());
    }
 
}
 
// Integer 오름차순
class AdjSort implements Comparator<Person> {
 
    @Override
    public int compare(Person p1, Person p2) {
        return ((Integer)p1.adjSize()).compareTo((Integer)p2.adjSize());
    }
 
}

class openDtSort implements Comparator<Movie>{
	@Override
    public int compare(Movie m1, Movie m2) {
        return m2.openDt.compareTo(m1.openDt);
    }
	
}

