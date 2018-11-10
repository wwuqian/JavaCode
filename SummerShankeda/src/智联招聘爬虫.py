#coding: utf-8
'''
Created on 2018-7-5

@author: dell
'''
from selenium import webdriver
from selenium.webdriver.common.proxy import *
import time
import random 

def scroll(n,i):  
    return "window.scrollTo(0,(document.body.scrollHeight/{0})*{1});".\
        format(n,i)

listIP = [["180.118.128.188","9000"],["121.232.148.178","9000"],["125.94.0.250","9000"],["122.243.13.41","9000"],["180.118.93.148","9000"]]
use_proxy = random.choice(listIP)
user_proxy = use_proxy[0]+":"+use_proxy[1]

#proxy初始化
_proxy = Proxy({
                'proxyType':ProxyType.MANUAL,
                'httpProxy':use_proxy,
                'ftpProxy':use_proxy,
                'sslProxy':use_proxy,
                'noProxy':None, # set this value as desired
                "proxyType": "MANUAL",
                "class":"org.selenium.Proxy",
                "autodetect": False
})

driver = webdriver.Firefox(proxy = _proxy)
url = "https://www.zhaopin.com/"
driver.get(url)

#登录
# user = driver.find_element_by_xpath(".//*[@id='root']/div[2]/div[2]/div[3]/div[1]/div/div/div[1]/form/div[1]/input")
# user.send_keys('wuqian.11@foxmail.com')
# 
# password = driver.find_element_by_xpath(".//*[@id='root']/div[2]/div[2]/div[3]/div[1]/div/div/div[1]/form/div[2]/input")
# password.send_keys('wuqian111')
# 
# button = driver.find_element_by_xpath(".//*[@id='root']/div[2]/div[2]/div[3]/div[1]/div/div/div[1]/form/div[3]/button")
# button.click()
# time.sleep(3)

#职位选择框
choose = driver.find_element_by_xpath(".//div[@class='zp-i-select-label zp-iconfont']")
choose.click()
time.sleep(1)

choose1 = driver.find_element_by_xpath(".//input[@class='zp-search-input']")
choose1.click()

#搜素框
#searchbox = driver.find_element_by_xpath(".//*[@id='rightNav_top']/div/div[2]/div/div/div[2]/div/div/input")
searchbox = driver.find_element_by_class_name('zp-search-input')
#将搜索框的内容清空
searchbox.clear()
searchbox.send_keys('C++开发工程师')
time.sleep(3)

#搜素按钮
#searchbtn = driver.find_element_by_xpath(".//*[@id='rightNav_top']/div/div[2]/div/div/div[2]/div/div/a")
searchbtn = driver.find_element_by_css_selector('.icon.zp-iconfont.zp-search')
searchbtn.click()
time.sleep(2)
print("搜素完成")

#页面的跳转:句柄
handle = driver.current_window_handle

#获取当前浏览器的所有句柄
handles = driver.window_handles
for h in handles:
    if h != handle:   #19 != 13 跳转到新的页面，手动跳转
        driver.switch_to_window(h)
        time.sleep(2)
  
#地点：全国
current = driver.find_element_by_class_name("currentCity")
current.click()
time.sleep(4)
city = driver.find_element_by_xpath(".//*[@id='queryCityBox']/div/ul/li[1]/a")
city.click()
time.sleep(1)
print("搜素完成")

file = open("D://work.txt","w+") #以追加的方式写
n = 6
for j in range(0,4):
    for i in range(0,n+1):
        s = scroll(n,i)
        print(s)
        driver.execute_script(s)
        time.sleep(1)
    contents = driver.find_elements_by_xpath("//div[@class='listItemBox clearfix']")
    for content in contents:
        job = content.find_element_by_class_name('jobName').text 
        company = content.find_element_by_class_name('commpanyName').text 
        print("工作：",job,'       ',"公司：",company)
        file.write("工作："+job+'       '+"公司："+company+'\n')
    
        jobDesc = content.find_element_by_class_name('jobDesc')
        jobDesc_text = jobDesc.find_element_by_class_name('job_saray').text 
        jobDesc_text += "   "
        demand_items = jobDesc.find_elements_by_class_name('demand_item')
        for item in demand_items:
            jobDesc_text += item.text+"   "
        print("条件：",jobDesc_text)
        file.write("条件："+jobDesc_text+'\n')
    
        companyDesc = content.find_element_by_class_name('commpanyDesc').text
        print("规模：",companyDesc)
        file.write("规模："+companyDesc+'\n')
    
        job_welfare = content.find_element_by_class_name('job_welfare')
        welfare_items = job_welfare.find_elements_by_class_name("welfare_item") 
        job_welfare_text = ""
        for fare in welfare_items:
            job_welfare_text += fare.text+"    "
        print("福利：",job_welfare_text) 
        file.write("福利 ："+job_welfare_text+'\n')
    nextbtn = driver.find_element_by_xpath("//div[@class='pager']/button[@class='btn btn-pager']") 
    nextbtn.click()
    
file.close()
driver.close() #关闭当前的浏览器页面



