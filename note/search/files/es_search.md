### 检索实战5.x

> [检索实战数据](../data/book.md)

### 全文检索

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
curl -H "Content-Type:application/json" -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
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
2. 对文档中的title,summary字段进行查询,包含"guide"
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "multi_match": { 
                    "query" : "elasticsearch guide",
                    "fields" : ["title","summary"]
              } 
     }
}
'
```

>  Boosting提升某字段得分的检索

对文档中的title,summary字段进行查询,希望提高title字段的得分,如下设置提高文档的相关性
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "multi_match": { 
                    "query" : "elasticsearch guide",
                    "fields" : ["title^3","summary"]
              } 
     }
}
'
```

> 分页,高亮,指定返回字段检索
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
       "match" : {
                   "title" : "in action"
               }
     },
  "size": 2,
  "from": 0,
  "_source": [ "title", "summary", "publish_date" ],
  "highlight": {
            "fields" : {
                 "title" : {}
            }
       }
}
'
```

> Bool检索:must等效于and,must_not等效于not,should等效于or

搜索title中包含"Elasticsearch"或"Solr"的书,并且authors是"clinton gormley",但不是"radu gheorge"
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "bool": {
            "must": {
                "bool":{
                    "should":[
                        {"match":{"title":"Elasticsearch"}},
                        {"match":{"title":"Solr"}}
                    ]
                 }
            },
            "must": { "match": { "authors": "clinton gormely" }},
            "must_not": { "match": {"authors": "radu gheorge" }}
        }
            
  }
}
'
```

> Fuzzy 模糊检索  
> 启用模糊匹配来捕捉拼写错误,基于与原始词的Levenshtein距离来指定模糊度,将模糊度设置为1可能会提高整体搜索性能
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
   "query": {
         "multi_match" : {
               "query" : "Comprehsnsive ",
               "fields": ["title", "summary"],
               "fuzziness": "AUTO"
         }
   },
   "_source": ["title", "summary", "publish_date"]
}
'
```

> Wildcard Query 通配符检索  
> 通配符查询允许您指定匹配的模式,而不是整个词组(term)检索   
> 1.?匹配任何单个字符  
> 2.*匹配零个或多个字符包括空字符序列  

查询"t"字母开头的作者的所有记录
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
   "query": {
         "wildcard" : {
                    "authors" : "t*"
         }
   },
   "highlight": {
           "fields" : {
               "authors" : {}
           }
   }
}
'
```

> Regexp Query 正则表达式检索:指定比通配符检索更复杂的检索模式
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
   "query": {
         "regexp" : {
                "authors" : "t[a-z]*y"
         }
   },
   "highlight": {
           "fields" : {
               "authors" : {}
           }
   }
}
'
```

> Match Phrase Query 匹配短语检索:匹配短语查询要求查询字符串中的所有词都存在于文档中,按照查询字符串中指定的顺序并且彼此靠近  
> 默认情况下这些词必须完全相邻,但可以指定偏离值(slop value),该值指示在仍然考虑文档匹配的情况下词与词之间的偏离值
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
   "query": {
         "multi_match" : {
                     "query": "search engine",
                     "fields": ["title", "summary"],
                     "type": "phrase",
                     "slop": 3
         }
   },
   "_source": [ "title", "summary", "publish_date" ]
}
'
```

> 匹配词组前缀检索
> 和match_phrase查询一样,它接受一个斜率参数,使得单词的顺序和相对位置没有那么"严格"
> 还接受max_expansions参数来限制匹配的条件数以减少资源强度
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
   "query": {
         "match_phrase_prefix" : {
                             "summary": {
                             "query": "search en",
                             "slop": 3,
                             "max_expansions": 10
               }
         }
   },
   "_source": [ "title", "summary", "publish_date" ]
}
'
```

> Simple Query String 简化的字符串检索
> 是query_string查询的一个版本,分别用+ / | / - 替换了AND / OR / NOT的使用，并放弃查询的无效部分，而不是在用户出错时抛出异常

对"search algorithm"一词执行模糊搜索,其中一本作者是"grant ingersoll"或"tom morton"
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
   "query": {
         "simple_query_string" : {
                    "query": "(saerch~1 algorithm~1) + (grant ingersoll)  | (tom morton)",
                    "fields": ["_all", "summary^2"]
         }
   },
   "_source": [ "title", "summary", "publish_date" ]
}
'
```


### 结构化搜索
> term/terms 指定字段检索

1. 查询publisher为manning的数据
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
   "query": {
         "term":{
              "publisher":"manning"
         }
   },
   "_source": [ "title", "publisher", "publish_date" ]
}
'
```
2. 定多个关键词进行检索
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
   "query": {
         "terms":{
              "publisher":["oreilly", "packt"]
         }
   },
   "_source": [ "title", "publisher", "publish_date" ]
}
'
```
> 排序检索
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
   "query": {
         "term":{
              "publisher":"manning"
         }
   },
   "_source": [ "title", "publisher", "publish_date" ],
   "sort": [
           { "publish_date": {"order":"desc"}}
       ]
}
'
```

> Range query 范围检索

查询publish_date为2015的数据
```
curl -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
   "query": {
         "range":{
              "publish_date":{
                    "gte": "2015-01-01",
                    "lte": "2015-12-31"
              }
         }
   },
   "_source": [ "title", "publisher", "publish_date" ]
}
'
```
*注:范围查询适用于日期,数字和字符串类型字段*