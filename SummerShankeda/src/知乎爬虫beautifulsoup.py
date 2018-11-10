#coding: utf-8
'''
Created on 2018-7-11

@author: dell
'''
from selenium import webdriver
from selenium.webdriver.common.proxy import *
from bs4 import BeautifulSoup
import time
import random 

def scroll(n,i):  
    return "window.scrollTo(0,(document.body.scrollHeight/{0})*{1});".\
        format(n,i)

#�������
listIP = [["180.118.128.188","9000"],["121.232.148.178","9000"],["125.94.0.250","9000"],["122.243.13.41","9000"],["180.118.93.148","9000"]]
use_proxy = random.choice(listIP)
user_proxy = use_proxy[0]+":"+use_proxy[1]

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
driver.implicitly_wait(10)
driver.get('https://www.zhihu.com')
#driver.get('https://www.zhihu.com/signup')

# #֪����¼��ť��������е�¼
# login1 = driver.find_element_by_css_selector('div[class="SignContainer-switch"] span')
# login1.click()
# 
# #��¼�û���
# user = driver.find_element_by_name('username')
# user.send_keys('18392978637')
# 
# #��¼����
# pwd = driver.find_element_by_name('password')
# pwd.send_keys('Test_811031')
# 
# #�ύ��¼
# login = driver.find_element_by_css_selector('button[class="Button SignFlow-submitButton Button--primary Button--blue"]')
# login.click()
# time.sleep(3)

#֪�������
input = driver.find_element_by_xpath(".//*[@id='Popover1-toggle']")
input.send_keys('吴亦凡')
time.sleep(3)

btnNext = driver.find_element_by_xpath(".//*[@id='root']/div/div[2]/header/div/div[1]/div/form/div/div/div/div/button")
btnNext.click()
time.sleep(3)
print("搜索完成")

n = 5
for i in range(0,n+1):
    s = scroll(n,i)
    print(s)
    driver.execute_script(s)
    time.sleep(2)
    
soup = BeautifulSoup(driver.page_source,"html.parser")
texts = soup.select(".List-item")

for tr in texts:
    title = tr.select(".ContentItem-title a")[0].text.strip()
    print(title)
