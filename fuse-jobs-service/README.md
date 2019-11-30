#Build

```mvn clean package -DskipTests```

You need the Red Hat repository in your m2


#Run locally

```mvn spring-boot:run```

#Setup OCP

You need to be logged in the cluster with ```oc login```

##Binary deploy job

If you have it delete all old stuff:

```
oc delete bc/fuse-jobs-service dc/fuse-jobs-service svc/fuse-jobs-service route/fuse-jobs-service
```

```
oc new-build --image-stream=openshift/fuse7-java-openshift:1.3 --name=fuse-jobs-service --binary=true
oc start-build fuse-jobs-service --from-file=target/fuse-jobs-service-1.0.jar
```

#Create the app

```oc new-app fuse-jobs-service -e MYSQL_SERVICE_NAME='mysql' -e MYSQL_SERVICE_USERNAME='mysql' -e MYSQL_SERVICE_PASSWORD='mysql' -e MYSQL_SERVICE_DATABASE=integrationdb```

Expose the service

```oc expose 


#Create job
