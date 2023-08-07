#
# PowerShell script for running podman with compose commands:
#
# `build` - build images
# `up`    - create/start pod and containers
# `down`  - remove pod and containers
#

param(
    [Parameter(Mandatory)]
    [string]$ComposeAction
)

$podname = "modexp"

#Build spring and db containers
if ($ComposeAction.Equals("build")) {
    Write-Output "Building container: modexp-spring"
    podman build -t modexp-spring ./modexp

    Write-Output "Building container: db"
    podman build -t modexp-db ./postgres
}

# create pod, expose ports. start and stop pod with podman pod start/stop modexp
# 9876 -> PGAdmin portal
# 5432 -> for connecting local running app to pod db
# 8080 -> port for pod running app
if ($ComposeAction.Equals("up")) {
    Write-Output "Creating pod: $podname"
    podman pod create --name $podname -p 9876:80  -p 5432:5432 -p 8080:8081

    #Run PGAdmin Container
    Write-Output "Run container and add to pod: modexp-pgadmin"
    podman run --pod $podname `
    -e PGADMIN_DEFAULT_EMAIL='admin@example.gov' `
    -e PGADMIN_DEFAULT_PASSWORD='adminpass'  `
    --name modexp-pgadmin `
    -d docker.io/dpage/pgadmin4:latest

    #Run built db container
    Write-Output "Run container and add to pod: modexp-db"
    podman run --name modexp-db --pod=$podname -d `
    -e POSTGRES_USER='admin' `
    -e POSTGRES_PASSWORD='adminpass' `
    localhost/modexp-db:latest

    #Run built prototype container
    Write-Output "Run container and add to pod: modexp-spring"
    podman run --name modexp-spring --pod=$podname -d localhost/modexp-spring:latest
}

#Shutdown pod and containers, prune
if ($ComposeAction.Equals("down")) {
    Write-Output "Stopping pod: $podname"
    podman pod stop $podname

    Write-Output "Pruning Pod and Containers"
    podman pod rm $podname
}