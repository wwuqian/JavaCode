#coding: utf-8
'''
Created on 2018-7-9

@author: dell
'''
#爬取符合搜索书籍条件的所有书籍

from selenium import webdriver
import time

def scroll(n,i):
    return "window.scrollTo(0,(document.body.scrollHeight/{0})*{1});".\
        format(n,i)

#driver = webdriver.Chrome("C:/Program Files (x86)/Google/Chrome/Application/chromedriver")
driver = webdriver.Firefox()
url = "http://www.quanben5.com/"
driver.get(url)

searchbox = driver.find_element_by_xpath("html/body/div[3]/div/div[1]/form/input[3]")
searchbox.send_keys('诛仙')

searchbtn = driver.find_element_by_class_name('search_submit')
searchbtn.click()
time.sleep(2)

n = 2   
for i in range(0,4):
    for j in range(0,n+1):
       s = scroll(n,j)
       print(s)
       driver.execute_script(s)
       time.sleep(1)
    books = driver.find_elements_by_xpath(".//div[@class='pic_txt_list']")
    for book in books:
        name = book.find_element_by_xpath(".//span[@class='name']/b")
        print(name.text)
        author = book.find_element_by_xpath(".//p[@class='info']")
        print(author.text)
        descrip = book.find_element_by_xpath(".//p[@class='description']")
        print(descrip.text)
    next = driver.find_element_by_xpath(".//p[@class='page_next']/a")     
    next.click()


    

