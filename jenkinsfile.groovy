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

        stage("gitversion") {
            sh 'dotnet tool install --global GitVersion.Tool --version 5.1.3'
            sh 'dotnet-gitversion /output buildserver'
            sh 'cat gitversion.properties'
        }

        stage("restore") {
            sh 'dotnet restore src/Zuehlke.OpenShiftDemo.sln'
        }

        stage("build") {
            sh 'dotnet build src/Zuehlke.OpenShiftDemo.sln -c Release --no-restore'
        }

        stage("publish") {
            sh 'dotnet publish src/Zuehlke.OpenShiftDemo/Zuehlke.OpenShiftDemo.csproj -c Release -o ./app/publish --no-restore --no-build'
        }
    }
}