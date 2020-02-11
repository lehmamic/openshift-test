node {
    stage("build") {
        sh 'dotnet build'
    }

    state("test") {
        sh 'dotnet test'
    }
}