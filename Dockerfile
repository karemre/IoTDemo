FROM hypriot/rpi-java
COPY HeatControl.java .
RUN javac HeatControl.java
CMD ["java","HeatControl"]
EXPOSE 9898
