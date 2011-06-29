#!/bin/bash

gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/coordinator/coordinator.jar" -t "coortinator" &
gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/motor/motor.jar" -t "motor"&
gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/reflex/reflex.jar" -t "reflex"&
gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/planner/planner.jar" -t "planner"&
gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/mapper/mapper.jar" -t "mapper" &
gnome-terminal --command "java -jar /home/mpdw/uva/ap2dx/java/AP2DX/ant/jar/sensor/sensor.jar" -t "sensor" &
