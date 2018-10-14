import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;

public class NodeHandler {
	int Max_dist =0;
	public void insertEdge(Movie m) {
		int numOfActors = m.getActors().size();
		
		//��� ���� 1�̸� edge ���� �Ұ�
		if(numOfActors>1) {	
			
			
			for(int i=0; i<numOfActors; i++) {
				Person p1 = findbyNameCode(m.getActors().get(i).name,m.getActors().get(i).code);
				
				for(int j=i+1; j<numOfActors; j++) {
					Person p2 = findbyNameCode(m.getActors().get(j).name,m.getActors().get(j).code);
					if(!p1.isAdjacent(p2)) {	//���� ���� �ȵǾ������� ����
						p1.addAdj(p2);
						p1.setWeight(1);	//����ġ ����
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
		//�̸��� ���� ��츦 ã�� ��带 ��ȯ
				for(int i=0; i<JavaApp.pList.size();i++) {
					//equals�� ������ؾ���
					if(JavaApp.pList.get(i).code.equals(code)&&JavaApp.pList.get(i).name.equals(name)) return JavaApp.pList.get(i); 
				}
				//�������� ������ �� ��带 ����� ��ȯ
				Person node = new Person(name,code);
				JavaApp.pList.add(node);
				return node;
		
	}
	
	public void showPeople() {
		for(int i=0; i<JavaApp.pList.size(); i++) {
			Person p = JavaApp.pList.get(i);
			System.out.println(i+"��° ���: "+p.getName());
			
		}
		
	}
	//P �߽� �Ÿ� ��� ���
	//���� need
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
				Person temp = q.poll();	//ť���� ����
				
				for(Person node: temp.adj) {
					if(node.visit==false) {
						q.offer(node);	//ť�� ����
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
			Person temp = q.poll();	//ť���� ����
			
			//System.out.println(temp.getName());
			for(Person node: temp.adj) {
				if(node.visit==false) {
					q.offer(node);	//ť�� ����
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
