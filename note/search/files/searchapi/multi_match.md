### Multi Match Query

> 基于匹配查询且允许多字段查询构建的.即允许我们在输入一个搜索内容的时候,支持在某个索引类型下的多个字段中进行搜索.

*需求场景:输入"tutorial elasticsearch",希望title中包含"tutorial elasticsearch"的优先返回,若没有则返回desc中包含的*
```
curl -H "Content-Type:application/json" -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "multi_match": { 
                    "query" : "Elasticsearch ",
                    "fields" : ["title","summary"]
              } 
     }
}
'
```
结果title和summary都含有Elasticsearch的排在后面
```
"hits" : {
    "total" : 2,
    "max_score" : 0.2876821,
    "hits" : [
      {
        "_index" : "tutorial",
        "_type" : "book",
        "_id" : "1",
        "_score" : 0.2876821,
        "_source" : {
          "title" : "Elasticsearch: The Definitive Guide",
          "authors" : [
            "clinton gormley",
            "zachary tong"
          ],
          "summary" : "A distibuted real-time search and analytics engine",
          "publish_date" : "2015-02-07",
          "num_reviews" : 20,
          "publisher" : "oreilly"
        }
      },
      {
        "_index" : "tutorial",
        "_type" : "book",
        "_id" : "3",
        "_score" : 0.2876821,
        "_source" : {
          "title" : "Elasticsearch in Action",
          "authors" : [
            "radu gheorge",
            "matthew lee hinman",
            "roy russo"
          ],
          "summary" : "build scalable search applications using Elasticsearch without having to do complex low-level programming or understand advanced data science algorithms",
          "publish_date" : "2015-12-03",
          "num_reviews" : 18,
          "publisher" : "manning"
        }
      }
    ]
  }
```
> tie_breaker参数来控制匹配的权重缓冲值,意思是每个字段都会在得到匹配之后都会对该值进行计算.

```
curl -H "Content-Type:application/json" -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "multi_match": { 
                    "query" : "Elasticsearch ",
                    "fields" : ["title","summary"],
                    "tie_breaker": 20
              } 
     }
}
'
```
结果:title和summary都含有Elasticsearch的排在前面
