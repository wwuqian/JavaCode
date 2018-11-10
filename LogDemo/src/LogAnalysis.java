import java.util.*;
import java.io.*;

class LogAnalysis {
	
	//Q1:统计日志中的请求总量
	public static int RequestNum(List<LogInfo> log)
	{
		int count = 0;
		count = log.size();
		return count;
	}
	
	//Q2:top10的HTTP接口及其数量
	
	//实现接口Comparator，采用降序排列 
	private static class ValueComparator implements Comparator<Map.Entry<String,Integer>>
	{
		public int compare(Map.Entry<String,Integer> m,Map.Entry<String,Integer> n)
		{
			return n.getValue()-m.getValue();
		}
	}
	
	public static void Top10OfHTTP(List<LogInfo> log)
	{
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(LogInfo info : log)
		{
			//将url作为map的键值
			String key = info.geturl();
			if(map.containsKey(key)) {
				int value = map.get(key);
				map.put(key, value+1);
			}
			else {
				map.put(key, 1);
			}
		}
		//将map中的值按照value值进行降序排序
		List<Map.Entry<String, Integer>> list = new ArrayList<>();
		list.addAll(map.entrySet());
		LogAnalysis.ValueComparator com = new ValueComparator();
		Collections.sort(list, com);
		int i = 0;
		System.out.println("出现最频繁的HTTP接口及其数量为： ");
		Iterator<Map.Entry<String,Integer>> it = list.iterator();
		for(;it.hasNext() && i < 10;i++)
		{
			Map.Entry<String,Integer> entry = it.next();
			System.out.println("HTTP为：" + entry.getKey() + ", 次数为： " + entry.getValue());
		}	
	}
	
	//Q3.求POST和GET的请求量
	public static void PostAndGetTimes(List<LogInfo> log)
	{
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(LogInfo info : log)
		{
			String key = info.getmethod();
			if(map.containsKey(key)) {
				int value = map.get(key);
				map.put(key,value+1);
			}
			else {
				map.put(key, 1);
			}
		}
		System.out.print("POST的请求量为： ");
		System.out.println(map.get("POST"));
		System.out.print("GET的请求量为： ");
		System.out.println(map.get("GET"));
	}
	
	//Q4:输出不同类别下的URL
	public static void CategoryOfUrl(List<LogInfo> log)
	{
		List<String> list1 = new ArrayList<String>(); // /AAA/BBB
		List<String> list2 = new ArrayList<String>(); // /AAA/BBB/CCC
		for(LogInfo info : log)
		{
			String url = info.geturl();
			//统计/出现的次数：2次，3次
			String s = "/";
			int cnt = 0;
			String tmp = url;
			for(int i = 0;i < tmp.length();i++)
			{
				if(tmp.indexOf(s) == i) {
					tmp = tmp.substring(i+1,tmp.length());
					cnt++;
				}
			}
			if(cnt == 2)
				list1.add(url);
			else if(cnt == 3)
				list2.add(url);
		}
		
		//遍历两种格式的链表
		System.out.println("格式为/AAA/BBB的URL有： ");
		for(int i = 0;i < list1.size();i++)
		{
			System.out.println(list1.get(i));
		}
		System.out.println();
		System.out.println("格式为/AAA/BBB/CCC的URL有： ");
		for(int i = 0;i < list2.size();i++)
		{
			System.out.println(list2.get(i));
		}
	}
	
	public static void main(String[] args)
	{
		//将日志的信息放到一个List中，每个节点存放LogInfo
		List<LogInfo> mylist = new ArrayList<LogInfo>(10000);
		String filename = "E:/access.log";
		String line;
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			line = in.readLine();
			while(line != null)
			{
				Vector<String> data = new Vector<String>();
				for(String retval : line.split(" "))
				{
					data.add(retval);
				}
				LogInfo log = new LogInfo();
				log.setmethod(data.get(0));
				log.seturl(data.get(1));
				mylist.add(log);
				
				line = in.readLine();
			}
			in.close();
		}
		catch(IOException iox) {
			System.out.println("Problem reading " + filename);
		}
		
		//将控制台的信息输入到文件里
		try{
			File f = new File("out.txt");
			if(!f.exists())
			{
				f.createNewFile();
				FileOutputStream fileOutputStream = new FileOutputStream(f);
				PrintStream printStream = new PrintStream(fileOutputStream);
				System.setOut(printStream);
			}
		}
		catch(IOException iox){
			System.out.println("Problem output ");
		}
		
		//Q1.
		int count = RequestNum(mylist);
		System.out.print("访问总量为： ");
		System.out.println(count);
		System.out.println("----------------------------------------------");
		
		//Q2.
		Top10OfHTTP(mylist);
		System.out.println("----------------------------------------------");
		
		//Q3.
		PostAndGetTimes(mylist);
		System.out.println("----------------------------------------------");
		
		//Q4.
		CategoryOfUrl(mylist);
		System.out.println("---------------完成------------------------------------------");
		
	}	
}





































