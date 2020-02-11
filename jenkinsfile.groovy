String label = "worker-${UUID.randomUUID().toString()}"

podTemplate(label: label,
        cloud: "openshift",
        containers: [
                containerTemplate(
                        name: "dotnetcore",
                        image: "registry.access.redhat.com/dotnet/dotnet-31-jenkins-slaverhel7:latest",
                        alwaysPullImage: true,
                        resourceRequestMemory: "2Gi",
                        resourceRequestCpu: "500m",
                        resourceLimitMemory: "2Gi",
                        resourceLimitCpu: "1",
                        command: "cat",
                        ttyEnabled: true,
                        envVars: []),
        ],
        volumes: []
) {
    node("label") {
        stage("build") {
            echo 'dotnet build'
        }

        stage("test") {
            echo 'dotnet test'
        }
    }
}