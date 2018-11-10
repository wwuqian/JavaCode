'''
Created on 2018-7-13

@author: dell
'''
import selenium 
from selenium import webdriver
from selenium.webdriver.common.keys import Keys  
import time 
import xlwt 
from lxml import etree
import random
from selenium.webdriver.common.proxy import *

list = [["180.118.128.188","9000"],["121.232.148.178","9000"],["125.94.0.250","8080"],["122.243.13.41","9000"],["180.118.93.148","9000"]]
use_proxy=random.choice(list)
user_proxy = use_proxy[0]+":"+use_proxy[1]
_proxy = Proxy({
'proxyType': ProxyType.MANUAL,
'httpProxy': user_proxy,
'ftpProxy': user_proxy,
'sslProxy': user_proxy,
'noProxy': None, # set this value as desired
"proxyType":"MANUAL",
"class":"org.openqa.selenium.Proxy",
"autodetect":False
})

fire = webdriver.Firefox(proxy=_proxy)
fire.get("https://www.kuaidaili.com/")

free = fire.find_element_by_id('menu_free')
free.click()
time.sleep(3)

html = etree.HTML(fire.page_source)
title = html.xpath(".//*[@id='list']/table/thead/tr/th")
lists = html.xpath(".//*[@id='list']/table/tbody/tr")

print("-------start----------\n")

#新建工作薄
wb = xlwt.Workbook(encoding="utf-8")
#新建工作表
sheet = wb.add_sheet("块代理",cell_overwrite_ok = True)

#写内容：
for col,ti in enumerate(title):
    sheet.write(0,col,ti.text)
for row,list in enumerate(lists,1):
   for col,list in enumerate(list):
       sheet.write(row,col,list.text)

wb.save("IP.xlsx")
print("-----------end------------\n")

fire.close()




