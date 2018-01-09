### elasticsearch restapi 使用-employee例子
> 创建索引 [测试数据](../data/employee.md)
```
curl -XPUT 'localhost:9200/tutorial/employee/1?pretty' -d ' 
{
    "first_name" : "John",
    "last_name" :  "Smith",
    "age" :        25,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
}'
```

> 查询根据id查询employee信息
```
curl -XGET 'localhost:9200/tutorial/employee/1?pretty'
```

> 查询所有employee信息(默认查询10条)
```
curl -XGET 'localhost:9200/tutorial/employee/_search?pretty'
```
**查询字符串(query string)搜索**
> 查询姓氏中包含"Smith"的employee
```
curl -XGET 'localhost:9200/tutorial/employee/_search?q=last_name:Smith&pretty'
```