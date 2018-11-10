import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Decryption {
	
	public static void TextDecryption(Map<String,String> map,List<NatureType> listN) {
		
		//1.��map����ֵ��������---������list1��---�����ı�����
		List<Map.Entry<String,String>> list1 = new 
				ArrayList<Map.Entry<String,String>>(map.entrySet());
		Collections.sort(list1,new Comparator<Map.Entry<String,String>>(){
			//��������
			public int compare(Map.Entry<String,String> m,Map.Entry<String,String> n) {
				return m.getKey().compareTo(n.getKey());
			}
		});
		
		//2.��map����ֵ������������---������list2��----�����ı�����
		List<Map.Entry<String,String>> list2 = new 
				ArrayList<Map.Entry<String,String>>(map.entrySet());
		Collections.sort(list2,new Comparator<Map.Entry<String,String>>(){
			//��������
			public int compare(Map.Entry<String,String> m,Map.Entry<String,String> n) {
				return n.getKey().compareTo(m.getKey());
			}
		});
		
		//3.��ȡsdxl_template.txt�е�����,���н��ܣ���ԭ��sdxl.txt��
		try {
			//������FileReader����ȡ���Ļ�������룬ʹ��InputStreamReader��ȡ�����ַ���Ϊutf-8,�������
			BufferedReader novel = new BufferedReader(new InputStreamReader(new FileInputStream("sdxl_template.txt"),"utf-8"));
			
			//����ԭ������д��sdxl.txt��
			FileOutputStream output = new FileOutputStream("sdxl.txt"); 
			BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(output,"utf-8"));
			
			//���л�ԭ�Ĺ���
			String line;
			while((line = novel.readLine()) != null)
			{
				String str; //���ڽ���ƥ�䵽���ַ���
				String substr; //��������
				int start = 0; // ( ���±�
				int end = 0;   // ) ���±�
				
				//��Ȼ����String��matches����ȫ��ƥ��
				if(line.matches(".*\\$natureOrder\\([0-9]{1,}\\).*")) {
					List<String> list = new ArrayList<String>();
					//ʹ��Matcher����ȡ������ʽ��ȡ���Ľ��������ŵ�list��
					Pattern pattern = Pattern.compile("\\$natureOrder\\([0-9]{1,}\\)");
					Matcher matcher = pattern.matcher(line);
					while(matcher.find()) {
						list.add(matcher.group());
					}
					for(int i = 0;i < list.size();i++) {
						str = list.get(i);
						start = str.lastIndexOf("(");
						end = str.lastIndexOf(")");
						substr = str.substring(start+1,end); //�õ����Ǳ��
						
						NatureType nt = new NatureType();
						int index = Integer.parseInt(substr);
						nt = listN.get(index);
						line = line.replace(str,nt.getText());
						}	
					}
				/*else {
						System.out.println("����Ȼ�����������");
					}*/
				
				//��������
				if(line.matches(".*\\$indexOrder\\([0-9]+\\).*")) {
					List<String> list = new ArrayList<String>();
					Pattern pattern = Pattern.compile("\\$indexOrder\\([0-9]+\\)");
					Matcher matcher = pattern.matcher(line);
					while(matcher.find()) {
						list.add(matcher.group()); 
						//group()=group(0):ƥ�������ʽ�ӣ�group(1):ƥ�䵽�ĵ�һ���ӱ��ʽ
					}
					for(int i = 0;i < list.size();i++) {
						str = list.get(i);
						//System.out.println(str);
						start = str.lastIndexOf("(");
						end = str.lastIndexOf(")");
						substr = str.substring(start+1,end);
						
						//��map�еõ�key=substr��value������value�滻��������ʽ��(str)
						if(map.containsKey(substr)) {
							String value = map.get(substr);
							line = line.replace(str,value);
						}
					}
				}
				/*else {
					System.out.println("�����������������"); //Ϊ�˵���
				}*/
				
				//�ı�����
				if(line.matches(".*\\$charOrder\\([0-9]+\\).*")) {
					List<String> list = new ArrayList<String>();
					Pattern pattern = Pattern.compile("\\$charOrder\\([0-9]+\\)");
					Matcher matcher = pattern.matcher(line);
					while(matcher.find()) {
						list.add(matcher.group());
					}
					for(int i = 0;i < list.size();i++) {
						str = list.get(i);
						//System.out.println(str);
						start = str.lastIndexOf("(");
						end = str.lastIndexOf(")");
						substr = str.substring(start+1,end);
						
						int index = Integer.parseInt(substr);
						Map.Entry<String,String> pair = list1.get(index);
						//Map.Entry<String,String> pair = list1.get(0); //�����ı�����û����
						//System.out.println(pair.getValue());
						
						//��ƥ�䵽��������ʽ���滻��list1�е�valueֵ
						line = line.replace(str, pair.getValue()); 
						//System.out.println(line);
						
					}	
				} 
				/*else {
					System.out.println("���ı������������");
				}*/
				
				//�ı�����---������
				if(line.matches(".*\\$charOrderDESC\\([0-9]+\\).*")) {
					List<String> list = new ArrayList<String>();
					Pattern pattern = Pattern.compile("\\$charOrderDESC\\([0-9]+\\)");
					Matcher matcher = pattern.matcher(line);
					while(matcher.find()) {
						list.add(matcher.group());
					}
					for(int i = 0;i < list.size();i++) {
						str = list.get(i);
						start = str.lastIndexOf("(");
						end = str.lastIndexOf(")");
						substr = str.substring(start+1,end);
						
						int index = Integer.parseInt(substr);
						Map.Entry<String,String> pair = list2.get(index);
						
						//��ƥ�䵽��������ʽ���滻��list2�е�valueֵ
						line = line.replace(str,pair.getValue());
					}
				}
				/*else {
					System.out.println("���ı������������");
				}*/
				
				//���滻��ɵ�lineд��sdxl.txt��
				buffer.write(line);
				buffer.newLine();
				
			}
			novel.close();
			buffer.close();
		} catch(NullPointerException e) {
			e.printStackTrace();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	public static void main(String[] args) {
		//��sdxl_prop.txt�е���Ϣ�浽Map��
		String file = "sdxl_prop.txt";
		Map<String,String> map = new HashMap<String,String>();
		List<NatureType> list = new ArrayList<NatureType>();
		String line;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			line = in.readLine();
			while(line != null) {
				Vector<String> data = new Vector<String>();
				for(String retval : line.split("\\s"))
					data.add(retval);
				map.put(data.get(0),data.get(1));
				NatureType nt = new NatureType();
				nt.setNumber(data.get(0));
				nt.setText(data.get(1));
				list.add(nt);
				
				line = in.readLine();
			}
			
			//����map:����һ�����⣺
			//map�е�˳������Ȼ�����˳��Ӧ�ý���Ȼ����ŵ������У��Ӷ����ı��е�˳��һ��
			/*Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				String value = map.get(key);
				System.out.println(key + "------" + value);
			}
			System.out.println(map.size());
			*/
			
			TextDecryption(map,list);
			System.out.println("ת����ɣ�");
			
			in.close();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {}
	}
	
}
