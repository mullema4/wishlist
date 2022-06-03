FROM adoptopenjdk/openjdk11:alpine-jre
COPY ./build/libs/wishlist-1.0.0.jar /wishlist-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/wishlist-1.0.0.jar"]