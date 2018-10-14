import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class FileHandler {
	
	public void readMovie(String filename) {
		try {
			
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\data\\movie\\";
			dir = dir + filename ;
			
			File file =new File(dir);
			
			//�Է� ��Ʈ�� ����
			FileReader fReader = new FileReader(file);
			//�Է� ���� ����
			BufferedReader bReader = new BufferedReader(fReader);
			String line = "";
		
			while ((line = bReader.readLine())!= null) {
					
				String[] splt = line.split("@");
				Movie movie = new Movie();
				movie.name = splt[0];
				movie.eName = splt[1];
				movie.code = splt[2];
				movie.typeNm = splt[3];
				movie.repGenre = splt[4];
				movie.openDt = splt[5];
				OpenApi.mList.add(movie);
				
			}
			bReader.close();
		}catch(FileNotFoundException e) {
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	public void readRelation(String filename) {
		try {
			
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\data\\relation\\";
			dir = dir + filename ;
			File file =new File(dir);
			//�Է� ��Ʈ�� ����
			FileReader fReader = new FileReader(file);
			//�Է� ���� ����
			BufferedReader bReader = new BufferedReader(fReader);
			String line = "";
			while ((line = bReader.readLine())!= null) {
				String[] splt = line.split("@");
				line = bReader.readLine();
				String[] splt2 = line.split("@");
				Person p = NodeHandler.findbyNameCode(splt[0],splt2[0]);
				for(int i=1; i<splt.length; i++) {
					p.addAdj(NodeHandler.findbyNameCode(splt[i],splt2[i]));
				}
			}
			bReader.close();
		}catch(FileNotFoundException e) {
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	public void writeMovie(String filename) {
		try {
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\data\\movie\\";
			dir = dir + filename ;
			File file =new File(dir);
			//���� �����, ������ ���� ����
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(dir, false));
			if(file.isFile()&&file.canWrite()) {
				for(int i=0; i<OpenApi.mList.size(); i++) {
					String str  = 
							OpenApi.mList.get(i).name+"@"
							+OpenApi.mList.get(i).eName+"@"
							+ OpenApi.mList.get(i).code+"@" 
							+OpenApi.mList.get(i).typeNm+"@"
							+OpenApi.mList.get(i).repGenre+"@"
							+OpenApi.mList.get(i).openDt;
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
	public void writeAverage(String filename) {
		try {
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\data\\Average\\";
			dir = dir + filename ;
			File file =new File(dir);
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(dir, false));
			//���� �б�
			if(file.isFile()&&file.canWrite()) {
				//����
				for(int i=0; i<JavaApp.pList.size(); i++) {
					String str  = i+1+"�� "+JavaApp.pList.get(i).getName()+"�� �������: "+JavaApp.pList.get(i).adjSize() 
							+" ��� �߽�����: "+JavaApp.pList.get(i).average;
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
	public void writeAResult(String filename) {
		try {
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\data\\result\\";
			dir = dir + filename ;
			File file =new File(dir);
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(dir, false));
			//���� �б�
			if(file.isFile()&&file.canWrite()) {
				//����
				for(int i=0; i<JavaApp.pList.size(); i++) {
					String str  = JavaApp.pList.get(i).average+" ";
					bWriter.write(str);
					
				}
			}
			bWriter.close();
		}catch(FileNotFoundException e) {
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	public void writeRelation(String filename) {
				try {
					
					String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\data\\relation\\";
					dir = dir + filename ;
					File file =new File(dir);
					BufferedWriter bWriter = new BufferedWriter(new FileWriter(dir, false));
					
					if(file.isFile()&&file.canWrite()) {
						for(int i=0; i<JavaApp.pList.size(); i++) {
							String str  = JavaApp.pList.get(i).name;
							String str2 = JavaApp.pList.get(i).code;
							for(int j=0; j<JavaApp.pList.get(i).adjSize(); j++) {
								 str = str + "@" + JavaApp.pList.get(i).adj.get(j).name;
								 str2 = str2 + "@" + JavaApp.pList.get(i).adj.get(j).code;
							}
							bWriter.write(str);
							bWriter.newLine();
							bWriter.write(str2);
							bWriter.newLine();
						}
					}
					bWriter.close();
				}catch(FileNotFoundException e) {
					
				}catch(IOException e) {
					System.out.println(e);
				}
	}
	public void writeFilmo(String filename) {
		try {
			
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\data\\filmo\\";
			dir = dir + filename ;
			File file =new File(dir);
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(dir, false));
			
			if(file.isFile()&&file.canWrite()) {
				for(int i=0; i<JavaApp.pList.size(); i++) {
					String str  = JavaApp.pList.get(i).name+"@"+JavaApp.pList.get(i).code;
					for(int j=0; j<JavaApp.pList.get(i).movie.size(); j++) {
						 str = str + "@" + JavaApp.pList.get(i).movie.get(j).name;
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
	public void writeYearInfo(String filename) {
		try {
			
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\data\\year_info\\";
			dir = dir + filename ;
			File file =new File(dir);
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(dir, false));
			String[] sub = filename.split("_");
			if(file.isFile()&&file.canWrite()) {
				String str = sub[0]+" | "+"�� ��ȭ ��: "+OpenApi.mList.size()+"�� ���: "+JavaApp.pList.size();
					bWriter.write(str);
					bWriter.newLine();
				
			}
			
			bWriter.close();
		}catch(FileNotFoundException e) {
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
}
	 

