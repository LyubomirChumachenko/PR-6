# lab-6
Information systems development. 6 lab

## Предварительные требования
Убедитесь, что у вас установлен **PostgreSQL** - для работы с базой данных.
## Компиляция проекта
Перейдите в корневой каталог проекта

Подключитесь к PostgreSQL
```
psql -U your_username
```

Создайте базу данных:
```
CREATE DATABASE sg_db;
```

Перейдите в созданную базу данных
```
\c sg_db
```

Создайте пользователя и предоставьте ему доступ.
```
CREATE USER postgres WITH ENCRYPTED PASSWORD '0890';
GRANT ALL PRIVILEGES ON DATABASE sg_db TO postgres;
```


Запустите программу:
```
java -jar target/parfume-app-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:/application.properties
```
