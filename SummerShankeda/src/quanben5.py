'''
Created on 2018-7-13

@author: dell
'''
import selenium 
from selenium import webdriver 
import time 
from selenium.webdriver.common.keys import Keys 
import xlwt 

fire = webdriver.Firefox()
url = "http://www.quanben5.com"
fire.get(url)

#search = fire.find_element_by_class_name('search_input')
search = fire.find_element_by_css_selector('.search_input.c3')
#引入回车键，类名+RETURN
search.send_keys("为了你"+Keys.RETURN) 
time.sleep(3)

#点击书名
bookname = fire.find_element_by_xpath("html/body/div[3]/div/div[2]/div[2]/h3/a/span")
bookname.click()
time.sleep(2)

#点击阅读
readbtn = fire.find_element_by_link_text("点击阅读")
readbtn.click()
time.sleep(3)

#定位目录
print("----------start---------------\n")
lists = fire.find_elements_by_class_name("c3")


#1-新建工作薄
wb = xlwt.Workbook(encoding="utf-8")
#2-新建sheet文件：add_sheet的参数说明：sheet的文件名，文件的内容是否覆盖（默认不覆盖）
sheet = wb.add_sheet("为了你章节",cell_overwrite_ok=True)
#调整文件的宽：256为一个字符的宽度
sheet.col(0).width = 256*20 
#3-写内容
#sheet.write(row,col=0,content)
#拿值和索引的遍历
for row,li in enumerate(lists):
    sheet.write(row,0,li.text)
wb.save("为了你.xls")

#wb.close()


#直接拿值的遍历
# for li in lists:
#     print(li.text)
    
print("----------end-----------------\n")
fire.close()










