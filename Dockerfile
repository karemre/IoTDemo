FROM hypriot/rpi-java
COPY HeatControl.java .
RUN apt-get update
RUN apt-get install -y curl
EXPOSE 9898
