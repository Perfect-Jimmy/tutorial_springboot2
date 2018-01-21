### 文档更新与替换
> 创建文档
```
curl -H "Content-Type:application/json" -XPUT 'localhost:9200/test/car/1?pretty' -d ' 
{
   "brand": "Lexus", 
   "publish_date" : "2015-02-07"
}'
```
结果:
```
{
  "_index" : "test",
  "_type" : "car",
  "_id" : "1",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 0,
  "_primary_term" : 1
}
```
> 替换文档 使用PUT
```
curl -H "Content-Type:application/json" -XPUT 'localhost:9200/test/car/1?pretty' -d ' 
{
   "brand": "Audi", 
   "publish_date" : "2015-02-07"
}'
```
结果:_version变为2,result为updated
```
{
  "_index" : "test",
  "_type" : "car",
  "_id" : "1",
  "_version" : 2,
  "result" : "updated",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```
缺点:必须带上全部的field.如果某一个field没有带上,则此field的值会更新为null.

> 更新文档 使用POST
```
curl -H "Content-Type:application/json" -XPOST 'localhost:9200/test/car/1/_update?pretty' -d ' 
{
   "doc": {
       "name": "benz"
     }
}'
```
结果:
```
{
  "_index" : "test",
  "_type" : "car",
  "_id" : "1",
  "_version" : 3,
  "result" : "updated",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 2,
  "_primary_term" : 1
}
```
结果:_version变为2,result为updated,新增一个字段name
```
{
  "_index" : "test",
  "_type" : "car",
  "_id" : "1",
  "_version" : 3,
  "found" : true,
  "_source" : {
    "brand" : "Audi",
    "publish_date" : "2015-02-07",
    "name" : "benz"
  }
}
```

curl -XGET 'localhost:9200/test/car/1?pretty'