# curso-kubernetes
 
# Install app Docker Usuarios 
- mvn clean install -DskipTests=true
- Dar permisos  (chmod +x mvnw)

# Usuarios
- docker build -t usuarios . 
- docker build -t usuarios:v1 . -f ./msvc-usuarios/Dockerfile
- docker images
- docker run -p 8001:8001 --name msvc-usuarios usuarios:v1 (attachado, en terminal se ve ejecucion, añade nombre a container)
- docker run -p 8001:8001 --env-file ./msvc-usuarios/.env -d --rm --name msvc-usuarios --network spring usuarios:v1 (con network y env file)
- docker run -d -p 8001:8001 usuarios:v1 (detach, en terminal no se ve ejecucion)
- docker run -d -p 8001:8001 --rm usuarios (detach, elimina contenedor al detenerlo)
- docker run -p 8001:8001 --rm -it usuarios /bin/sh (detach, elimina contenedor al detenerlo, interactivo)
- docker buildx build --platform linux/amd64 -t chris0100eka/usuarios . -f ./msvc-usuarios/Dockerfile (crear nueva imagen en docker hub)

- docker ps
- docker stop {container_id}


# Cursos
- docker build -t cursos:v1 . -f ./msvc-cursos/Dockerfile
- docker run -p 8002:8002 --env-file ./msvc-cursos/.env -d --rm --name msvc-cursos --network spring cursos:v1 (con network)
- docker buildx build --platform linux/amd64 -t chris0100eka/cursos . -f ./msvc-cursos/Dockerfile (crear nueva imagen en docker hub)

# Gateway
- docker buildx build --platform linux/amd64 -t chris0100eka/gateway . -f ./msvc-gateway/Dockerfile (crear nueva imagen en docker hub)


# Commands
- docker start -a usuarios:v1 (iniciar un contenedor detach, en un segundo plano, -a queda atachado)
- docker stop usuarios (detener contenedor)
- docker -f logs usuarios (con -f queda atachado)
- docker rm usuarios (elimina contenedores)
- docker container prune (elimina los contenedores con status exited)
- docker rmi usuarios (elimina imagenes)
- docker network create spring (crear network)
- docker pull mysql:8 (descargar imagen)
- docker volume ls (listar volumes)
- docker container inspect msvc-usuarios (inspeccionar contenedor)
- docker-compose up -d (crear y levantar containers)
- docker-compose down (parar y eliminar containers)
- docker-compose down -v (eliminar volumes tambien)
- docker-compose up --build -d (forzar a construir y levantar)
- docker buildx build --platform linux/amd64 -t chris0100eka/usuarios . -f ./msvc-usuarios/Dockerfile (crear nueva imagen en docker hub)

# Docker Hub
- docker tag chris0100eka/usuarios chris0100eka/usuarios:v3 (clona la imagen actual)
- docker push chris0100eka/usuarios (subir cambios imagen)
- docker pull chris0100eka/usuarios (descargar imagen)

- docker tag chris0100eka/cursos chris0100eka/cursos:v3
- docker push chris0100eka/cursos (subir cambios)
- docker pull chris0100eka/cursos (subir cambios)

- docker tag chris0100eka/gateway chris0100eka/gateway:v1
- docker push chris0100eka/gateway:v1 (subir cambios)



# SQL
- docker run -d -p 3307:3306 --name mysql8 --network spring -e MYSQL_ROOT_PASSWORD=fence0gras -e MYSQL_DATABASE=msvc_usuarios -v data-mysql:/var/lib/mysql --restart=always mysql:8
- docker run -it --rm --network spring mysql:8 bash (conectarse al bash mysql)
  - mysql -hmysql8 -uroot -p

# POSTGRES
- docker run -p 5532:5432 --name postgres14 --network spring -e POSTGRES_PASSWORD=fence0gras -e POSTGRES_DB=msvc_cursos -d -v data-postgres:/var/lib/postgresql/data --restart=always postgres:14-alpine
- docker run -it --rm --network spring postgres:14-alpine psql -h postgres14 -U postgres



# KUBERNETES
- minikube status
- minikube start
- kubectl get pods
- kubectl logs mysql8-568769c8c-qkbg9
- kubectl get deployments(deploy)
- kubectl delete deployment mysql8
- kubectl create deployment mysql8 --image=mysql:8 --port=3306 --dry-run=client -o yaml > deployment-mysql.yaml
- kubectl apply -f ./deployment-mysql.yaml (aplicar archivo para subir )
- kubectl expose deployment mysql8 --port=3306 --type=ClusterIP
- kubectl get services
- kubectl describe service mysql8
- kubectl get all
- kubectl create deployment msvc-usuarios --image=chris0100eka/usuarios:latest --port=8001
- kubectl expose deployment msvc-usuarios --port=8001 --type=LoadBalancer
- minikube service msvc-usuarios --url
- kubectl set image deployment msvc-usuarios usuarios=chris0100eka/usuarios:v2 (cambiar version de imagen a usar)
- kubectl scale deployment msvc-usuarios --replicas=3 (scale crea 3 instancias replicas)
- kubectl get service mysql8 -o yaml > svc-mysql.yaml (crear yaml de config)
- kubectl create deployment msvc-usuarios --port=8001 --image=chris0100eka/usuarios:v3 --dry-run=client -o yaml > deployment-usuarios.yaml (create deployment file)
- kubectl get service msvc-usuarios -o yaml > svc-usuarios.yaml
- kubectl delete -f ./deployment-usuarios.yaml (eliminar deployment)
- minikube dashboard
- kubectl apply -f ./deployment-postgres.yaml -f ./svc-postgres.yaml
- kubectl apply -f ./deployment-cursos.yaml -f ./svc-cursos.yaml
- kubectl apply -f ./deployment-usuarios.yaml -f ./svc-usuarios.yaml
- minikube service msvc-cursos --url
- kubectl apply -f ./mysql-pv.yaml
- kubectl apply -f ./mysql-pvc.yaml
- kubectl apply -f ./deployment-mysql.yaml
- kubectl apply -f ./postgres-pv.yaml
- kubectl apply -f ./postgres-pvc.yaml
- kubectl apply -f ./deployment-postgres.yaml
- kubectl get pv
- kubectl get pvc
- kubectl apply -f ./configmap.yaml
- kubectl get configmap
- kubectl create clusterrolebinding admin --clusterrole=cluster-admin --serviceaccount=default:default  (used for clud kubernetes)
- kubectl apply -f ./gateway.yaml












