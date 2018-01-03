###### centos7安装efk(elasticsearch+filebeat+kibana)
> 安装elasticsearch 
1. 上传rpm包到路径/usr/search
2. 解压 rpm -ivh elasticsearch-6.1.1.rpm 安装路径在/usr/share/elasticsearch
3. 启动服务 service elasticsearch start  
   或者命令  
   sudo chkconfig --add elasticsearch  
   sudo -i service elasticsearch start  
   sudo -i service elasticsearch stop  
4. 测试是否安装成功 curl http://localhost:9200/
5. 日志目录 /var/log/elasticsearch/


> 安装kibana
1. 上传rpm包到路径/usr/search
2. 解压 rpm -ivh kibana-6.1.1-x86_64.rpm  安装路径在/usr/share/kibana
3. 修改yml文件 server.host: "0.0.0.0"，可以远程访问,yml路径在/etc/kibana
4. 命令  
   sudo chkconfig --add kibana   # 设置自动启动    
   sudo -i service kibana start  
   sudo -i service kibana stop  
   或者    
   sudo /bin/systemctl daemon-reload  
   sudo -i service kibana start  
   sudo -i service kibana stop  
   nohup ./bin/kibana & 后台启动  
5. 日志目录 /var/log/kibana
    
    
> 安装filebeat
1. 上传rpm包到路径/usr/search
2. 解压 rpm -ivh filebeat-6.1.1-x86_64.rpm  安装路径在/usr/share/filebeat
3. 修改yml文件,yml路径在/etc/filebeat
4. 命令  
   sudo /etc/init.d/filebeat start
5. 日志目录 /var/log/filebeat

    
    
