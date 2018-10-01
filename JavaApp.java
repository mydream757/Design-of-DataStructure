import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JavaApp {
	
	
	
	public static void main(String[] args) {
		System.out.println("The Social Graph of Actors and Actress\n");
		FileHandler fHandler = new FileHandler();
		NodeHandler nHandler = new NodeHandler();
		OpenApi movies = new OpenApi();	
		
		
		fHandler.readInfo("Result.txt");
		
		//Test: 10 ,  cList.size()
		fHandler.readCode();
		/*
		for(int i=0; i<fHandler.cList.size(); i++) {
			String code = fHandler.cList.get(i);
			Movie movie = movies.Connect(code);
			movies.mList.add(movie);
			nHandler.insertEdge(movie);
			nHandler.showPeople();
					
		}
		*/
		
		/*	��ȭ�� ������� ��	
		for(int i=0; i<movie.getActors().size(); i++) {
		System.out.println(movie.getActors().get(i));
		}
		*/
		
		//NameSort ascending = new NameSort();
		/*	����
		AdjSort ascending = new AdjSort();
		Collections.sort(NodeHandler.pList,ascending);
		*/
		
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
		
		
		for(int i=0; i<NodeHandler.pList.size(); i++) {
			float average = nHandler.calAverage(NodeHandler.pList.get(i));
			NodeHandler.pList.get(i).average = average;
			System.out.println(NodeHandler.pList.get(i).getName()+"�� �������: "+NodeHandler.pList.get(i).adjSize() +" ��� �߽�����: "+average);
			
		}
		fHandler.writeAverage("average.txt");
		fHandler.writeInfo("Information.txt");
		
		System.out.println("Good bye");
		
	}
	
	
	
}

class NameSort implements Comparator<Person> {
	 
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName());
    }
 
}
 
// Integer ��������
class AdjSort implements Comparator<Person> {
 
    @Override
    public int compare(Person p1, Person p2) {
        return ((Integer)p1.adjSize()).compareTo((Integer)p2.adjSize());
    }
 
}


