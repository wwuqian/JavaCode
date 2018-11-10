'''
Created on 2018-7-16
数据---rows
存储---insert
智联招聘信息（id（主键-信息唯一）,职位名，公司，薪资，工作经验，学历，地址，）
@author: dell
'''

#爬取智联招聘信息---老师版本
import selenium
from selenium import webdriver
import time
import pymysql
#建立数据库连接
conn = pymysql.connect(host='localhost',
               user='root',
               password='123',
               port=3306,
               database='ipdb',
               charset='utf8')
#创建sql语句的执行对象
cursor = conn.cursor()
#输入网址，打开检索的网页
fire = webdriver.Firefox()
time.sleep(1)
fire.get("https://zhaopin.com/")
time.sleep(2)
#定位搜索框，并输入java
search = fire.find_element_by_class_name("zp-search-input")
search.clear()
search.send_keys("java开发工程师")
#定位搜索按钮，并点击
searchbtn = fire.find_element_by_css_selector(".zp-search-btn.zp-blue-button")
searchbtn.click()
time.sleep(2)
#进行网页的跳转
handle = fire.current_window_handle
handles = fire.window_handles
for h in handles:
    if h != handle: 
        fire.switch_to_window(h)
        time.sleep(2)
        
#定位当前职位的地址，并点击
city = fire.find_element_by_class_name("currentCity")
city.click()
time.sleep(2)

#选择职位的地址范围为《全国》，并点击 xpath
citychina = fire.find_element_by_class_name("zp-query")
citychina.click()
time.sleep(3)

#定义数据库删除语句，清空之前的表数据
delete = "delete from job "
#执行delete语句
cursor.execute(delete)
#提交数据库的表数据删除结果
conn.commit()

#定义方法，用来执行每一页的行信息获取
def getcontent():
    contents=fire.find_elements_by_xpath(".//*[@id='listContent']/div")
    for row,content in enumerate(contents):
        #职位信息---6个基本信息
        jobname = content.find_element_by_class_name("jobName").text
        commpany = content.find_element_by_class_name("commpanyName").text     
        sal = content.find_element_by_class_name("job_saray").text     
        lists = content.find_element_by_class_name("job_demand").find_elements_by_tag_name("li")
        address=lists[0].text
        #检测地址是否存在“-”，存在仅获取前面的地址
        if address.find("-")!=-1:
            #"成都-高新区" --->获取"成都"即可
            address = address[0:address.find("-")]
        year=lists[1].text
        edu=lists[2].text  
              
       # print(jobname,"  ",commpany,"   ",sal)  
        insert = "INSERT INTO job(jobname,jobcommpany,jobsal,jobaddress,\
        jobyear,jobedu) VALUES(%s,%s,%s,%s,%s,%s)"
        row = cursor.execute(insert,(jobname,commpany,sal,address,year,edu))
        if row>0 :
            conn.commit()
        else:
            print("fail")   
        
print("------start---------------")        
for page in range(0,6):#start,stop-1,1
    print("正在获取第"+str(page+1)+"页")
    if page != 0:
        a = fire.find_element_by_xpath(".//*[@id='pagination_content']/div/button[2]")
        a.click()
    time.sleep(2)
    getcontent()
 
print("----------ending--------")   
cursor.close()
conn.close()
