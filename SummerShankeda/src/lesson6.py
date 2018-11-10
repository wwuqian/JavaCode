'''
Created on 2018-7-15

@author: dell
'''
#爬取块代理网站:将数据存到数据库中
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
fire.get("https://www.kuaidaili.com/free/")
time.sleep(2)

#定义数据库删除语句，清空之前的表数据
delete = "delete from iptab"
#执行delete语句
cursor.execute(delete)
#提交数据库的表数据删除结果
conn.commit()
#定义方法，用来执行每一页的行信息获取

#获取每一页的内容：定义为一个方法
def getcontent(page,count):
    #获取每行的数据
    rows = fire.find_elements_by_tag_name("tr")
    for index,row in enumerate(rows):
        if index == 0 and page == 1:
            cols = row.find_elements_by_tag_name("th") 
            #自动获取内容
            insert = "INSERT INTO iptab(ip,port,degree,type,position,speed,time)\
            VALUES(%s,%s,%s,%s,%s,%s,%s)"
            row = cursor.execute(insert,(cols[0].text,cols[1].text,cols[2].text,cols[3].text,\
                                         cols[4].text,cols[5].text,cols[6].text))
            if row > 0:
                conn.commit()
            else:
                print("fail")
        else:
            if index == 0:
                pass
            else:
                cols = row.find_elements_by_tag_name("td")
                insert = "INSERT INTO iptab(ip,port,degree,type,position,speed,time)\
                VALUES(%s,%s,%s,%s,%s,%s,%s)"
                row = cursor.execute(insert,(cols[0].text,cols[1].text,cols[2].text,cols[3].text,\
                                             cols[4].text,cols[5].text,cols[6].text))
                if row > 0:
                    conn.commit()
                else:
                    print("fail")
        count += 1

print("---------start------------\n")   
#获取1-5页的内容：
count = 0  
for page in range(1,6):
    print("正在获取第"+str(page)+"页")
    a = fire.find_element_by_link_text(str(page))
    a.click()
    time.sleep(2)
    getcontent(page,count)
    count = 1+(15*page)
print(count)
print("---------end---------------\n")      
cursor.close()
conn.close()  

  