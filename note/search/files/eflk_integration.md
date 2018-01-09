### eflk整合
> filebeat配置
```
#----------------------------- Logstash output --------------------------------
output.logstash:
  hosts: ["36.111.193.248:5044"]   # The Logstash hosts
```

> logstash配置
```
新建test.conf

input {
     beats {
         port => "5044" #注意此处的双引号
      }
}

output {
     elasticsearch {
         hosts => ["36.111.193.248:9200"]
         index => "tutorial-filebeat-%{+YYYY.MM.dd}"
      }
}
```
*注意:开放5044端口*