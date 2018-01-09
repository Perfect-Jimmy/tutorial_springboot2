### elasticsearch restapi 使用-employee例子
> 创建索引 [测试数据](../data/employee.md)

> 查询根据id查询employee信息
```
curl -XGET 'localhost:9200/tutorial/employee/1?pretty'
```

> 查询所有employee信息(默认查询10条)
```
curl -XGET 'localhost:9200/tutorial/employee/_search?pretty'
```
### 查询字符串(query string)搜索
> 查询姓氏中包含"Smith"的employee
```
curl -XGET 'localhost:9200/tutorial/employee/_search?q=last_name:Smith&pretty'
```

### DSL(Domain Specific Language特定领域语言)
```
curl -XGET 'localhost:9200/tutorial/employee/_search?pretty' -d '
{
  "query": { 
        "match": { "last_name": "Smith" } 
  }
}
'
```


> 查询Smith且age>30的employee
```
curl -XGET 'localhost:9200/tutorial/employee/_search?pretty' -d '
{
  "query": { 
         "bool": { 
              "must": [
                { "match": { "last_name": "Smith"}}
              ],
              "filter": [ 
                { "range": { "age": { "gte": 30}}} 
              ]
            }
  }
}
'
```
### 全文搜索

### 短语搜索