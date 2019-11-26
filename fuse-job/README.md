# Build

```mvn clean package -DskipTests```

You need the Red Hat repository in your m2


# Run locally

```mvn spring-boot:run```

# Setup OCP

You need to be logged in the cluster with ```oc login```

## MySQL DB

oc delete svc/mysql  && oc delete dc/mysql  && oc delete configmap/mysql-initdb-config


## Binary deploy job

If you have it delete all old stuff:

```
oc delete bc/fuse-batch dc/fuse-batch svc/fuse-batch
```

```
oc new-build --image-stream=openshift/fuse7-java-openshift:1.4 --name=fuse-batch --binary=true
oc start-build fuse-batch --from-file=target/fuse-batch-1.0.jar
```

Create the service


# Create job
