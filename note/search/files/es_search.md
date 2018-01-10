### 检索实战

> [检索实战数据](../data/book.md)

> 全文检索:match 指定特定字段检索
1. 指定title字段查询,包含"guide"
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "match": { "title": "guide" } 
      }
}
'
```

> 全文检索:multi_match 多字段检索,对多个字段运行相同查询
1. 对文档中的所有字段进行查询,包含"guide"
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "multi_match": { 
                    "query" : "guide",
                    "fields" : ["_all"]
              } 
     }
}
'
```
2. 对文档中的title,publisher字段进行查询,包含"guide"
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "multi_match": { 
                    "query" : "guide",
                    "fields" : ["title","publisher"]
              } 
     }
}
'
```
