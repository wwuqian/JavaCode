'''
Created on 2018-7-16

@author: dell
'''

#数据分析 --- 画图
import selenium 
from selenium import webdriver 
import time 
import pymysql 
import xlwt 
import xlsxwriter

conn = pymysql.connect(host='localhost',
                       user='root',
                       password='123',
                       port=3306,
                       database='ipdb',
                       charset='utf8')
#创建SQL语句执行对象
cursor = conn.cursor()
select = "SELECT jobsal 范围,COUNT(1) 数量 FROM job GROUP BY jobsal ORDER BY COUNT(1)"
cursor.execute(select)

wb = xlwt.Workbook(encoding="utf-8")
sheet = wb.add_sheet("工资分析",cell_overwrite_ok=True)
print("-------start-----工资分析-----")
style = xlwt.easyxf("font:name 楷体,bold on,color red")
alignment = xlwt.Alignment()#设置居中
sheet.write_merge(0,0,0,1,"工资分析",style)

for row,result in enumerate(cursor.fetchall()):
    sheet.write(row+1,0,result[0])
    sheet.write(row+1,1,result[1])
print("-------end-------工资分析-----")

select = "SELECT jobedu 学历,COUNT(1) 数量  FROM job GROUP BY jobedu ORDER BY COUNT(1)"
cursor.execute(select)
sheet = wb.add_sheet("学历分析",cell_overwrite_ok=True)
print("-------start------学历分析----")
style = xlwt.easyxf("font:name 楷体,bold on,color red")
sheet.write_merge(0,0,0,1,"学历分析",style)
for row,result in enumerate(cursor.fetchall()):
    sheet.write(row+1,0,result[0])
    sheet.write(row+1,1,result[1])
print("-------end-------学历分析-----")

select = "SELECT jobaddress 地点,COUNT(1) 数量 FROM job GROUP BY jobaddress ORDER BY COUNT(1)"
cursor.execute(select)
sheet = wb.add_sheet("区域分析",cell_overwrite_ok=True)
print("------start-----区域分析----------")  
style = xlwt.easyxf("font:name 楷体,bold on,color red")
sheet.write_merge(0,0,0,1,"区域分析",style)      
for row,result in enumerate(cursor.fetchall() ):
    sheet.write(row+1,0,result[0])
    sheet.write(row+1,1,result[1])
print("-----ending-----区域分析--------") 


wb.save("智联招聘分析.xls")
cursor.close()
conn.close()

