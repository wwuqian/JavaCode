import java.io.*;
import java.lang.String;

public class StatisticsRow {
	//��ʱ�Ȳ�ͳ�ƿ��к�ע���У���ΪԴ��������java����HTML���
	//static int emptyLines = 0; //����
	//static int commentLines = 0; //ע����
	static int codeLines = 0; //������
	static int codeSum = 0; //������
	
	public static void main(String[] args) {
		try {
			File in = new File("StringUtils.java");  //Ҫͳ�Ƶ��ļ�
			//��ͳ�ƽ�������validLineCount.txt�ļ���
			FileOutputStream output = new FileOutputStream("validLineCount.txt"); 
			BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(output,"utf-8"));
			buffer.write("StringUtils.java���ܴ�������Ϊ�� ");
			
			//ͳ������
			StatisticsOfRow(in);
			
			buffer.newLine();
			buffer.write(codeSum + "\t"); //ʹ��BufferedWrite����д���͵�ʱ����Ҫ��"\t",���������
			buffer.newLine();
			buffer.write("StringUtils.java����Ч��������Ϊ�� ");
			buffer.newLine();
			buffer.write(codeLines + "\t");
			buffer.flush();
			buffer.close(); //�ر������
			System.out.println("StringUtils.java���ܴ�������Ϊ�� ");
			System.out.println(codeSum);
			System.out.println("StringUtils.java����Ч��������Ϊ�� ");
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
			//��utf-8�ĸ�ʽ����
			String line = null;
			boolean comment = false;
			while((line = br.readLine()) != null) {
				line = line.replaceAll("\\s", ""); 
				//ʹ�ÿ��ַ��滻���еĿհ׷����ո񣬻س������У��Ʊ����
				
				//���˵�ע�ͺͿ���
				if("".equals(line)
						|| line.startsWith("//")
						|| line.startsWith("/*")
						|| line.startsWith("/**")
						|| line.startsWith("*")) {
					codeSum++;
				}else if(line.startsWith("<!--") && !line.endsWith("-->")) {
					//��<!--��ͷ��Ϊhtmlע�͵Ŀ�ͷ
					comment = true;  //���������ע��
					codeSum++;
				}else if(comment == true && !line.endsWith("-->")) {
					//��Ϊ����ע���е�һ��
					codeSum++;
				}else if(comment == true && line.endsWith("-->")) {
					//����ע�͵Ľ�����
					comment = false;
					codeSum++;
				}else if(line.startsWith("<!--") && line.endsWith("-->")) {
					//����ע����
					codeSum++;
				}else {
					//����������
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
					br.close(); //�رն�����
					br = null;
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}




















