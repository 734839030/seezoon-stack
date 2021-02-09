FROM openjdk:11
LABEL maintainer="734839030@qq.com"

ARG src
ARG dest

COPY ${src} ${dest}
WORKDIR ${dest}
EXPOSE 8080
ENTRYPOINT ["bin/docker-entrypoint.sh"]
CMD ["start"]