# BE SWIM/ESB - PDL/SOA Project  
# INSA 2015-2016 - Group DD 

#### Github link : https://github.com/loicboyeldieu/SOA_ESBQualificationTool.git

## Introduction 

This project is a whole system for OpenESB qualification. To test our application (our main Standalone Java Application "ESBQualifictionToolkit"), you have to : 

 * Have access to Proxmox containers using [this VPN configuration files](https://drive.google.com/open?id=0BwTbFUDK0EzsOFVueUlzZDJ6Vmc). To be sure your configuration is working, you should have [access to Kibana](http://192.168.0.103:5601/).  
 * Have Java installed on your computer
 * Be on a unix sytem

## Launch the application 

You can choose to launch from Git repo or from Google Drive ````.jar````.

### Launch from Git

Execute this three line codes ! :+1:

```sh
git clone https://github.com/loicboyeldieu/SOA_ESBQualificationTool.git
cd SOA_ESBQualificationTool/ESBQualificationTool/test/
sudo java -jar ESBQualificationTool-1.0-SNAPSHOT-jar-with-dependencies.jar 
```

Note that the last command requires ```sudo``` because the application needs the rights to write the file result.  

### Launch from ```.jar```

Download and extract [the zip of the application on Google Drive](https://drive.google.com/open?id=0BwTbFUDK0EzsOFVueUlzZDJ6Vmc).

## Scenario 

Provide a scenario which respects [this schema](https://github.com/loicboyeldieu/SOA_ESBQualificationTool/blob/master/ESBQualificationToolJAXBHandler/src/com/esbqualificationtool/xmlHelper/ScenarioSchema.xsd).


[Here](https://drive.google.com/open?id=0BwTbFUDK0EzsOFVueUlzZDJ6Vmc) is an example of a scenario : feel free to change the different parameters, but be careful to respect the XSD, where you can find the different producers and consumers you can provide. 

