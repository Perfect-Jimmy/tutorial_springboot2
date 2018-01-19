### elasticsearch-analysis-ik分词器安装

Analyzer:  ik_smart  ik_max_word   
Tokenizer: ik_smart  ik_max_word  
* ik_smart:最少切分
* ik_max_word:细粒度的切分

ik_max_word: 会将文本做最细粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,中华人民,中华,华人,人民共和国,人民,人,民,共和国,共和,和,国国,国歌”，会穷尽各种可能的组合；
ik_smart: 会做最粗粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,国歌”

1. 下载elasticsearch-analysis-ik.zip,地址:https://github.com/medcl/elasticsearch-analysis-ik
2. 上传到目录elasticsearch/plugins/,直接unzip,并重命名为ik,删除zip包重启elasticsearch服务
3. 测试分词
ik_max_word
```
curl  -H "Content-Type:application/json" -XGET "localhost:9200/_analyze?pretty" -d'
{
  "analyzer": "ik_max_word",
  "text":     "中华人民共和国"
}
'
```
结果:
```
{
  "tokens" : [
    {
      "token" : "中华人民共和国",
      "start_offset" : 0,
      "end_offset" : 7,
      "type" : "CN_WORD",
      "position" : 0
    },
    {
      "token" : "中华人民",
      "start_offset" : 0,
      "end_offset" : 4,
      "type" : "CN_WORD",
      "position" : 1
    },
    {
      "token" : "中华",
      "start_offset" : 0,
      "end_offset" : 2,
      "type" : "CN_WORD",
      "position" : 2
    },
    {
      "token" : "华人",
      "start_offset" : 1,
      "end_offset" : 3,
      "type" : "CN_WORD",
      "position" : 3
    },
    {
      "token" : "人民共和国",
      "start_offset" : 2,
      "end_offset" : 7,
      "type" : "CN_WORD",
      "position" : 4
    },
    {
      "token" : "人民",
      "start_offset" : 2,
      "end_offset" : 4,
      "type" : "CN_WORD",
      "position" : 5
    },
    {
      "token" : "共和国",
      "start_offset" : 4,
      "end_offset" : 7,
      "type" : "CN_WORD",
      "position" : 6
    },
    {
      "token" : "共和",
      "start_offset" : 4,
      "end_offset" : 6,
      "type" : "CN_WORD",
      "position" : 7
    },
    {
      "token" : "国",
      "start_offset" : 6,
      "end_offset" : 7,
      "type" : "CN_CHAR",
      "position" : 8
    }
  ]
}
```
ik_smart
```
curl  -H "Content-Type:application/json" -XGET "localhost:9200/_analyze?pretty" -d'
{
  "analyzer": "ik_smart",
  "text":     "中华人民共和国"
}
'
```
结果:
```
{
  "tokens" : [
    {
      "token" : "中华人民共和国",
      "start_offset" : 0,
      "end_offset" : 7,
      "type" : "CN_WORD",
      "position" : 0
    }
  ]
}
```

### elasticsearch-analysis-pinyin分词器安装
pinyin分词器可以让用户输入拼音就查找到相关的关键词.比如输入zhonguo就能匹配到中国
1. clone:https://github.com/medcl/elasticsearch-analysis-pinyin,使用maven打包
2. target\releases\elasticsearch-analysis-pinyin-6.1.1.zip上传到目录elasticsearch/plugins/,直接unzip,并重命名为py,删除zip包重启elasticsearch服务
3. 测试分词
```
curl  -H "Content-Type:application/json" -XGET "localhost:9200/_analyze?pretty" -d'
{
  "analyzer": "pinyin",
  "text":     "刘德华"
}
'
```
结果:
```
{
  "tokens" : [
    {
      "token" : "liu",
      "start_offset" : 0,
      "end_offset" : 0,
      "type" : "word",
      "position" : 0
    },
    {
      "token" : "ldh",
      "start_offset" : 0,
      "end_offset" : 0,
      "type" : "word",
      "position" : 0
    },
    {
      "token" : "de",
      "start_offset" : 0,
      "end_offset" : 0,
      "type" : "word",
      "position" : 1
    },
    {
      "token" : "hua",
      "start_offset" : 0,
      "end_offset" : 0,
      "type" : "word",
      "position" : 2
    }
  ]
}
```