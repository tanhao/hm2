#构建的镜像名称
image_name="43.252.170.250/hm2-test/hm-navigation-ui"
#构建的版本号
#image_version=`date +%Y%m%d%H%M`
image_version="latest"
echo $image_version;
#防止存在镜像先删除
#docker rmi $image_name:$image_version

cp -r src/. docker/app
#执行构建镜像
docker build ./docker/. -t $image_name:$image_version
#查看
docker images $image_name
docker push $image_name:$image_version
#删除build过程中产生的镜像
#docker rmi $image_name:$image_version
