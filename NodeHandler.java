import java.util.Iterator;
import java.util.ArrayList;

public class NodeHandler {
	
	public static void insertEdge(Movie m) {
		int numOfActors = m.getActors().size();
		
		//��� ���� 1�̸� edge ���� �Ұ�
		if(numOfActors>1) {	
			
			
			for(int i=0; i<numOfActors; i++) {
				Person p1 = NodeHandler.findbyName(m.getActors().get(i));
				
				for(int j=i+1; j<numOfActors; j++) {
					Person p2 = NodeHandler.findbyName(m.getActors().get(j));
					if(!p1.isAdjacent(p2)) {	//���� ���� �ȵǾ������� ����
						p1.addAdj(p2);
						p1.setWeight(1);	//����ġ ����
						p2.addAdj(p1);
						p2.setWeight(1);
					} 
				} // for-end
					
			} // for-end
		}	//if-end
		
	}
	
	
	public static Person findbyName(String name) {
		//�̸��� ���� ��츦 ã�� ��带 ��ȯ
		for(int i=0; i<JavaApp.pList.size();i++) {
			//equals�� ������ؾ���
			if(JavaApp.pList.get(i).getName().equals(name)) return JavaApp.pList.get(i); 
		}
		//�������� ������ �� ��带 ����� ��ȯ
		Person node = new Person(name);
		JavaApp.pList.add(node);
		return node;
		
	}
	
	public static void showPeople() {
		for(int i=0; i<JavaApp.pList.size(); i++) {
			Person p = JavaApp.pList.get(i);
			System.out.println(i+"��° ���: "+p.getName());
			//System.out.println(p.getName()+" �� ��������...");
			//����� adj ����
			//for(int j=0; j<p.adj.size(); j++) {
			//	System.out.println(p.getAdj(j).getName()+" �� "+"����ġ "+p.getWeight(j));	//��������� �̸� ���
			//}
			//System.out.println("\n");
		}
		
	}
	
	
}
