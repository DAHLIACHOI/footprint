#!/usr/bin/env bash

# $IDLE_PROFILE을 통해 properties 값을 가져오고 active profile을 지정한다

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

BUILD_PATH=$(ls /home/ec2-user/footprint/build/*.jar)
JAR_NAME=$(basename $BUILD_PATH)
echo "> build 파일명: $JAR_NAME"

IDLE_PROFILE=$(find_sleep_profile)
DEPLOY_JAR_NAME=footprint-0.0.1-SNAPSHOT-$IDLE_PROFILE.jar
echo "> deploy jar 파일명 변경: $DEPLOY_JAR_NAME"

echo "> Build 파일 복사"
DEPLOY_PATH=/home/ec2-user/footprint/
echo "> cp $BUILD_PATH $DEPLOY_PATH$DEPLOY_JAR_NAME"
cp $BUILD_PATH $DEPLOY_PATH$DEPLOY_JAR_NAME

echo "> springboot-deploy.jar 교체"
APPLICATION_JAR=$DEPLOY_PATH$DEPLOY_JAR_NAME

echo "> $APPLICATION_JAR 를 profile=$IDLE_PROFILE 로 실행합니다."
 nohup java -Dspring.profiles.active=$IDLE_PROFILE -jar $APPLICATION_JAR > /prod/null 2> /prod/null < /prod/null &
#nohup java -Dspring.config.activate.on-profile.active=$IDLE_PROFILE -jar $APPLICATION_JAR > /prod/null 2> /prod/null < /prod/null &