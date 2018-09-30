import java.util.ArrayList;

public class Movie {
	String name;
	int code;
	String director;
	ArrayList<String> actors = new ArrayList<String>();

	public void setName(String s) {
		name = s;
	}
	public void setDirector(String s) {
		
		director = s;
	}
	public String getName() {
		return name;
	}
	public void addActors(String s) {
		actors.add(s);
	}
	
	public void setCode(int i) {
		code = i;
	}
	public ArrayList<String> getActors(){
		
		return actors;
	}
}
