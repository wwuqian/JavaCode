'''
Created on 2018-7-16

@author: dell
'''
#数据分析：学历分析--将数据存入EXCEL中
import pymysql
import xlwt
conn = pymysql.connect(host='localhost',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
               user='root',
               password='123',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
               port=3306,
               database='ipdb',
               charset='utf8')
cursor = conn.cursor()
select = "SELECT jobedu,COUNT(1) educount FROM job GROUP BY jobedu"
cursor.execute(select)
#获取执行语句的执行结果
all = cursor.fetchall()
#新建工作薄
wb = xlwt.Workbook(encoding="utf-8")

sheet = wb.add_sheet("招聘信息的学历分析",cell_overwrite_ok=True)
print("--------start----学历分析---")
#遍历all，查看结果
for row,a in enumerate(all):
    sheet.write(row,0,a[0])
    sheet.write(row,1,a[1])
    
wb.save("智联招聘数据分析.xls")
print("--------end------学历分析---")
cursor.close()
conn.close()


