FROM java:8

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN dpkg-reconfigure -f noninteractive tzdata

RUN apt-get update \
 && apt-get install -y imagemagick \
 && rm -rf /var/lib/apt/lists/* \
 && apt-get clean

WORKDIR /usr/src/spring-boot-app

COPY ./scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
COPY ./scripts/runservice.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
