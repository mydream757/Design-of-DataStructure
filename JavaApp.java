import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JavaApp {
	public static ArrayList<Movie> mList = new ArrayList<Movie>();
	public static ArrayList<String> cList = new ArrayList<String>();
	public static ArrayList<Person> pList = new ArrayList<Person>();
	
	public static void main(String[] args) {
		System.out.println("The Social Graph of Actors and Actress\n");
		FileHandler fHandler = new FileHandler();
		OpenApi web = new OpenApi();	
		
		
		//fHandler.readInfo("Information.txt");
		
		//Test: 10 , base: cList.size()
		fHandler.readCode();
		for(int i=0; i<cList.size(); i++) {
			String code = cList.get(i);
			Movie movie = web.Connect(code);
			mList.add(movie);
			NodeHandler.insertEdge(movie);
			NodeHandler.showPeople();
					
		}
		
		
		/*	영화의 배우목록을 봄	
		for(int i=0; i<movie.getActors().size(); i++) {
		System.out.println(movie.getActors().get(i));
		}
		*/
		
		//NameSort ascending = new NameSort();
		AdjSort ascending = new AdjSort();
		Collections.sort(pList,ascending);
		/*
		 * ArrayList<Person> path = new ArrayList<Person>();
		path = Person.Bfs("A", "I");
		Collections.reverse(path);
		System.out.println("path is....");
		for(Person n:path) {
			System.out.println(n.getName());
		}
		System.out.println("Distance: "+path.size());
		*/
		fHandler.writeInfo("Result.txt");
		System.out.println("Good bye");
		
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


