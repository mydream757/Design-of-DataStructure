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
				// parsing�� url ����(API Ű �����ؼ�)
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
				/* ��ȭ ���� �Ӽ��� ������ ���� */
				NodeList nList = doc.getElementsByTagName("movieInfo");
				for(Node node = nList.item(0).getFirstChild(); node !=null; node=node.getNextSibling()) {
					//��ȭ �̸�
					if(node.getNodeName().equals("movieNm")) {
						movie.setName(node.getTextContent());
						System.out.println(movie.getName());
					}
					//��ȭ ����
					//��ȭ �ڵ�
					//��Ÿ ���
				}
				
				/* actors �±׷� ��帮��Ʈ ������ */
				nList = doc.getElementsByTagName("actor");
				System.out.println("�Ľ��� ����Ʈ �� : "+ nList.getLength());
				
				for(int temp = 0; temp < nList.getLength(); temp++){		
					Node nNode = nList.item(temp);
					if(nNode.getNodeType() == Node.ELEMENT_NODE){
										
						Element eElement = (Element) nNode;
						System.out.println("######################");
						//System.out.println(eElement.getTextContent()); //��ü ����Ʈ ������
						/* �Ľ� Ȯ�� */
						String name = getTagValue("peopleNm", eElement);
						System.out.println("��� �̸�  : " + name);
						System.out.println("���� �̸�  : " + getTagValue("peopleNmEn", eElement));
						movie.addActors(name);
					}	// if end
				}	// for end
				
		} catch (Exception e){	
			e.printStackTrace();
		}	// try~catch end
		
		return movie;
	}
}
