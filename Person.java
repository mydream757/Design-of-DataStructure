import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Person {
	String name;
	ArrayList<Person> adj = new ArrayList<Person>();
	ArrayList<Integer> weight = new ArrayList<Integer>();
	ArrayList<String> movie = new ArrayList<String>();
	int dist=0;
	boolean visit=false;
	Person previous;
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
	
	/*
	//name에 도달하는 최단경로 찾기
 	public static ArrayList<Person> FindPath(String s, String e){
		
 		//start Node
 		Person start = NodeHandler.findbyName(s);
 		
 		//target Node
 		Person end = NodeHandler.findbyName(e);
		//next Node
 		ArrayList<Person> tmp = new ArrayList<Person>();
		ArrayList<Person> result = new ArrayList<Person>();
 		int distance = 9999;
 		int i=0;
		//출발지 설정
 		Person node = start;
 		Iterator iter = node.adj.iterator(); 
 		while(iter.hasNext()) {
 			return FindPath(node,end);
 			
 			
 		}
	}*/
 	//byBFS
	public static ArrayList<Person> Bfs(String start, String end){
		Person s = NodeHandler.findbyName(start);
		Person e = NodeHandler.findbyName(end);
		
		Queue<Person> q = new <Person> LinkedList();
		
		//ArrayList<Person> visit = new ArrayList<Person>();
		//ArrayList<Integer> distance = new ArrayList<Integer>();
		ArrayList<Person> path = new ArrayList<Person>();
		ArrayList<Person> result = new ArrayList<Person>();
		q.offer(s);
		s.visit = true;
		s.dist = 0;
		while(!q.isEmpty()) {
			Person temp = q.poll();	//큐에서 꺼냄
			
			//System.out.println(temp.getName());
			for(Person node: temp.adj) {
				
				
				if(node.visit==false) {
					q.offer(node);	//큐에 삽입
					node.previous=temp;
					node.dist=node.previous.dist+1;
					node.visit=true;
					
				}
				if(node.equals(e)) {
					//getPath
					Person tmp = node;
					path.add(node);
					for(int i=0; i<node.dist; i++) {
						tmp = tmp.previous;
						path.add(tmp);
					}
					//if문, 경로 거리 비교
					/*
					if(result.size() <= path.size()) {
						result = path;
					}
					*/
					
				}
			}
			
		}
		return path;
	}
}
