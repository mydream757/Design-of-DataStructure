import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OpenApi {
	
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
						movie.name = getTagValue("movieNm", eElement);
						movie.code = getTagValue("movieCd",eElement);
						movie.openDt = getTagValue("openDt",eElement);
						movie.repGenre = getTagValue("repGenreNm",eElement);
						movie.typeNm = getTagValue("typeNm",eElement);
						//this.Connect(movie.code,movie);
						//nHandler.insertEdge(movie);
						JavaApp.mList.add(movie);				//영화 저장
						c++;
					}	// if end
				}	// for end
				
			}
			System.out.println("확인된 영화 수: "+count);
			System.out.println("저장 영화 수: "+ c);
		} catch (Exception e){	
			e.printStackTrace();
		}	// try~catch end
	}
	public void Connect(Movie movie) {
			String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.xml"+"?key="+key+"&movieCd="+movie.code;
			try{
				
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);
				doc.getDocumentElement().normalize();
				if(doc!=null) System.out.println("Connected:"+url);
				
				/* 출연진 정보 얻기 */
				NodeList nList = doc.getElementsByTagName("actor");
				
				for(int temp = 0; temp < nList.getLength(); temp++){		
					Node nNode = nList.item(temp);
					if(nNode.getNodeType() == Node.ELEMENT_NODE){
										
						Element eElement = (Element) nNode;
						/* 파싱 확인 */
						String name = getTagValue("peopleNm", eElement);
						movie.addActors(name);
					}	// if end
				}	// for end
				
		} catch (Exception e){	
			e.printStackTrace();
		}	// try~catch end
	}
}
