#coding: utf-8
'''
Created on 2018-7-4

@author: delln
'''
import selenium
import time
from selenium import webdriver 
from selenium.webdriver.common.keys import Keys

def scroll(n,i):  
    return "window.scrollTo(0,(document.body.scrollHeight/{0})*{1});".\
        format(n,i)

#打开浏览器
fire = webdriver.Firefox()
fire.get('https://www.zhihu.com/signup')

#知乎登录按钮点击，进行登录
login1 = fire.find_element_by_css_selector('div[class="SignContainer-switch"] span')
login1.click()

#登录用户名
user = fire.find_element_by_name('username')
user.send_keys('18392978637')

#登录密码
pwd = fire.find_element_by_name('password')
pwd.send_keys('Test_811031')

#提交登录
login = fire.find_element_by_css_selector('button[class="Button SignFlow-submitButton Button--primary Button--blue"]')
login.click()
time.sleep(3)

#知乎输入框
input = fire.find_element_by_xpath(".//*[@id='Popover1-toggle']")
input.send_keys('吴亦凡')
time.sleep(3)

btnNext = fire.find_element_by_xpath(".//*[@id='root']/div/div[2]/header/div/div[1]/div/form/div/div/div/div/button")
btnNext.click()
time.sleep(3)
print("搜索完成")

n = 2
for i in range(0,n+1):
    s = scroll(n,i)
    print(s)
    time.sleep(2)

texts = fire.find_elements_by_xpath(".//h2[@class='ContentItem-title']/div/a")
    
for tr in texts:
    print(tr.text)














