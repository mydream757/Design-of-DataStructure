import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;

public class NodeHandler {
	public void insertEdge(Movie m) {
		int numOfActors = m.getActors().size();
		
		//배우 수가 1이면 edge 설정 불가
		if(numOfActors>1) {	
			
			
			for(int i=0; i<numOfActors; i++) {
				Person p1 = findbyName(m.getActors().get(i));
				
				for(int j=i+1; j<numOfActors; j++) {
					Person p2 = findbyName(m.getActors().get(j));
					if(!p1.isAdjacent(p2)) {	//엣지 설정 안되어있으면 설정
						p1.addAdj(p2);
						p1.setWeight(1);	//가중치 설정
						p2.addAdj(p1);
						p2.setWeight(1);
					} 
				} // for-end
					
			} // for-end
		}	//if-end
		
	}
	public static Person findbyName(String name) {
	
		//이름이 같은 배우를 찾아 노드를 반환
		for(int i=0; i<JavaApp.pList.size();i++) {
			//equals로 내용비교해야함
			if(JavaApp.pList.get(i).getName().equals(name)) return JavaApp.pList.get(i); 
		}
		//존재하지 않으면 새 노드를 만들어 반환
		Person node = new Person(name);
		JavaApp.pList.add(node);
		return node;
		
	}
	public void showPeople() {
		for(int i=0; i<JavaApp.pList.size(); i++) {
			Person p = JavaApp.pList.get(i);
			System.out.println(i+"번째 노드: "+p.getName());
			//System.out.println(p.getName()+" 의 인접노드는...");
			//노드의 adj 접근
			//for(int j=0; j<p.adj.size(); j++) {
			//	System.out.println(p.getAdj(j).getName()+" 과 "+"가중치 "+p.getWeight(j));	//인접노드의 이름 출력
			//}
			//System.out.println("\n");
		}
		
	}
	//P 중심 거리 평균 계산
	//정렬 need
	public static float calAverage(Person p) {
		float average;
		int sum = 0;
		AdjSort ascending = new AdjSort();
		Collections.sort(JavaApp.pList,ascending);
		
		//initialize visit
		for(Person node:JavaApp.pList) {
			node.visit = false;
			node.previous = null;
			node.dist = 0;
		}
		Queue<Person> q = new <Person> LinkedList();
		
		ArrayList<Person> path = new ArrayList<Person>();
		q.offer(p);
		p.visit = true;
		p.dist = 0;
		int visitCount = 0;
		while(!q.isEmpty()) {
			Person temp = q.poll();	//큐에서 꺼냄
			
			//System.out.println(temp.getName());
			for(Person node: temp.adj) {
				if(node.visit==false) {
					q.offer(node);	//큐에 삽입
					node.previous=temp;
					node.dist=node.previous.dist+1;
					node.visit=true;
					visitCount++;
				}
			}
		}
		for(int i=0; i<JavaApp.pList.size(); i++) {
			sum = sum + JavaApp.pList.get(i).dist;
		}
		average = ((float)sum)/(float)visitCount;
		return average;
	}
	//byBFS
	public ArrayList<Person> Bfs(String start, String end){
		Person s = NodeHandler.findbyName(start);
		Person e = NodeHandler.findbyName(end);
		
		Queue<Person> q = new <Person> LinkedList();
		
		ArrayList<Person> path = new ArrayList<Person>();
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
				}
			}
		}
		return path;
	}
}
