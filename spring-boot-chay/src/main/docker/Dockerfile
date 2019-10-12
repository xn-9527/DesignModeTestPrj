# FROM 表示基于哪个基础镜像制作镜像
FROM java:8



# RUN 运行指定的命令
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN dpkg-reconfigure -f noninteractive tzdata

# config apt-get source
RUN apt-key adv --recv-keys --keyserver keyserver.ubuntu.com 3B4FE6ACC0B21F32
RUN apt-key adv --recv-keys --keyserver keyserver.ubuntu.com 40976EAF437D05B5
ADD sources.list /etc/apt/
RUN sed -i s@/archive.ubuntu.com/@/mirrors.aliyun.com/@g /etc/apt/sources.list
# fix Failed to fetch http://deb.debian.org/debian/dists/jessie-updates/main/binary-amd64/Packages  404  Not Found
RUN echo "deb [check-valid-until=no] http://archive.debian.org/debian jessie-backports main" > /etc/apt/sources.list.d/jessie-backports.list
# As suggested by a user, for some people this line works instead of the first one. Use whichever works for your case
# RUN echo "deb [check-valid-until=no] http://archive.debian.org/debian jessie main" > /etc/apt/sources.list.d/jessie.list
RUN sed -i '/deb http:\/\/deb.debian.org\/debian jessie-updates main/d' /etc/apt/sources.list
# fix Release file for http://archive.debian.org/debian/dists/jessie-backports/InRelease is expired (invalid since 234d 6h 15min 30s). Updates for this repository will not be applied.
RUN apt-get -o Acquire::Check-Valid-Until=false update --fix-missing

# imagemagick 图像处理软件时
RUN apt-get install -y imagemagick \
 && rm -rf /var/lib/apt/lists/* \
 && apt-get clean

WORKDIR /usr/src/spring-boot-app

COPY ./scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
COPY ./scripts/runservice.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
# ENTRYPOINT 进入点，后面跟的是需要执行的脚本
ENTRYPOINT ["/entrypoint.sh"]