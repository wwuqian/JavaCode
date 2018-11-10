#coding: utf-8
'''
Created on 2018-7-5

@author: dell
'''
from selenium import webdriver
import time

def scroll(n,i):
    return "window.scrollTo(0,(document.body.scrollHeight/{0})*{1});".\
        format(n,i)


driver = webdriver.Firefox()
url = "https://www.jd.com"
driver.get(url)

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
   

#goods = driver.find_elements_by_xpath(".//div[@class='gl-i-wrap']")
goods = driver.find_elements_by_css_selector('.gl-i-wrap')

for good in goods:
    #price = good.find_element_by_xpath(".//div[@class='p-price']/strong")
    price = good.find_element_by_css_selector('.p-price')
    print(price.text)
    #name = good.find_element_by_xpath(".//div[@class='p-name p-name-type-2']/a/em")
    name = good.find_element_by_css_selector('.p-name.p-name-type-2')
    print(name.text)

    
    
  




