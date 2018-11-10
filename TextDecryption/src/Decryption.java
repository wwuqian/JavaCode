import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Decryption {
	
	public static void TextDecryption(Map<String,String> map,List<NatureType> listN) {
		
		//1.对map按键值进行排序---保存在list1中---用于文本排序
		List<Map.Entry<String,String>> list1 = new 
				ArrayList<Map.Entry<String,String>>(map.entrySet());
		Collections.sort(list1,new Comparator<Map.Entry<String,String>>(){
			//升序排序
			public int compare(Map.Entry<String,String> m,Map.Entry<String,String> n) {
				return m.getKey().compareTo(n.getKey());
			}
		});
		
		//2.对map按键值进行逆序排序---保存在list2中----用于文本倒序
		List<Map.Entry<String,String>> list2 = new 
				ArrayList<Map.Entry<String,String>>(map.entrySet());
		Collections.sort(list2,new Comparator<Map.Entry<String,String>>(){
			//降序排序
			public int compare(Map.Entry<String,String> m,Map.Entry<String,String> n) {
				return n.getKey().compareTo(m.getKey());
			}
		});
		
		//3.读取sdxl_template.txt中的内容,进行解密，还原到sdxl.txt中
		try {
			//不能用FileReader，读取中文会出现乱码，使用InputStreamReader读取设置字符集为utf-8,解决问题
			BufferedReader novel = new BufferedReader(new InputStreamReader(new FileInputStream("sdxl_template.txt"),"utf-8"));
			
			//将还原的内容写到sdxl.txt中
			FileOutputStream output = new FileOutputStream("sdxl.txt"); 
			BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(output,"utf-8"));
			
			//进行还原的过程
			String line;
			while((line = novel.readLine()) != null)
			{
				String str; //用于接收匹配到的字符串
				String substr; //数字索引
				int start = 0; // ( 的下标
				int end = 0;   // ) 的下标
				
				//自然排序：String的matches，是全量匹配
				if(line.matches(".*\\$natureOrder\\([0-9]{1,}\\).*")) {
					List<String> list = new ArrayList<String>();
					//使用Matcher类提取正则表达式获取到的结果，并存放到list中
					Pattern pattern = Pattern.compile("\\$natureOrder\\([0-9]{1,}\\)");
					Matcher matcher = pattern.matcher(line);
					while(matcher.find()) {
						list.add(matcher.group());
					}
					for(int i = 0;i < list.size();i++) {
						str = list.get(i);
						start = str.lastIndexOf("(");
						end = str.lastIndexOf(")");
						substr = str.substring(start+1,end); //得到的是编号
						
						NatureType nt = new NatureType();
						int index = Integer.parseInt(substr);
						nt = listN.get(index);
						line = line.replace(str,nt.getText());
						}	
					}
				/*else {
						System.out.println("在自然排序这里出错！");
					}*/
				
				//索引排序
				if(line.matches(".*\\$indexOrder\\([0-9]+\\).*")) {
					List<String> list = new ArrayList<String>();
					Pattern pattern = Pattern.compile("\\$indexOrder\\([0-9]+\\)");
					Matcher matcher = pattern.matcher(line);
					while(matcher.find()) {
						list.add(matcher.group()); 
						//group()=group(0):匹配的完整式子，group(1):匹配到的第一个子表达式
					}
					for(int i = 0;i < list.size();i++) {
						str = list.get(i);
						//System.out.println(str);
						start = str.lastIndexOf("(");
						end = str.lastIndexOf(")");
						substr = str.substring(start+1,end);
						
						//在map中得到key=substr的value，并将value替换到正则表达式处(str)
						if(map.containsKey(substr)) {
							String value = map.get(substr);
							line = line.replace(str,value);
						}
					}
				}
				/*else {
					System.out.println("在索引排序这里出错！"); //为了调试
				}*/
				
				//文本排序
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
						//Map.Entry<String,String> pair = list1.get(0); //测试文本排序没问题
						//System.out.println(pair.getValue());
						
						//将匹配到的正则表达式处替换成list1中的value值
						line = line.replace(str, pair.getValue()); 
						//System.out.println(line);
						
					}	
				} 
				/*else {
					System.out.println("在文本排序这里出错！");
				}*/
				
				//文本倒序---有问题
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
						
						//将匹配到的正则表达式处替换成list2中的value值
						line = line.replace(str,pair.getValue());
					}
				}
				/*else {
					System.out.println("在文本倒序这里出错！");
				}*/
				
				//将替换完成的line写到sdxl.txt中
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
		//将sdxl_prop.txt中的信息存到Map中
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
			
			//测试map:存在一个问题：
			//map中的顺序不是自然排序的顺序，应该将自然排序放到链表中，从而和文本中的顺序一致
			/*Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				String value = map.get(key);
				System.out.println(key + "------" + value);
			}
			System.out.println(map.size());
			*/
			
			TextDecryption(map,list);
			System.out.println("转换完成！");
			
			in.close();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {}
	}
	
}
