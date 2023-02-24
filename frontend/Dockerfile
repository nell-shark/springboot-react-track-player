FROM node:alpine

WORKDIR /frontend

COPY package.json packe-lock.json ./

RUN npm install --silent

COPY .  ./

CMD [ "npm", "start" ]
