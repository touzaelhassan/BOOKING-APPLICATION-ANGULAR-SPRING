FROM node:16
WORKDIR /app
COPY package*.json ./
RUN npm install --force
COPY . .
RUN npm run build
EXPOSE 4200
CMD ["npm", "start"]
