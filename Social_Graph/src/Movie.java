import java.util.ArrayList;

public class Movie {
	String name;
	String code;
	String repGenre;
	ArrayList<String> actors = new ArrayList<String>();
	//String prdtYear; ���۳⵵?
	String openDt; //���� ��¥ 20180228
	String typeNm; //ex) ����
	
	public void addActors(String s) {
		actors.add(s);
	}
	
	
	public ArrayList<String> getActors(){
		
		return actors;
	}
}
