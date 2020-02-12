
node("dotnet-31") {
    stage("build") {
        echo 'dotnet build'
    }

    stage("test") {
        echo 'dotnet test'
    }
}
