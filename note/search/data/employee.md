### employee测试数据
```
curl -XPUT 'localhost:9200/tutorial/employee/1?pretty' -d ' 
{
    "first_name" : "John",
    "last_name" :  "Smith",
    "age" :        25,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
}'

curl -XPUT 'localhost:9200/tutorial/employee/2?pretty' -d ' 
{
    "first_name" :  "Jane",
    "last_name" :   "Smith",
    "age" :         32,
    "about" :       "I like to collect rock albums",
    "interests":  [ "music" ]
}'

curl -XPUT 'localhost:9200/tutorial/employee/3?pretty' -d ' 
{
    "first_name" :  "Douglas",
    "last_name" :   "Fir",
    "age" :         35,
    "about":        "I like to build cabinets",
    "interests":  [ "forestry" ]
}'
```