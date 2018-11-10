import java.io.*;
import java.util.*;

public class Simulation {
	
	public static void HandleCommand(String command) {
		//��commandͨ���ո���зָ��һ��������ŵ�List��
		 List<String> list = new ArrayList<String>();
		 for(String retval : command.split(" ")) {
			 list.add(retval);
		 }
		 
		 //��������
		 String instruct = list.get(0);
		 if(instruct.equals("cat")) {
			 list.remove(0);
			 instruct = list.get(0); //Ҫ��ȡ���ļ�
			 list.remove(0);
			 
			 try {
				 BufferedReader br = new BufferedReader(new FileReader(instruct));
				 String line; // ��ȡһ������
				//listΪ�գ�˵��ֻ��1��cat����
				 if(list.isEmpty()) {
					 //ֱ�Ӷ�ȡ�ļ��������ļ�д�뵽����̨��
					 while((line = br.readLine()) != null) {
						 System.out.println(line); //ֱ������ļ�
					 }
				 }else {
					 if((instruct = list.get(0)).equals("|")) {
						 list.remove(0);
						 instruct = list.get(0);
						 if(instruct.equals("grep")) {
							 list.remove(0);
							 instruct = list.get(0);  //�ؼ���
							 list.remove(0);
							 List<String> l = new ArrayList<String>();
							 //��filename�е��ļ�һ��һ�зŵ�l��
							 while((line = br.readLine()) != null) {
								 if(line.contains(instruct)) 
									 l.add(line);
							 }
							 
							 if(list.isEmpty()) {
								 for(String val : l) 
									 System.out.println(val);  //����йؼ��ֵ���
							 }else {
								 if((instruct = list.get(0)).equals("|")) {
									 list.remove(0);
									 instruct = list.get(0);
									 if(instruct.equals("wc")) {
										 list.remove(0);
										 instruct = list.get(0);
										 if(instruct.equals("-l")) {
											 list.remove(0);
											 if(list.isEmpty()) 
												 System.out.println(l.size()); //ͳ������
											 else 
											 System.out.println("��ʽ����");
										 }
									 }
								 }
							 } 
							 
						 }else if(instruct.equals("wc")) {
							 list.remove(0);
							 instruct = list.get(0);
							 int cnt = 0;
							 if(instruct.equals("-l")) {
								 list.remove(0);
								 if(list.isEmpty()) {
									 while((line = br.readLine()) != null) {
										 cnt++;
									 }
									 System.out.println(cnt); //�ı���������
								 }else {
									 System.out.println("��ʽ����");
								 }
							 }
								 
						 }else {
							 System.out.println("�����������������룡");
						 }
					 }
				 }
				 
				 br.close();
			 } catch(IOException e) {
					
			 }
 
		 }else if(instruct.equals("grep")) { 
			 String key = list.get(1);
			 String filename = list.get(2);
			 try {
				 BufferedReader br = new BufferedReader(new FileReader(filename));
				 String line;
				 List<String> l = new ArrayList<String>();
				 while((line = br.readLine()) != null) {
					 if(line.contains(key))
						 l.add(line);
				 }
				 if(list.size() == 3) {  //grep keyword filename
					for(String val : l)
						System.out.println(val);
				 }else if(list.size() > 3){
					 if(list.get(3).equals("|") || list.get(4).equals("wc") || list.get(5).equals("-l"))
						 System.out.println(l.size());
				 } 
				 br.close();
			 }catch(IOException e) {
				 
			 }
			 
		 }else if(instruct.equals("wc")) {
			 if(list.get(1).equals("-l")) {
				 String filename = list.get(2);
				 try {
					 BufferedReader br = new BufferedReader(new FileReader(filename));
					 String str;
					 int cnt = 0;
					 while((str = br.readLine()) != null)
						 cnt++;
					 System.out.println(cnt);
					 
					 br.close();
				 }catch(IOException e) {
					 
				 }
			 }
		 }else {
			 System.out.println("�����ʽ�������ȷ��");
		 }
	}
	
	public static void main(String[] args) {
		//����shell����
		Scanner scan = new Scanner(System.in);
		String com = "";
		int i = 0;
		while(i < 10) {
			System.out.print("[wuqian@localhost ~]$");
			com = scan.nextLine();
			HandleCommand(com);
		}
		scan.close();
	}
	
}






