FROM hypriot/rpi-java
COPY randomGenerator.java .
RUN javac randomGenerator.java
CMD ["java","randomGenerator"]
EXPOSE 9898
