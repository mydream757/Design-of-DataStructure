import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Person implements Comparable<Person>{
	String name;
	String eName;
	String code;
	ArrayList<Person> adj = new ArrayList<Person>();
	ArrayList<Integer> adjCount = new ArrayList<Integer>();
	ArrayList<Integer> weight = new ArrayList<Integer>();
	ArrayList<Movie> movie = new ArrayList<Movie>();
	int dist=0;
	boolean visit=false;
	Person previous;	//heap search¸¦ À§ÇÔ.
	float average=0;
	int visitingCount = 0;
	
	@Override
    public int compareTo(Person p){
		// TODO Auto-generated method stub
		if (this.adj.size() < p.adj.size()) {
            return -1;
        } else if (this.adj.size()> p.adj.size()) {
            return 1;
        }
        return 0;
	}
	public Person(String name) {
		this.name = name;
	}
	public Person(String name, String code) {
		this.name = name;
		this.code = code;
	}
	/*	Getter	*/
	public String getName() {
		
		return name;
	}
	public void setWeight(int i) {
		weight.add(i);
		
	}
	public void addWeight(Person p) {
		int i;
		for(i=0; i<adj.size(); i++) {
			
			if(adj.get(i)==p) break;
		}
		weight.set(i, weight.get(i)+1);
	}
	public int adjSize() {
		return adj.size();
	}
	public boolean isAdjacent(Person n) {
			
			
			if(adj.contains(n)) return true;
			else return false;
	}
	public void addAdj(Person n) {
		if(!adj.contains(n))
		adj.add(n);
	}
	
}
