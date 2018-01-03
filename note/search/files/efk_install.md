###### centos7安装efk(elasticsearch+filebeat+kibana)
> 安装elasticsearch 
1. 上传rpm包到路径/usr/search
2. rpm -ivh elasticsearch-6.1.1.rpm 
3. 测试是否安装成功 curl http://localhost:9200/