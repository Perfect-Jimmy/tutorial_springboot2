### Match Phrase Query

> 查询分析文本,并从分析的文本中创建短语查询,match_phrase的主要作用是用于匹配包含当前短语的文档

```
curl -H "Content-Type:application/json" -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "match_phrase": { 
                    "title" : "in action"
              } 
     }
}
'
```
*注:输入的内容要一致且内容顺序、间隔也必须一致才能匹配上*


*需求场景:数据内容为"This is my book",输入内容"is book",如果要匹配上数据内容需要指定参数slop*

查询不出数据:
```
curl -H "Content-Type:application/json" -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "match_phrase": { 
                    "summary" : "scalable search using"
              } 
     }
}
'
```
设置slop参数查询出数据
```
curl -H "Content-Type:application/json" -XGET 'localhost:9200/tutorial/book/_search?pretty' -d '
{
  "query": { 
        "match_phrase": { 
                     "summary" : {
                             "query":"scalable search using",
                             "slop":1
                     }
        } 
  }
}
'
```
**说明:slop的数值表示输入的短语中每个词项(term)之间允许隔着几个词项(term)**
