FROM node:20-alpine

WORKDIR /frontend

COPY package.json package-lock.json ./

RUN npm install --silent

COPY .  ./

CMD [ "npm", "start" ]
