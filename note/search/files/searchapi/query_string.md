### Query String Query

> 无需指定某字段而对文档全文进行匹配查询的一个高级查询,同时可以指定在哪些字段上进行匹配.

真正意义上的全文检索
1. 可以在多个索引下进行无指定字段检索
2. 可以在指定的索引/类型下进行无指定字段检索

```
curl -H "Content-Type:application/json" -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "query_string" : {
                     "default_field" : "title",
                     "query" : "(Elasticsearch AND Action) OR Guide"
        }
     }
}
'
```

### 常用参数
> default_field:用于指定默认查询的字段

> 