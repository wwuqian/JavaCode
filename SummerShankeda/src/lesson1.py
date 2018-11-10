#coding: utf-8
'''
Created on 2018-7-4

@author: dell
'''
from selenium import webdriver
import time

#browser=webdriver.Firefox()
#browser=webdriver.Chrome("/usr/local/bin/chromedriver")
browser = webdriver.Chrome("C:/Program Files (x86)/Google/Chrome/Application/chromedriver")
url="http://www.baidu.com"
browser.get(url)

#searchbox=browser.find_element_by_name("wd")
searchbox=browser.find_element_by_xpath(".//*[@id='kw']")
searchbox.send_keys("selenium")

#searchbtn=browser.find_element_by_id("su")
searchbtn=browser.find_element_by_xpath(".//*[@id='su']")
searchbtn.click()
print("点击完成")
time.sleep(5)

browser.close()

##链表
lista=[1,2,3,'t','testing']
for i in lista:
    print(i)







