### elasticsearch常用查询

> 索引列表 curl 'localhost:9200/_cat/indices?v'

> 健康检查 curl 'localhost:9200/_cat/health?v'

> 节点查询 curl 'localhost:9200/_cat/nodes?v'

> 创建索引 curl -X PUT 'localhost:9200/customer/external/1?pretty' -d ' {"name": "John Doe"}'

> 删除索引 curl -X DELETE 'localhost:9200/customer?pretty'

> 查询集群中的文档数量 
```
curl -XGET 'http://localhost:9200/_count?pretty' -d '
{
    "query": {
        "match_all": {}
    }
}
'
```

