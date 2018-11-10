'''
Created on 2018-7-14

@author: dell
'''

#数据库的连接
#1.新建连接
#——主机地址：host，用户名：root，密码：mysql，端口号，charset：utf8，数据库：database = work
#2.新建游标对象
#——cursor()
#3.准备sql
#——insert into iptable() values...
#4.执行sql
#——conn.commit()
#5.提交结果
#——close()

import pymysql
conn = pymysql.connect(host='localhost',
                user='root',
                password='123',
                port=3306,
                database='ipdb',
                charset='utf8')
cursor = conn.cursor()
sql = "insert into iptable(ip,port) values('127.0.0.1','3306')"
row = cursor.execute(sql) #执行
if row > 0:
    print("成功添加一条数据")
    conn.commit()  #提交
else:
    print("失败添加一条数据")
cursor.close()
conn.close()






