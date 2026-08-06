# Book Management

## 技術スタック
- Kotlin
- Spring Boot
- jOOQ
- PostgreSQL
- Flyway

## 起動方法
./gradlew bootRun

## API
POST /authors
PUT /authors/{id}
GET /authors/{id}

POST /books
PUT /books/{id}
GET /books/{id}
GET /books?authorId=1

## ビジネスルール
・書籍には最低1人の著者が必要
・価格は0以上
・出版済み→未出版への変更は禁止
・著者の生年月日は未来日不可
