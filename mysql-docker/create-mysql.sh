#sudo ifconfig lo0 alias 192.100.30.100
docker run --name mysql-fuse-batch-test -p 3306:3306 -e MYSQL_ROOT_HOST=192.100.30.100 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=fuse -e MYSQL_PASSWORD=fuse -e MYSQL_DATABASE=test -d mysql-fuse-batch:1.0


#ENTRYPOINT ["/entrypoint.sh"]
#EXPOSE 3306 33060
#CMD ["mysqld"]