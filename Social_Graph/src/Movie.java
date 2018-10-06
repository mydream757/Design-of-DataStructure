import java.util.ArrayList;

public class Movie {
	String name;
	String code;
	String repGenre;
	ArrayList<String> actors = new ArrayList<String>();
	//String prdtYear; 제작년도?
	String openDt; //개봉 날짜 20180228
	String typeNm; //ex) 장편
	
	public void addActors(String s) {
		actors.add(s);
	}
	
	
	public ArrayList<String> getActors(){
		
		return actors;
	}
}
