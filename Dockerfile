FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_OPTS="" \
    TZ=Asia/Seoul
CMD java $JAVA_OPTS -server -jar app.jar