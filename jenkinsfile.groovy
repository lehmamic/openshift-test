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
          git([
              url:"${GIT_REPO}",
              branch:"${GIT_BRANCH}"
          ]) 
        }

        stage("restore") {
            sh 'dotnet restore src/Zuehlke.OpenShiftDemo.sln'
        }

        stage("build") {
            sh 'dotnet build src/Zuehlke.OpenShiftDemo.sln -c Release --no-restore'
        }

        stage("publich") {
            sh 'dotnet publish src/Zuehlke.OpenShiftDemo/Zuehlke.OpenShiftDemo.csproj -c Release -o ./app/publish --no-restore --no-build'
        }

        stage("test") {
            echo 'dotnet test'
        }
    }
}