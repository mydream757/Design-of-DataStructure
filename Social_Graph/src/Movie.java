import java.util.ArrayList;
import java.util.Comparator;

public class Movie implements Comparator<Movie>{
	String name;
	String eName;
	String code;
	String repGenre;
	ArrayList<Person> actors = new ArrayList<Person>();
	//String prdtYear; ���۳⵵?
	String openDt; //���� ��¥ 20180228
	String typeNm; //ex) ����
	
	@Override
	public int compare(Movie m1, Movie m2) {
		// TODO Auto-generated method stub
		if (Integer.parseInt((m1.openDt)) < Integer.parseInt(m2.openDt)) {
            return -1;
        } else if (Integer.parseInt(m1.openDt) > Integer.parseInt(m2.openDt)) {
            return 1;
        }
        return 0;
		
	}
	public void addActors(Person p) {
		if(!actors.contains(p)) {
			actors.add(p);
		}
	}

	
	public ArrayList<Person> getActors(){
		
		return actors;
	}

	
}
