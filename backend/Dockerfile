# ---------- [1단계: Build Stage] ----------
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

# 소스 복사
COPY . .
RUN chmod +x gradlew
RUN ./gradlew build -x test

# ---------- [2단계: Runtime Stage] ----------
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR만 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
