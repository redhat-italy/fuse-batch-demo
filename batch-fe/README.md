# Frontend Example


```
oc new-build --image-stream=openshift/httpd:latest --name=batch-fe --binary=true
oc start-build batch-fe --from-dir=www
oc new-app batch-fe
```

```
oc expose svc/batch-fe
```