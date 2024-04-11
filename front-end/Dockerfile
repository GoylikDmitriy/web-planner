FROM node:18

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm install

EXPOSE 3000

COPY . .

CMD ["npm", "start"]