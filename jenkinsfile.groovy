node {
    stage("build") {
        echo 'dotnet build'
    }

    state("test") {
        echo 'dotnet test'
    }
}