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
        stage("checkout") {
          sh 'printenv'
          git(${GIT_REPO}, ${GIT_BRANCH})
        }

        stage("build") {
            echo 'dotnet build'
        }

        stage("test") {
            echo 'dotnet test'
        }
    }
}