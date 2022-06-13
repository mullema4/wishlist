FROM adoptopenjdk/openjdk11:alpine-jre

# Add the application's jar to the container
COPY ./build/libs/wishlist-1.0.0.jar /wishlist-1.0.0.jar

# Create a group and user so we are not running our container and application as root and thus user 0 which is a security issue.
RUN addgroup --system --gid 1000 appgroup \
    && adduser --system --uid 1000 --ingroup appgroup --shell /bin/sh appuser

# Tell docker that all future commands should run as the appuser user
USER 1000

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/wishlist-1.0.0.jar"]