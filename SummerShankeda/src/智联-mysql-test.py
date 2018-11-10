'''
Created on 2018-7-15

@author: dell
'''
#爬取智联招聘网站:将数据存到数据库中--自己写版本
import selenium 
from selenium import webdriver
import time 
import pymysql

conn = pymysql.connect(host='localhost',
                user='root',
                password='123',
                port=3306,
                database='ipdb',
                charset='utf8')
#创建sql语句的执行对象
cursor = conn.cursor()
#打开浏览器
fire = webdriver.Firefox()
time.sleep(2)
#创建sql语句的执行对象
fire.get("https://www.zhaopin.com/")
time.sleep(2)

#定义数据库删除语句，清空之前的表数据
delete = "truncate table recruit"
#执行delete语句
cursor.execute(delete)
#提交数据库的表数据删除结果
conn.commit()
#定义方法，用来执行每一页的行信息获取

#职位选择框
choose = fire.find_element_by_xpath(".//div[@class='zp-i-select-label zp-iconfont']")
choose.click()
time.sleep(1)

choose1 = fire.find_element_by_xpath(".//input[@class='zp-search-input']")
choose1.click()

#搜素框
searchbox = fire.find_element_by_class_name('zp-search-input')
#将搜索框的内容清空
searchbox.clear()
searchbox.send_keys('C++开发工程师')
time.sleep(3)

#搜素按钮
searchbtn = fire.find_element_by_css_selector('.zp-search-btn.zp-blue-button')
searchbtn.click()
time.sleep(2)


#页面的跳转:句柄
handle = fire.current_window_handle
#获取当前浏览器的所有句柄
handles = fire.window_handles
for h in handles:
    if h != handle:   #19 != 13 跳转到新的页面，手动跳转
        fire.switch_to_window(h)
        time.sleep(3)

#地点：全国
current = fire.find_element_by_class_name("currentCity")
current.click()
time.sleep(4)
city = fire.find_element_by_xpath(".//*[@id='queryCityBox']/div/ul/li[1]/a")
city.click()
time.sleep(1)
print("搜素完成")
#获取每一页的内容：定义为一个方法
def getcontent():
    #获取每行的数据
    rows = fire.find_elements_by_xpath("//div[@class='listItemBox clearfix']")
    for index,row in enumerate(rows):
        col1 = row.find_element_by_xpath(".//div[@class='jobName']/a/span")
        col2 = row.find_element_by_xpath(".//div[@class='commpanyName']/a")

        info_items = row.find_elements_by_class_name('info_item')
        info_item_test = ""
        for item in info_items:
            info_item_test += (item.text+", ")
        col3 = info_item_test
        
        job_welfare = row.find_element_by_class_name('job_welfare')
        welfare_item = job_welfare.find_elements_by_class_name('welfare_item')
        welfare_item_test = ""
        for item in welfare_item:
            welfare_item_test += (item.text +" , ")
        col4 = welfare_item_test
              
        col7 =row.find_element_by_class_name("job_saray")
        ul_li_s = row.find_elements_by_tag_name("li")
        col5 = ul_li_s[2]
        col6 = ul_li_s[0]
        col8 = ul_li_s[1]
        insert = "INSERT INTO recruit(岗位,公司,公司规模,福利,学历,工作地点,薪水,经验)\
        VALUES(%s,%s,%s,%s,%s,%s,%s,%s)"
        row = cursor.execute(insert,(col1.text,col2.text,col3,col4,\
                                     col5.text,col6.text,col7.text,col8.text))
        if row > 0:
            conn.commit()
        else:
            print("fail")

print("---------start------------\n")   

#获取1-5页的内容：
for page in range(1,6):
    print("正在获取第"+str(page)+"页")
    a = fire.find_element_by_xpath(".//*[@id='pagination_content']/div/button[2]")
    a.click()
    time.sleep(2)
    getcontent()
    
#记录获取到了多少行的数据
print("---------end---------------\n")      
cursor.close()
conn.close()  
fire.close()
