import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OpenApi {
	
	String norm = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.xml";
	String key = "47254930c7c1bb050f3261d23bb8be10";
	String code = "20179101";
	
	public String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if(nValue == null)
			return null;
		return nValue.getNodeValue();
	}
	public String urlSum(String c) {
		code = c;
		String urlstr=norm+"?key="+key+"&movieCd="+c;
		return urlstr;
	}
	
	public  Movie Connect(String cd) {
			Movie movie = new Movie();
				// parsing할 url 지정(API 키 포함해서)
			String url = this.urlSum(cd);
			System.out.println("Connection:"+url);
			try{
				
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);
				
				// root tag 
				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				//Root element: movieInfoResult
				/* 영화 관련 속성을 빼오기 위함 */
				NodeList nList = doc.getElementsByTagName("movieInfo");
				for(Node node = nList.item(0).getFirstChild(); node !=null; node=node.getNextSibling()) {
					//영화 이름
					if(node.getNodeName().equals("movieNm")) {
						movie.setName(node.getTextContent());
						System.out.println(movie.getName());
					}
					//영화 감독
					//영화 코드
					//기타 등등
				}
				
				/* actors 태그로 노드리스트 재정의 */
				nList = doc.getElementsByTagName("actor");
				System.out.println("파싱할 리스트 수 : "+ nList.getLength());
				
				for(int temp = 0; temp < nList.getLength(); temp++){		
					Node nNode = nList.item(temp);
					if(nNode.getNodeType() == Node.ELEMENT_NODE){
										
						Element eElement = (Element) nNode;
						System.out.println("######################");
						//System.out.println(eElement.getTextContent()); //전체 컨텐트 가져옴
						/* 파싱 확인 */
						String name = getTagValue("peopleNm", eElement);
						System.out.println("배우 이름  : " + name);
						System.out.println("영문 이름  : " + getTagValue("peopleNmEn", eElement));
						movie.addActors(name);
					}	// if end
				}	// for end
				
		} catch (Exception e){	
			e.printStackTrace();
		}	// try~catch end
		
		return movie;
	}
}
