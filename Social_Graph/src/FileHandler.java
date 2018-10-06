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
			
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\";
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
				movie.code = splt[1];
				movie.typeNm = splt[2];
				movie.repGenre = splt[3];
				movie.openDt = splt[4];
				JavaApp.mList.add(movie);
				
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
			//�Է� ��Ʈ�� ����
			FileReader fReader = new FileReader(file);
			//�Է� ���� ����
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
	public void writeMovie(String filename) {
		try {
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\";
			dir = dir + filename ;
			File file =new File(dir);
			//���� �����, ������ ���� ����
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(dir, false));
			if(file.isFile()&&file.canWrite()) {
				for(int i=0; i<JavaApp.mList.size(); i++) {
					String str  = 
							JavaApp.mList.get(i).name+"@"
							+ JavaApp.mList.get(i).code+"@" 
							+JavaApp.mList.get(i).typeNm+"@"
							+JavaApp.mList.get(i).repGenre+"@"
							+JavaApp.mList.get(i).openDt;
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
			String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\";
			dir = dir + filename ;
			File file =new File(dir);
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(dir, false));
			//���� �б�
			if(file.isFile()&&file.canWrite()) {
				//����
				for(int i=0; i<JavaApp.pList.size(); i++) {
					String str  = JavaApp.pList.get(i).getName()+"�� �������: "+JavaApp.pList.get(i).adjSize() 
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
	public void writeInfo(String filename) {
				try {
					
					String dir = "C:\\Users\\Joonyoung\\eclipse-workspace\\Social_Graph\\";
					dir = dir + filename ;
					File file =new File(dir);
					BufferedWriter bWriter = new BufferedWriter(new FileWriter(dir, false));
					
					if(file.isFile()&&file.canWrite()) {
						for(int i=0; i<JavaApp.pList.size(); i++) {
							String str  = JavaApp.pList.get(i).getName();
							for(int j=0; j<JavaApp.pList.get(i).adjSize(); j++) {
								 str = str + "@" + JavaApp.pList.get(i).adj.get(j).name;
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
	 

