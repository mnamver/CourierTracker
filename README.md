# CourierTracker
Tech Stack : 
Java-8 , Springboot, Maven,  Docker

How to run project ?

mvn clean package

docker build -f Dockerfile -t migroscouriertracker .

docker run -p 8080:8080 migroscouriertracker


how to publish courier location :

http://localhost:8080/courier/location

{
    "id" : 2523234243234234,
    "courierId" : 1,
    "latitude" : 40.9923307,
    "longitude" : 29.1254229,
    "time" : "2019-02-03T11:10:02"
}


how to see total distance : 

http://localhost:8080/courier/total-distance/13
