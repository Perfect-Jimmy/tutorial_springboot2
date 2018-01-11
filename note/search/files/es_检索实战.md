### 检索实战6.x

> match 匹配查询
```
curl -H "Content-Type:application/json" -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "match": { "title": "guide" } 
      }
}
'
```

> Match Phrase Prefix Query 短语前缀匹配查询

