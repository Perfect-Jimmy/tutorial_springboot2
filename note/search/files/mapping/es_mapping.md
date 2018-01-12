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
                         "type":"text"
                   }
          }
  }
}'
```

### mapping处理流程

1. es索引(存储)文档时,把字段中的内容传递给相应的analyzer,analyzer再传递给各自的filters
2. 一个filter就是一个转换数据的方法,如一个将字符串转为小写的方法就是一个filter
3. 一个analyzer由一组顺序排列的filter组成,执行分析的过程就是按顺序一个filter一个filter依次调用,ES存储和索引最后得到的结果
4. 总结来说mapping的作用就是执行一系列的指令将输入的数据转成可搜索的索引项
Elasticsearch 默认使用 standard analyzer


### 属性解析 [官网参数设置doc](https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-params.html)
description为field名称,type指定field类型,index指定索引类型,analyzer指定索引阶段和检索阶段分词类型

> index 默认true. false时不可查询 

> analyzer es默认使用standard analyzer分词器,还有其他内建分词器whitespace,simple,english
例子:

1. 新建索引
```
curl -H "Content-Type:application/json" -XPUT 'localhost:9200/testmapping?pretty'
```
2. 创建type并设置mapping
```
curl -H 'Content-Type: application/json' -XPUT "localhost:9200/testmapping/phone/_mapping?pretty" -d ' 
{
  "phone": {
        "properties": {
                 "description": {
                      "type":"text",
                      "index" : true,
                      "analyzer": "standard"
                 }
        }
  }
}
'
```
3. 创建文档
```
curl -H "Content-Type:application/json" -XPUT 'localhost:9200/testmapping/phone/1?pretty' -d ' 
{
   "description": "A Pretty cool boy"
}'
```
4. 查询文档
```
curl -H "Content-Type:application/json" -XGET 'localhost:9200/testmapping/phone/_search?pretty' -d '
{
  "query": { 
        "match": { "description": "A" } 
      }
}
'
```
5. analysis文档
```
curl  -H "Content-Type:application/json" -XGET "localhost:9200/_analyze?pretty" -d'
{
  "analyzer": "standard",
  "text":     "A Pretty cool boy"
}
'
```
结果:
```
{
  "tokens" : [
    {
      "token" : "a",
      "start_offset" : 0,
      "end_offset" : 1,
      "type" : "<ALPHANUM>",
      "position" : 0
    },
    {
      "token" : "pretty",
      "start_offset" : 2,
      "end_offset" : 8,
      "type" : "<ALPHANUM>",
      "position" : 1
    },
    {
      "token" : "cool",
      "start_offset" : 9,
      "end_offset" : 13,
      "type" : "<ALPHANUM>",
      "position" : 2
    },
    {
      "token" : "boy",
      "start_offset" : 14,
      "end_offset" : 17,
      "type" : "<ALPHANUM>",
      "position" : 3
    }
  ]
}
```
*说明:"A Pretty cool boy"被分成a,pretty,cool和boy索引起来*

