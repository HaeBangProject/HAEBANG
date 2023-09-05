#private ip
MY_IP=$(hostname -i)

NGINX_PORT='nginx port'
PORTS=('server_port' 'server_port' 'server_port')
SERVERS=("server_name" "server_name" "server_name")

echo "도커 이미지 pull"
docker pull haebang/haebang:jenkins

echo "배포 시작"
# old 서버 하나만 우선 중지
docker stop "${SERVERS[0]}" 2>/dev/null && docker rm "${SERVERS[0]}" 2>/dev/null

for cnt in {0..2}; do
    echo "$cnt 배포중..."
    docker run -d --name "${SERVERS[$cnt]}" -p "${PORTS[$cnt]}:$NGINX_PORT" haebang/haebang:jenkins

    echo "health check 연결"
    for retry_count in {1..10}; do
        response=$(sudo curl -s "http://$MY_IP:${PORTS[$cnt]}/actuator/health")
        up_count=$(echo "$response" | grep 'UP' | wc -l)
        if [ "$up_count" -ge 1 ]; then
            echo "> Health check 성공"
            if [ "$cnt" -eq "0" ]; then
                # old 서버 중지
                docker stop "${SERVERS[1]}" 2>/dev/null && docker rm "${SERVERS[1]}" 2>/dev/null
                docker stop "${SERVERS[2]}" 2>/dev/null && docker rm "${SERVERS[2]}" 2>/dev/null
                # 서버 down 확인
            elif [ "$cnt" -eq "2" ]; then
                echo "> 배포 완료"
                exit 0
            fi
            break
        else
            echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
            echo "> Health check: $response"
        fi

        if [ "$retry_count" -eq 10 ]; then
            echo "> Health check 실패."
            echo "> Nginx에 연결하지 않고 배포를 종료합니다."
            exit 1
        fi

        echo "> Health check 연결 실패. 재시도..."
        sudo sleep 10
    done
done