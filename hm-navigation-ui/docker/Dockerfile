FROM nginx:alpine
MAINTAINER hm2
#创建文件夹存放数据
RUN mkdir /app
#复制vue打包数据
COPY app /app
#替换conf配置文件
COPY nginx.conf /etc/nginx/nginx.conf
