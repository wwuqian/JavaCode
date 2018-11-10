import java.io.*;
import java.util.*;

public class Simulation {
	
	public static void HandleCommand(String command) {
		//将command通过空格进行分割，将一个个命令放到List中
		 List<String> list = new ArrayList<String>();
		 for(String retval : command.split(" ")) {
			 list.add(retval);
		 }
		 
		 //解析命令
		 String instruct = list.get(0);
		 if(instruct.equals("cat")) {
			 list.remove(0);
			 instruct = list.get(0); //要读取的文件
			 list.remove(0);
			 
			 try {
				 BufferedReader br = new BufferedReader(new FileReader(instruct));
				 String line; // 读取一行数据
				//list为空，说明只有1个cat命令
				 if(list.isEmpty()) {
					 //直接读取文件，并将文件写入到控制台中
					 while((line = br.readLine()) != null) {
						 System.out.println(line); //直接输出文件
					 }
				 }else {
					 if((instruct = list.get(0)).equals("|")) {
						 list.remove(0);
						 instruct = list.get(0);
						 if(instruct.equals("grep")) {
							 list.remove(0);
							 instruct = list.get(0);  //关键字
							 list.remove(0);
							 List<String> l = new ArrayList<String>();
							 //将filename中的文件一行一行放到l中
							 while((line = br.readLine()) != null) {
								 if(line.contains(instruct)) 
									 l.add(line);
							 }
							 
							 if(list.isEmpty()) {
								 for(String val : l) 
									 System.out.println(val);  //输出有关键字的行
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
												 System.out.println(l.size()); //统计行数
											 else 
											 System.out.println("格式错误！");
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
									 System.out.println(cnt); //文本的总行数
								 }else {
									 System.out.println("格式错误！");
								 }
							 }
								 
						 }else {
							 System.out.println("命令有误，请重新输入！");
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
			 System.out.println("输入格式或命令不正确！");
		 }
	}
	
	public static void main(String[] args) {
		//输入shell命令
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






