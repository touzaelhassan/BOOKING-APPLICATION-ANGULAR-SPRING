FROM node:alpine as build
RUN mkdir -p /app
WORKDIR /app
COPY package.json /app/
RUN npm install --force
COPY . /app/
RUN npm run build --prod
FROM nginx:alpine
COPY --from=build /app/dist/frontend-application /usr/share/nginx/html

