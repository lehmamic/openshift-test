# Workshop Steps

## First Setup of the Jenkins Pipeline

### Create public git repo

Create a public git repository, e.g. on github. It also work with private repositories, but for simplification we use a public one.

### Create a simple Jeninsfile

Create a file `jenkinsfile.groovie`.

```Groovy
node {
    stage("build") {
        echo 'dotnet build'
    }

    stage("test") {
        echo 'dotnet test'
    }
}
```

### Create a Jenkins Pipeline Build Configuration

Create a file `openshift/pipeline/pipeline.buildconfig.yml``

```yml
kind: "BuildConfig"
apiVersion: "v1"
metadata:
  name: "devops-fusion-sample-pipeline"
spec:
  source:
    git:
      uri: "https://github.com/lehmamic/openshift-test"
  strategy:
    jenkinsPipelineStrategy:
      jenkinsfilePath: jenkinsfile.groovy
    type: JenkinsPipeline
```

### Create the Jenkins Pipeline Build Config Object in OpenShift

```bash
oc create -f openshift/pipeline/pipeline.buildconfig.yml
```

### Start a build

```bash
oc start-build devops-fusion-sample-pipeline
```

## Setup Pod Template

The Kubernetes Jenkins Plugin DSL allows us to define a pod template in the jenkins file.

```Groovy
podTemplate(label: "dotnet-31",
                    cloud: "openshift",
                    inheritFrom: "maven",
                    containers: [
            containerTemplate(name: "jnlp",
                              image: "registry.redhat.io/dotnet/dotnet-31-jenkins-agent-rhel7:latest",
                              resourceRequestMemory: "512Mi",
                              resourceLimitMemory: "512Mi",
                              resourceRequestCpu: "500m",
                              resourceLimitCpu: "2",
                              ttyEnabled: true,
                              envVars: [
              envVar(key: "CONTAINER_HEAP_PERCENT", value: "0.25")
            ])
          ]) {
    node("dotnet-31") {
        stage("build") {
            echo 'dotnet build'
        }

        stage("test") {
            echo 'dotnet test'
        }
    }
}
```
