import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Person {
	String name;
	ArrayList<Person> adj = new ArrayList<Person>();
	ArrayList<Integer> weight = new ArrayList<Integer>();
	ArrayList<String> movie = new ArrayList<String>();
	int dist=0;
	boolean visit=false;
	Person previous;	//heap search¸¦ À§ÇÔ.
	float average=0;
	
	public Person() {
		
	}
	public Person(String s) {
		name = s;
	}
	/*	Getter	*/
	public String getName() {
		
		return name;
	}
	
	
	public Person getAdj(int i){
		
		return adj.get(i);
	}
	
	public int getWeight(int i){
		return weight.get(i);
	}
	public int adjSize() {
		return adj.size();
	}
	public int weightSize() {
		return weight.size();
	}
	
	/*	Setter	*/
	public void setWeight(int i) {
		weight.add(i);
	}
	
	
	
	public boolean isAdjacent(Person n) {
			
			
			if(adj.contains(n)) return true;
			else return false;
	}
	
	public void addAdj(Person n) {
		adj.add(n);
	}
	
	
 	
	
}
