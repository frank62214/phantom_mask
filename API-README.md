## 專案Maven啟動參數
```
執行時請將mvn的工作路徑調整至demo執行，並選擇JDK17
spring-boot:run -Dspring-boot.run.arguments=--jasypt.encryptor.password=[填入] -f pom.xml -f pom.xml
```
* jasypt.encryptor.password 附在Email上

## API Doc
```
http://localhost:8082/swagger-ui/index.html
```

## API baseUrl
```
http://localhost:8082/
```

## Docker build
```
docker build -t phantom_mask:1.0 .
```

## Docker run
```
docker run -d --restart always --name phantom_mask -p 8082:8082 phantom_mask:1.0
```
* -d 背景執行
* --restart always 永遠自動重啟
* -p 介面接聽port與容器的port橋接
* image 的名稱
* 容器的名稱