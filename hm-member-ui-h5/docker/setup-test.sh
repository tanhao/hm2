#构建的镜像名称
image_name="43.252.170.250/hm2-test/hm-member-ui-h5"
#构建的版本号
#image_version=`date +%Y%m%d%H%M`
image_version="latest"
echo $image_version;
##如果有添加包或者第一次npm构建使用下面初始化
#rm -rf node_modules/ && npm install --unsafe-perm
#打包，分环境
npm run build-test-h5
#执行构建镜像
docker build ./docker/. -t $image_name:$image_version
#查看
docker images $image_name
docker push $image_name:$image_version
#删除build过程中产生的镜像
#docker rmi $image_name:$image_version
