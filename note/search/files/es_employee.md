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
