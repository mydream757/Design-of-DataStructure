import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class FileHandler {
	public ArrayList<String> cList = new ArrayList<String>();
		
	public void readCode() {
		try {
			//파일 객체
			File file =new File("C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\movieCode.txt");
			
			//입려 스트림 생성
			FileReader fReader = new FileReader(file);
			//입력 버퍼 생성
			BufferedReader bReader = new BufferedReader(fReader);
			String line = "";
			
			while ((line = bReader.readLine())!= null) {
				String[] splt = line.split(" ");
				cList.add(splt[0]);
			}
			bReader.close();
		}catch(FileNotFoundException e) {
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	public void readInfo(String filename) {
		try {
			
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\";
			dir = dir + filename ;
			
			File file =new File(dir);
			
			//입려 스트림 생성
			FileReader fReader = new FileReader(file);
			//입력 버퍼 생성
			BufferedReader bReader = new BufferedReader(fReader);
			String line = "";
		
			while ((line = bReader.readLine())!= null) {
					
					
				String[] splt = line.split("@");
				Person p = NodeHandler.findbyName(splt[0]);
				for(int i=1; i<splt.length; i++) {
					p.addAdj(NodeHandler.findbyName(splt[i]));
				}
				
			}
			bReader.close();
		}catch(FileNotFoundException e) {
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	public void writeAverage(String filename) {
		try {
			
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\";
			dir = dir + filename ;
			
			File file =new File(dir);
			if(file.exists()==false) {
				file.createNewFile();
			}
			
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
			//파일 읽기
			if(file.isFile()&&file.canWrite()) {
				//쓰기
				for(int i=0; i<NodeHandler.pList.size(); i++) {
					String str  = NodeHandler.pList.get(i).getName()+"의 인접노드: "+NodeHandler.pList.get(i).adjSize() +" 평균 중심지수: "+NodeHandler.pList.get(i).average;
					
					bWriter.write(str);
					bWriter.newLine();
				}
			}
			bWriter.close();
		}catch(FileNotFoundException e) {
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	public void writeInfo(String filename) {
				try {
					
					String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\";
					dir = dir + filename ;
					
					File file =new File(dir);
					if(file.exists()==false) {
						file.createNewFile();
					}
					
					BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
					//파일 읽기
					if(file.isFile()&&file.canWrite()) {
						//쓰기
						for(int i=0; i<NodeHandler.pList.size(); i++) {
							String str  = NodeHandler.pList.get(i).getName();
							for(int j=0; j<NodeHandler.pList.get(i).adjSize(); j++) {
								 str = str + "@" + NodeHandler.pList.get(i).adj.get(j).name;
							}
							bWriter.write(str);
							bWriter.newLine();
						}
					}
					bWriter.close();
				}catch(FileNotFoundException e) {
					
				}catch(IOException e) {
					System.out.println(e);
				}
	}
}
	 

