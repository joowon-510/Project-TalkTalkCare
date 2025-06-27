# Step 1: Build 프론트엔드
FROM node:18 as build
WORKDIR /app

# 패키지 복사 및 설치치
COPY talktalkcare/package*.json ./
RUN npm ci

# 환경 변수 적용하여 .env.production 생성
ARG VITE_API_BASE_URL
ARG VITE_API_WS_URL
ARG VITE_AWS_ACCESS_KEY_ID
ARG VITE_AWS_SECRET_ACCESS_KEY
ARG VITE_AWS_REGION

ENV VITE_API_BASE_URL=$VITE_API_BASE_URL
ENV VITE_API_WS_URL=$VITE_API_WS_URL
ENV VITE_AWS_ACCESS_KEY_ID=$VITE_AWS_ACCESS_KEY_ID
ENV VITE_AWS_SECRET_ACCESS_KEY=$VITE_AWS_SECRET_ACCESS_KEY
ENV VITE_AWS_REGION=$VITE_AWS_REGION

# 애플리케이션 코드 복사 및 빌드
COPY talktalkcare/ ./

# 환경 변수 적용하여 Vite 빌드 실행
RUN echo "VITE_API_BASE_URL=$VITE_API_BASE_URL" > .env.production
RUN echo "VITE_API_WS_URL=$VITE_API_WS_URL" >> .env.production
RUN echo "VITE_AWS_ACCESS_KEY_ID=$VITE_AWS_ACCESS_KEY_ID" >> .env.production
RUN echo "VITE_AWS_SECRET_ACCESS_KEY=$VITE_AWS_SECRET_ACCESS_KEY" >> .env.production
RUN echo "VITE_AWS_REGION=$VITE_AWS_REGION" >> .env.production
RUN npm run build -- --mode production

# Step 2: Nginx에서 정적 파일 서빙
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
