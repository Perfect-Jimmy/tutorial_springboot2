###### centos7安装efk(elasticsearch+filebeat+kibana)
> 安装elasticsearch 
1. 上传rpm包到路径/usr/search
2. 解压 rpm -ivh elasticsearch-6.1.1.rpm 安装路径在/usr/share/elasticsearch
3. 启动服务 service elasticsearch start  
   或者命令  
   sudo chkconfig --add elasticsearch  
   sudo -i service elasticsearch start  
   sudo -i service elasticsearch stop   
   sudo -i service elasticsearch status 
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
   sudo -i service kibana restart   
   或者    
   sudo /bin/systemctl daemon-reload  
   nohup ./bin/kibana & 后台启动  
5. 日志目录 /var/log/kibana
    
    
> 安装filebeat
1. 上传rpm包到路径/usr/search
2. 解压 rpm -ivh filebeat-6.1.1-x86_64.rpm  安装路径在/usr/share/filebeat
3. 修改yml文件,yml路径在/etc/filebeat
4. 命令  
   sudo /etc/init.d/filebeat start
   sudo /etc/init.d/filebeat restart
   sudo /etc/init.d/filebeat status
5. 日志目录 /var/log/filebeat

    
> 防火墙打开9200端口
1. 查看防火墙  systemctl status firewalld  
2. 开启防火墙  systemctl start firewalld  
3. 关闭防火墙  systemctl stop firewalld  
4. 开启端口    firewall-cmd --zone=public --add-port=9200/tcp --permanent      
5. 重启防火墙  firewall-cmd --reload  
6. 查看端口是否开放 firewall-cmd --query-port=9200/tcp


> netstat -tunlp 查看开放的端口和监听信息
    
> some
1. es外网访问
```
network.host: 0.0.0.0  # 设置
discovery.zen.ping.unicast.hosts: ["0.0.0.0"] # 设置
```
