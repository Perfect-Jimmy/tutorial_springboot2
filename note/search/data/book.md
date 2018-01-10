### 检索实战测试数据
```
curl -XPUT 'localhost:9200/tutorial/book/1?pretty' -d ' 
{
   "title": "Elasticsearch: The Definitive Guide", 
   "authors": ["clinton gormley", "zachary tong"],
   "summary" : "A distibuted real-time search and analytics engine", 
   "publish_date" : "2015-02-07", 
   "num_reviews": 20, 
   "publisher": "oreilly"
}'

curl -XPUT 'localhost:9200/tutorial/book/2?pretty' -d ' 
{
   "title": "Taming Text: How to Find, Organize, and Manipulate It", 
   "authors": ["grant ingersoll", "thomas morton", "drew farris"], 
   "summary" : "organize text using approaches such as full-text search, proper name recognition, clustering, tagging, information extraction, and summarization", 
   "publish_date" : "2013-01-24", 
   "num_reviews": 12, 
   "publisher": "manning" 
}'

curl -XPUT 'localhost:9200/tutorial/book/3?pretty' -d ' 
{
    "title": "Elasticsearch in Action", 
    "authors": ["radu gheorge", "matthew lee hinman", "roy russo"], 
    "summary" : "build scalable search applications using Elasticsearch without having to do complex low-level programming or understand advanced data science algorithms", 
    "publish_date" : "2015-12-03", 
    "num_reviews": 18, 
    "publisher": "manning"
}'

curl -XPUT 'localhost:9200/tutorial/book/4?pretty' -d ' 
{
   "title": "Solr in Action", 
   "authors": ["trey grainger", "timothy potter"], 
   "summary" : "Comprehensive guide to implementing a scalable search engine using Apache Solr", 
   "publish_date" : "2014-04-05", 
   "num_reviews": 23, 
   "publisher": "manning" 
}'

```