String label = "worker-${UUID.randomUUID().toString()}"

podTemplate(label: label,
        cloud: "openshift",
        containers: [
                containerTemplate(
                        name: "dotnetcore",
                        image: "image-registry.openshift-image-registry.svc:5000/devopsfusionleh/dotnet-jenkins-slave",
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
    node(label) {
        stage("build") {
            echo 'dotnet build'
        }

        stage("test") {
            echo 'dotnet test'
        }
    }
}