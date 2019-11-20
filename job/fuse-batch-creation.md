# Prepare job image

oc new-build --image-stream=rtcm-sas/teradata_ubuntu_ttu:1.0.0 --name=bteq-job-s2i  --binary=true
oc start-build bteq-job-s2i --from-dir=bteq-s2i-job-source -n rtcm-sas --follow

# Creazione job Anagrafica

oc new-build --image-stream=rtcm-sas/bteq-job-s2i:latest --name=fuse-batch-job --binary=true -n rtcm-sas
oc start-build fuse-batch-job --from-dir=script -n rtcm-sas --follow

## Job from template (Anagrafica)

```
oc process -f fuse-batch-job-template.yaml -p JOB_SUFFIX=1 -p MYSQL_SERVICE_NAME=mysql -p MYSQL_SERVICE_USERNAME=mysql -p MYSQL_SERVICE_PASSWORD=mysql -p MYSQL_SERVICE_DATABASE=integrationdb -o yaml --local=true -n demo-batch-fuse|oc -n demo-batch-fuse create -f -
```

Il file:

```
apiVersion: v1
kind: Template
labels:
  application: fuse-batch-job
metadata:
  name: fuse-batch-job
objects:
- apiVersion: batch/v1
  kind: Job
  metadata:
    name: fuse-batch-job-${JOB_SUFFIX}
  spec:
    backoffLimit: 1
    parallelism: 1    
    completions: 1   
    template:         
      metadata:
        name: fuse-batch-job
      spec:
        containers:
        - env:
          - name: PARAMETER_ONE
            value: ${PARAMETER_ONE}
          - name: PARAMETER_TWO
            value: ${PARAMETER_TWO}
          name: fuse-batch-job
          image: docker-registry.default.svc:5000/demo-batch-fuse/fuse-batch
        restartPolicy: Never
parameters:
- description: Job suffix
  name: JOB_SUFFIX
  required: true
  value:
- description: Parameter one
  name: PARAMETER_ONE
  required: true
  value:  
- description: Parameter two
  name: PARAMETER_TWO
  required: true
  value:      
```

# Lancio del job

oc create -f job.yaml -n rtcm-sas

```
apiVersion: batch/v1
kind: Job
metadata:
  name: bteq-job-sample-1
spec:
  parallelism: 1    
  completions: 1    
  template:         
    metadata:
      name: bteq-job-sample-1
    spec:
      containers:
      - name: bteq-job-sample-1
        image: docker-registry.default.svc:5000/rtcm-sas/bteq-job
      restartPolicy: Never

```

Per controllare i job

```
oc get po -n rtcm-sas
```


# Creazione di una istanza per i Job (generica)

oc new-build --image-stream=rtcm-sas/bteq-job-s2i:latest --name=bteq-job --binary=true -n rtcm-sas
oc start-build bteq-job --from-dir=script -n rtcm-sas --follow



-------------
oc new-build --image-stream=rtcm-sas/teradata_ubuntu_ttu:1.0.0 --name=bteq-job-s2i  --binary=true
oc start-build bteq-job-s2i --from-dir=bteq-s2i-job-source
oc new-app bteq-s2i-job --strategy=docker --name bteq-job
oc start-build bteq-job --from-dir=bteq-s2i-job-source/