# Getting Started

## Run

```mvn clean spring-boot:run```


# Setup OCP

You need to be logged in the cluster with ```oc login```

## MySQL DB


## Binary deploy app

If you have it delete all old stuff:

```
oc delete bc/file-browser-service dc/file-browser-service svc/file-browser-service
```

```
oc new-build --image-stream=openshift/java:latest --name=file-browser-service --binary=true
oc start-build file-browser-service --from-file=target/file-browser-service-1.0.jar
```

Create ```DesploymentConfig``` ```Service``` and ```Route``` 

```
oc create -f file-browser-service-objects.yaml
```

# Test the app

## get file list (if any file is on the path)

curl -X GET http://`oc get route/file-browser-service -o jsonpath='{.spec.host}'`/messagelist

  http://`oc get route/file-browser-service -o jsonpath='{.spec.host}'`/file-browser-service/products/placeorder

## Get order


## Get the list

```curl -X GET http://`oc get route/file-browser-service -o jsonpath='{.spec.host}'`/file-browser-service/products```

