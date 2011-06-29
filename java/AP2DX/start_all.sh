#!/bin/bash

gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/coordinator/coordinator.jar" -t "coortinator" &
gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/motor/motor.jar"&
gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/reflex/reflex.jar"&
gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/planner/planner.jar"&
gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/mapper/mapper.jar"&
gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/sensor/sensor.jar"

