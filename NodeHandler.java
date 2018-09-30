import java.util.Iterator;
import java.util.ArrayList;

public class NodeHandler {
	
	public static void insertEdge(Movie m) {
		int numOfActors = m.getActors().size();
		
		//배우 수가 1이면 edge 설정 불가
		if(numOfActors>1) {	
			
			
			for(int i=0; i<numOfActors; i++) {
				Person p1 = NodeHandler.findbyName(m.getActors().get(i));
				
				for(int j=i+1; j<numOfActors; j++) {
					Person p2 = NodeHandler.findbyName(m.getActors().get(j));
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
	
	public static void showPeople() {
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
	
	
}
