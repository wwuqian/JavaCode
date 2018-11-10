#coding: utf-8
'''
Created on 2018-7-11

@author: dell
'''
#LXML
from selenium import webdriver
from selenium.webdriver.common.proxy import *
from lxml import etree
import time
import random 

def scroll(n,i):
    return "window.scrollTo(0,(document.body.scrollHeight/{0})*{1});".\
        format(n,i)

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
driver.get('https://www.jd.com')

searchbox = driver.find_element_by_id('key')
searchbox.send_keys('ZUK Z2')

searchbtn = driver.find_element_by_class_name('button')
searchbtn.click()
time.sleep(2)

n = 2
for i in range(0,n+1):
    s = scroll(n,i)
    print(s)
    driver.execute_script(s)
    time.sleep(5)
  
html = etree.HTML(driver.page_source) 

vlinks = html.xpath("//*[@id='J_goodsList']/ul[@class='gl-warp clearfix']")

for vlink in vlinks:
    plink = vlink.xpath("./li/div/div[@class='p-img']/a/@href")
    print(plink)
    sign = vlink.xpath("./li/div/div[@class='p-price']/strong/em/text()")
    print(sign)
    price = vlink.xpath("./li/div/div[@class='p-price']/strong/i/text()")
    print(price)
    content = vlink.xpath("./li/div/div[@class='p-name p-name-type-2']/a/em/text()")
    print(content)
    
