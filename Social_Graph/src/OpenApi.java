import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OpenApi {
	public static ArrayList<Movie> mList = new ArrayList<Movie>();
	
	String key = "47254930c7c1bb050f3261d23bb8be10";
	
	public String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if(nValue == null)
			return null;
		return nValue.getNodeValue();
	}
	
	public void getMovieList(String start, String end) {
		//NodeHandler nHandler = new NodeHandler();
		
		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.xml?key="+this.key+"&repNationCd=22041011"+"&itemPerPage=100"+"&openStartDt="+start+"&openEndDt="+end+"&curPage=1";
		
		try {
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dbBuilder.parse(url);
			
			int count=0;
			NodeList nList = doc.getElementsByTagName("movieListResult");
			for(Node node = nList.item(0).getFirstChild(); node !=null; node=node.getNextSibling()) {
					
				if(node.getNodeName().equals("totCnt")) {
					count = Integer.parseInt(node.getTextContent());
					System.out.println("검색된 영화 수:"+count);
				}
			}
			//xml 페이지 loop
			int c = 0;
			for(int i=1; i<=count/100+1; i++) {
			//for(int i=1; i<2; i++) {
				url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.xml?key="+this.key+"&repNationCd=22041011"+"&itemPerPage=100"+"&openStartDt="+start+"&openEndDt="+end+"&curPage="+String.valueOf(i);
				dbFactoty = DocumentBuilderFactory.newInstance();
				dbBuilder = dbFactoty.newDocumentBuilder();
				doc = dbBuilder.parse(url);
				if(doc!=null) System.out.println("Connected:"+url);
				nList = doc.getElementsByTagName("movie");
				
				//i번째 페이지에 대한 loop
				//영화 탐색
				for(int temp = 0; temp < nList.getLength(); temp++){		
					Node nNode = nList.item(temp);
					Movie movie = new Movie();
					if(nNode.getNodeType() == Node.ELEMENT_NODE){
										
						Element eElement = (Element) nNode;
						/* 파싱 확인 */
						if(getTagValue("movieNmEn",eElement)!=null) {
							movie.name = getTagValue("movieNm", eElement);
							movie.eName = getTagValue("movieNmEn",eElement);
							movie.code = getTagValue("movieCd",eElement);
							movie.openDt = getTagValue("openDt",eElement);
							movie.repGenre = getTagValue("repGenreNm",eElement);
							movie.typeNm = getTagValue("typeNm",eElement);
							//this.Connect(movie);
							//nHandler.insertEdge(movie);
							mList.add(movie);				//영화 저장
							c++;
						}
					}	// if end
				}	// for end
				
			}
			System.out.println("확인된 영화 수: "+c);	
		} catch (Exception e){	
			e.printStackTrace();
		}	// try~catch end
	}
	public void UpdateActors(Movie movie) {
			String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.xml"+"?key="+key+"&movieCd="+movie.code;
			try{
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);
				doc.getDocumentElement().normalize();
				if(doc!=null) System.out.println("Connect movie:"+url);
				
				/* 출연진 정보 얻기 */
				NodeList nList = doc.getElementsByTagName("actor");
				for(int temp = 0; temp < nList.getLength(); temp++){		
					Node nNode = nList.item(temp);
					if(nNode.getNodeType() == Node.ELEMENT_NODE){
										
						Element eElement = (Element) nNode;
						if(getTagValue("peopleNmEn",eElement)!=null) {
							String name = getTagValue("peopleNm", eElement);
							String eName = getTagValue("peopleNmEn",eElement);
							String code = getActorCode(eName, movie.eName);
							Person p = NodeHandler.findbyNameCode(name, code);
							p.eName = eName;
							p.movie.add(movie);
							movie.addActors(p);
						}
							
					}	// if end
				}	// for end
		} catch (Exception e){	
			e.printStackTrace();
		}	// try~catch end
	}
	public String getActorCode(String actor_name, String movie_name) {
		String aName = spaceNameChanger(actor_name);
		String mName = spaceNameChanger(movie_name);
		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleList.xml"+"?key="+key+"&peopleNm="+aName+"&itemPerPage=100"+"&filmoNames="+mName;
		try{
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			doc.getDocumentElement().normalize();
			if(doc!=null) System.out.println("Connect actor_code:"+url);
			
			NodeList nList = doc.getElementsByTagName("people");
			for(int temp = 0; temp < nList.getLength(); temp++){		
				Node nNode = nList.item(temp);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
									
					Element eElement = (Element) nNode;
					//if(getTagValue("repRoleNm",eElement).equals("배우")||getTagValue("repRoleNm",eElement).equals("감독")) {
						String code = getTagValue("peopleCd", eElement);
						return code;
					//}
						
					
				}	// if end
			}	// for end
		} catch (Exception e){	
			e.printStackTrace();
		}	
		return "none";
	}
	public String spaceNameChanger(String name) {
		String[] splt = name.split(" ");
		String str="";
		str = splt[0];
		for(int i=1; i<splt.length; i++) {
			str = str +"%20"+ splt[i];
		}
		return str;
	}
}
