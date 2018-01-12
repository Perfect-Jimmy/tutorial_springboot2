### Mapping

> Mapping定义了type中的诸多字段的数据类型以及这些字段如何被Elasticsearch处理,比如一个字段是否可以查询以及如何分词等

默认情况不需要显式的定义mapping,当新的type或者field引入时,Elasticsearch会自动创建并且注册有合理的默认值的mapping(毫无性能压力),只有要覆盖默认值时才必须要提供mapping定义

> 查询mapping
```
curl -XGET 'localhost:9200/product/_mapping/phone?pretty' 
```

1. 创建文档,index为product,type为phone
```
curl -H "Content-Type:application/json" -XPUT 'localhost:9200/product/phone/2?pretty' -d ' 
{
   "title": "iphone 5s", 
   "description": "made in china", 
   "price" : 4999.0,
   "onSale" : true, 
   "createDate": "2017-10-1"
}'
```
2. 此时查看默认创建的mapping信息如下
```
{
  "product" : {
    "mappings" : {
      "phone" : {
        "properties" : {
          "createDate" : {
            "type" : "text",
            "fields" : {
              "keyword" : {
                "type" : "keyword",
                "ignore_above" : 256
              }
            }
          },
          "description" : {
            "type" : "text",
            "fields" : {
              "keyword" : {
                "type" : "keyword",
                "ignore_above" : 256
              }
            }
          },
          "onSale" : {
            "type" : "boolean"
          },
          "price" : {
            "type" : "float"
          },
          "title" : {
            "type" : "text",
            "fields" : {
              "keyword" : {
                "type" : "keyword",
                "ignore_above" : 256
              }
            }
          }
        }
      }
    }
  }
}
```

> 修改mapping--只能新增字段,不能修改已有字段的类型
```
curl -H "Content-Type:application/json" -XPUT 'localhost:9200/product/phone/_mapping?pretty' -d ' 
{
  "phone": {
        "properties": {
                   "color":{
                          "type":"boolean"
                   }
          }
  }
}'
```
