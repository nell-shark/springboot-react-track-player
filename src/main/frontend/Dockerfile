FROM node:alpine

WORKDIR /frontend

COPY package.json ./

RUN npm install --silent

COPY .  ./

CMD [ "npm", "start" ]
