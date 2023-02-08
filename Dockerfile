# Dockerfile is a docker setup instructions for building a docker image
# Defining a base image

# Using java as a java docker image
FROM openjdk:19
# Who is the author of this java image
LABEL maintainer="Nagibin Vlas"
# Copying a war file to a docker image. ADD path_to_war image_name
ADD target/classified-api-docker.war classified-api-docker.war
# Telling Docker what port will be used by a docker container
EXPOSE 8088
# Telling Docker how to execute a spring boot application insie a docker container
ENTRYPOINT ["java", "-jar", "classified-api-docker.war"]

# The docker command to build a docker image using the current file
# docker build -t image_name .
# where -t -> tag,
# .(dot) tells Docker that the Dockerfile is located under the root
# directory where we are supposed to be (pwd)

# The docker command to run a docker image in a container
# docker run -p 8088:8088 image_name