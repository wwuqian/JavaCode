import java.util.*;

public class LogInfo {
	private String method;
	private String Urlpath;
	
	public String getmethod() {
		return method;
	}
	
	public String geturl() {
		return Urlpath;
	}
	
	public void setmethod(String m)
	{
		method = m;
	}
	
	public void seturl(String u)
	{
		Urlpath = u;
	}
	
	LogInfo(){}
}
