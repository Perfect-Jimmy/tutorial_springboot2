### elasticsearch restapi 使用-employee例子

> 创建索引和文档 [测试数据](../data/employee.md)

> 查询根据id查询employee信息
```
curl -XGET 'localhost:9200/tutorial/employee/1?pretty'
```

> 查询所有employee信息(默认查询10条)
```
curl -XGET 'localhost:9200/tutorial/employee/_search?pretty'
```

> 查询返回指定字段:返回id为1的age字段
```
curl -XGET 'localhost:9200/tutorial/employee/1/?_source=age&pretty'
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


> 查询名字包含Smith且age>30的employee
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
> 搜索所有喜欢"rock climbing"的employee
```
curl -XGET 'localhost:9200/tutorial/employee/_search?pretty' -d '
{
  "query": { 
         "match": { "about": "rock climbing"}
  }
}
'
```

结果相关性评分:默认情况下，Elasticsearch根据结果相关性评分来对结果集进行排序，所谓的「结果相关性评分」就是文档与查询条件的匹配程度。很显然，排名第一的John Smith的about字段明确的写到“rock climbing”,
但是为什么Jane Smith也会出现在结果里呢？原因是“rock”在她的abuot字段中被提及了。因为只有“rock”被提及而“climbing”没有，所以她的_score要低于John

### 短语搜索:match_phrase
> 确切的匹配若干个单词或者短语(phrases)。例如我们想要查询同时包含"rock"和"climbing"（并且是相邻的）的员工记录
```
curl -XGET 'localhost:9200/tutorial/employee/_search?pretty' -d '
{
  "query": { 
         "match_phrase": { "about": "rock climbing" }
  }
}
'
```

### 高亮显示
```
curl -XGET 'localhost:9200/tutorial/employee/_search?pretty' -d '
{
  "query": { 
         "match_phrase": { "about": "rock climbing" }
  },
  "highlight": {
          "fields" : {
              "about" : {}
          }
      }
}
'
```

