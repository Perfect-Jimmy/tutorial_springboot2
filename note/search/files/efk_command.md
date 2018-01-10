### elasticsearch常用查询

> 索引列表 curl 'localhost:9200/_cat/indices?v'

> 健康检查 curl 'localhost:9200/_cat/health?v'

> 节点查询 curl 'localhost:9200/_cat/nodes?v'

> 创建索引 curl -X PUT 'localhost:9200/customer?pretty'

> 删除索引 curl -X DELETE 'localhost:9200/customer?pretty'

> 创建文档 curl -X PUT 'localhost:9200/customer/external/1?pretty' -d ' {"name": "John Doe"}'

> 创建文档 curl -X POST 'localhost:9200/customer/external?pretty' -d ' {"name": "Jane Doe" }'  

*注:不指定id的时候，使用POST，elasticsearch会自动生成一个ID*
  
> 更新文档 curl -X PUT 'localhost:9200/customer/external/1?pretty' -d ' { "name": "Jane Doe1" }'


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
> 查看所有的mapping curl 'localhost:9200/_mapping/?pretty'

> 查看指定index的mapping curl 'localhost:9200/tutorial/_mapping/?pretty'

> 查看指定index和type的mapping  curl 'localhost:9200/tutorial/_mapping/book?pretty'




