import java.io.*;
import java.lang.String;

public class StatisticsRow {
	//暂时先不统计空行和注释行，因为源代码中有java风格和HTML风格
	//static int emptyLines = 0; //空行
	//static int commentLines = 0; //注释行
	static int codeLines = 0; //代码行
	static int codeSum = 0; //总行数
	
	public static void main(String[] args) {
		try {
			File in = new File("StringUtils.java");  //要统计的文件
			//将统计结果输出到validLineCount.txt文件中
			FileOutputStream output = new FileOutputStream("validLineCount.txt"); 
			BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(output,"utf-8"));
			buffer.write("StringUtils.java的总代码行数为： ");
			
			//统计行数
			StatisticsOfRow(in);
			
			buffer.newLine();
			buffer.write(codeSum + "\t"); //使用BufferedWrite进行写整型的时候需要加"\t",否则会乱码
			buffer.newLine();
			buffer.write("StringUtils.java的有效代码行数为： ");
			buffer.newLine();
			buffer.write(codeLines + "\t");
			buffer.flush();
			buffer.close(); //关闭输出流
			System.out.println("StringUtils.java的总代码行数为： ");
			System.out.println(codeSum);
			System.out.println("StringUtils.java的有效代码行数为： ");
			System.out.println(codeLines);
		}
		catch(Exception e) {
			e.printStackTrace();
		}      
	}
	
	public static void StatisticsOfRow(File in) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(in));
			//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(in),"utf-8"));
			//以utf-8的格式读入
			String line = null;
			boolean comment = false;
			while((line = br.readLine()) != null) {
				line = line.replaceAll("\\s", ""); 
				//使用空字符替换所有的空白符（空格，回车，换行，制表符）
				
				//过滤掉注释和空行
				if("".equals(line)
						|| line.startsWith("//")
						|| line.startsWith("/*")
						|| line.startsWith("/**")
						|| line.startsWith("*")) {
					codeSum++;
				}else if(line.startsWith("<!--") && !line.endsWith("-->")) {
					//以<!--开头视为html注释的开头
					comment = true;  //代码该行有注释
					codeSum++;
				}else if(comment == true && !line.endsWith("-->")) {
					//视为多行注释中的一行
					codeSum++;
				}else if(comment == true && line.endsWith("-->")) {
					//多行注释的结束行
					comment = false;
					codeSum++;
				}else if(line.startsWith("<!--") && line.endsWith("-->")) {
					//单行注释行
					codeSum++;
				}else {
					//正常代码行
					codeLines++;
					codeSum++;
				}
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					br.close(); //关闭读入流
					br = null;
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}




















