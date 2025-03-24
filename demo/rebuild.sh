docker stop phantom_mask
docker rm phantom_mask

cd phantom_mask/demo && git pull && mvn clean package install -Djasypt.encryptor.password="另行提供" -f pom.xml && docker build -t phantom_mask:1.0 . && docker run -d --restart always --name phantom_mask -p 8082:8082 phantom_mask:1.0