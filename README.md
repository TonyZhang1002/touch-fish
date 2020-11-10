# 摸鱼项目：基于Spring Boot的爬虫网站

🚧🚧🚧施工中 🚧🚧🚧

此项目是业余摸鱼项目，模仿[mofish](https://mo.fish)制作的爬虫网站，爬取三个网站的热点帖子/内容，并集合储存并展示。

## Run

可以使用docker-compose，直接pull已经打包好的docker镜像并一键部署在本地环境。

```
#在项目目录下
docker-compose up //开启
docker-compose down //关闭并删除所有images和network
```

## Visit

通过上述方式运行该项目，请访问本地8081端口，并使用正确的path （`http://localhost:8081/v2ex`）。

## Build

若想通过maven编译项目并构建dockers镜像，运行下面代码。插件 ***dockerfile-maven-plugin*** 会自动读取 Dockerfile 并构建image。

```shell
mvn clean
mvn package
```

注意如果要在本地debug该项目，需要在 `touch-fish\src\main\resources\application.properties` 中更改mongo db的url。