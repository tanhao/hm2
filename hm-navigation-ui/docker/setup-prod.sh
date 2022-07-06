#构建的镜像名称
image_name="43.252.170.250/hm2-prod/hm-navigation-ui"
#构建的版本号
image_version="latest"
cp -r src/. docker/app
#执行构建镜像
docker build -f ./docker/Dockerfile.prod/ . -t $image_name:$image_version
#推送仓库
docker push $image_name:$image_version
#删除build过程中产生的镜像
#docker rmi $image_name:$image_version
