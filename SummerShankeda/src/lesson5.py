'''
Created on 2018-7-14

@author: dell
'''
#爬取块代理网站：老师讲解版
import selenium 
from selenium import webdriver
import time 
import xlwt 


fire = webdriver.Firefox()
fire.get("https://www.kuaidaili.com/free/")

wb = xlwt.Workbook(encoding="utf-8")
print("---------start------------\n")

#单元格可以被成功覆盖
sheet = wb.add_sheet("代理IP",cell_overwrite_ok=True)
#添加样式:返回样式的变量
style = xlwt.easyxf("font:name 楷体,bold on,color red")
sheet.col(0).width = 256*20

#获取每一页的内容：定义为一个方法
def getcontent(page,count):
    rows = fire.find_elements_by_tag_name("tr")
    for index,row in enumerate(rows):
        if index == 0 and page == 1:
            cols = row.find_elements_by_tag_name("th")
            for i,col in enumerate(cols):
                sheet.write(count,i,col.text,style)
        else:
            cols = row.find_elements_by_tag_name("td")
            for i,col in enumerate(cols):
                sheet.write(count,i,col.text)      
        count += 1

count = 0       
for page in range(1,6):
    print("正在获取第"+str(page)+"页")
    a = fire.find_element_by_link_text(str(page))
    a.click()
    time.sleep(2)
    getcontent(page,count)
    count = 1+(15*page)
    
print(count)
    
    
wb.save("代理IP.xls")   

print("---------end---------------\n")    
        
        
        
        
        
        
        
        
        