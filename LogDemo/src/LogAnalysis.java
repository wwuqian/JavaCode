import java.util.*;
import java.io.*;

class LogAnalysis {
	
	//Q1:ͳ����־�е���������
	public static int RequestNum(List<LogInfo> log)
	{
		int count = 0;
		count = log.size();
		return count;
	}
	
	//Q2:top10��HTTP�ӿڼ�������
	
	//ʵ�ֽӿ�Comparator�����ý������� 
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
			//��url��Ϊmap�ļ�ֵ
			String key = info.geturl();
			if(map.containsKey(key)) {
				int value = map.get(key);
				map.put(key, value+1);
			}
			else {
				map.put(key, 1);
			}
		}
		//��map�е�ֵ����valueֵ���н�������
		List<Map.Entry<String, Integer>> list = new ArrayList<>();
		list.addAll(map.entrySet());
		LogAnalysis.ValueComparator com = new ValueComparator();
		Collections.sort(list, com);
		int i = 0;
		System.out.println("������Ƶ����HTTP�ӿڼ�������Ϊ�� ");
		Iterator<Map.Entry<String,Integer>> it = list.iterator();
		for(;it.hasNext() && i < 10;i++)
		{
			Map.Entry<String,Integer> entry = it.next();
			System.out.println("HTTPΪ��" + entry.getKey() + ", ����Ϊ�� " + entry.getValue());
		}	
	}
	
	//Q3.��POST��GET��������
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
		System.out.print("POST��������Ϊ�� ");
		System.out.println(map.get("POST"));
		System.out.print("GET��������Ϊ�� ");
		System.out.println(map.get("GET"));
	}
	
	//Q4:�����ͬ����µ�URL
	public static void CategoryOfUrl(List<LogInfo> log)
	{
		List<String> list1 = new ArrayList<String>(); // /AAA/BBB
		List<String> list2 = new ArrayList<String>(); // /AAA/BBB/CCC
		for(LogInfo info : log)
		{
			String url = info.geturl();
			//ͳ��/���ֵĴ�����2�Σ�3��
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
		
		//�������ָ�ʽ������
		System.out.println("��ʽΪ/AAA/BBB��URL�У� ");
		for(int i = 0;i < list1.size();i++)
		{
			System.out.println(list1.get(i));
		}
		System.out.println();
		System.out.println("��ʽΪ/AAA/BBB/CCC��URL�У� ");
		for(int i = 0;i < list2.size();i++)
		{
			System.out.println(list2.get(i));
		}
	}
	
	public static void main(String[] args)
	{
		//����־����Ϣ�ŵ�һ��List�У�ÿ���ڵ���LogInfo
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
		
		//������̨����Ϣ���뵽�ļ���
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
		System.out.print("��������Ϊ�� ");
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
		System.out.println("---------------���------------------------------------------");
		
	}	
}





































