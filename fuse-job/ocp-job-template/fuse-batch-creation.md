# Job image

## Job from template

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
          - name: MYSQL_SERVICE_DATABASE
            value: ${MYSQL_SERVICE_DATABASE}
          - name: MYSQL_SERVICE_NAME
            value: ${MYSQL_SERVICE_NAME}
          - name: MYSQL_SERVICE_PASSWORD
            value: ${MYSQL_SERVICE_PASSWORD}
          - name: MYSQL_SERVICE_USERNAME
            value: ${MYSQL_SERVICE_USERNAME}
          volumeMounts:
          - mountPath: /job/storage
            name: volume-i7sso
          name: fuse-batch-job
          image: docker-registry.default.svc:5000/demo-batch-fuse/fuse-batch
        volumes:
        - name: volume-i7sso
          persistentVolumeClaim:
            claimName: job-storage
        restartPolicy: Never
parameters:
- description: Job suffix
  name: JOB_SUFFIX
  required: true
  value:
- description: Mysql database
  name: MYSQL_SERVICE_DATABASE
  required: true
  value:
- description: Mysql service
  name: MYSQL_SERVICE_NAME
  required: true
  value:
- description: Password
  name: MYSQL_SERVICE_PASSWORD
  required: true
- description: User
  name: MYSQL_SERVICE_USERNAME
  required: true
  value:
