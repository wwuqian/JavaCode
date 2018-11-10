'''
Created on 2018-7-13

@author: dell
'''
#coding:utf-8
'''
Created on 2018-7-13

@author: LQ
'''

from selenium import webdriver
import selenium
import time
import xlwt
from selenium.webdriver.common.keys import Keys
from lxml import etree
import random
from selenium.webdriver.common.proxy import *


#代理IP
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

searchbtn = fire.find_element_by_xpath(".//*[@id='menu_free']/h2/a")
searchbtn.click()
time.sleep(3)

html = etree.HTML(fire.page_source)
wb = xlwt.Workbook(encoding="utf-8")
sheet = wb.add_sheet("代理IP", cell_overwrite_ok = True)

thead = html.xpath(".//*[@id='list']/table/thead/tr/th")
lists = html.xpath(".//*[@id='list']/table/tbody/tr")
for c,th in enumerate(thead):
    sheet.write(0,c,th.text)
for r,list in enumerate(lists,1):
    for c,list in enumerate(list):
        sheet.write(r,c,list.text)


#toplists = fire.find_element_by_xpath(".//*[@id='list']/table/thead/tr").find_elements_by_tag_name('th')
#表头
#for c,tlist in enumerate(toplists):
 #   sheet.write(0,c,tlist.text)

#lists = fire.find_element_by_xpath(".//*[@id='list']/table/tbody").find_elements_by_tag_name('tr')
#for r,tr in enumerate(lists,1):
 #   td = tr.find_elements_by_tag_name('td')
  #  for c,t in enumerate(td):
   #     sheet.write(r,c,t.text)

wb.save("IP.xlsx")
print("完成")
    

fire.close()
