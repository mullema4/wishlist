# Small & secure JRE 11 base image
FROM adoptopenjdk/openjdk11:alpine-jre
#you can also use eclipse-temurin:17-jre-alpine

# Install security updates
RUN apk -U upgrade

# Add the application's JAR to the container
COPY ./build/libs/wishlist.jar /wishlist.jar

# Create a group and user so we are not running our container and application as root and thus user 0 which is a security issue.
RUN addgroup --system --gid 1000 appgroup \
    && adduser --system --uid 1000 --ingroup appgroup --shell /bin/sh appuser

# Tell docker that all future commands should run as the appuser user
USER 1000

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-Xms512m", "-Xmx512m", "-jar", "/wishlist.jar"]