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

    state("test") {
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
