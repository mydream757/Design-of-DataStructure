import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;

public class NodeHandler {
	int Max_dist =0;
	public void insertEdge(Movie m) {
		int numOfActors = m.getActors().size();
		
		//배우 수가 1이면 edge 설정 불가
		if(numOfActors>1) {	
			
			
			for(int i=0; i<numOfActors; i++) {
				Person p1 = findbyNameCode(m.getActors().get(i).name,m.getActors().get(i).code);
				
				for(int j=i+1; j<numOfActors; j++) {
					Person p2 = findbyNameCode(m.getActors().get(j).name,m.getActors().get(j).code);
					if(!p1.isAdjacent(p2)) {	//엣지 설정 안되어있으면 설정
						p1.addAdj(p2);
						p1.setWeight(1);	//가중치 설정
						p2.addAdj(p1);
						p2.setWeight(1);
					}else {
						p1.addWeight(p2);
						p2.addWeight(p1);
					}
				} // for-end
					
			} // for-end
		}	//if-end
		
	}
	public static Person findbyNameCode(String name, String code) {
		//이름이 같은 배우를 찾아 노드를 반환
				for(int i=0; i<JavaApp.pList.size();i++) {
					//equals로 내용비교해야함
					if(JavaApp.pList.get(i).code.equals(code)&&JavaApp.pList.get(i).name.equals(name)) return JavaApp.pList.get(i); 
				}
				//존재하지 않으면 새 노드를 만들어 반환
				Person node = new Person(name,code);
				JavaApp.pList.add(node);
				return node;
		
	}
	
	public void showPeople() {
		for(int i=0; i<JavaApp.pList.size(); i++) {
			Person p = JavaApp.pList.get(i);
			System.out.println(i+"번째 노드: "+p.getName());
			
		}
		
	}
	//P 중심 거리 평균 계산
	//정렬 need
	public void calAverage() {
		Collections.sort(JavaApp.pList);
		
		//initialize visit
		for(Person node:JavaApp.pList) {
			node.visit = false;
			node.previous = null;
			node.dist = 0;
		}
		Queue<Person> q = new <Person> LinkedList();
		
		for(int i=0; i<JavaApp.pList.size(); i++) {
			Person p = JavaApp.pList.get(i);
			for(Person node:JavaApp.pList) {
				node.visit = false;
				node.previous = null;
				node.dist = 0;
			}
			q.offer(p);
			p.visit = true;
			p.dist = 0;
			p.visitingCount = 0;
			while(!q.isEmpty()) {
				Person temp = q.poll();	//큐에서 꺼냄
				
				for(Person node: temp.adj) {
					if(node.visit==false) {
						q.offer(node);	//큐에 삽입
						node.previous=temp;
						node.dist=node.previous.dist+1;
						if(Max_dist<node.dist) Max_dist = node.dist;
						node.visit=true;
						p.visitingCount++;
					}
				}
			}
			for(int j=0; j<JavaApp.pList.size(); j++) {
				p.average = p.average + JavaApp.pList.get(j).dist;
			}
			
		}
		for(int i=0; i<JavaApp.pList.size(); i++) {
			Person p = JavaApp.pList.get(i);
			p.average = p.average + (JavaApp.pList.size()-1-p.visitingCount)*Max_dist;
			p.average = p.average/(JavaApp.pList.size()-1);
		}
	}
	//byBFS
	public ArrayList<Person> Bfs(Person s, Person e){
		
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
