# shellcheck disable=SC2164
cd ../../WorkerService
docker rm -f xyu
docker rmi infrostructure-backend
mvn clean install
cd ../infrostructure
docker compose up