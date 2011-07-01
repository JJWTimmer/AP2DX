#!/bin/bash
gnome-terminal --working-directory=${PWD}/ant/jar/coordinator --command "java -jar coordinator.jar"
gnome-terminal --working-directory=${PWD}/ant/jar/motor --command "java -jar motor.jar"
gnome-terminal --working-directory=${PWD}/ant/jar/mapper --command "java -jar mapper.jar"
gnome-terminal --working-directory=${PWD}/ant/jar/planner --command "java -jar planner.jar"
gnome-terminal --working-directory=${PWD}/ant/jar/reflex --command "java -jar reflex.jar"
gnome-terminal --working-directory=${PWD}/ant/jar/sensor --command "java -jar sensor.jar"
