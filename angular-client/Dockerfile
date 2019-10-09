FROM node:10.9  as builder

RUN mkdir /usr/src/app
WORKDIR /usr/src/app
ENV PATH /usr/src/app/node_modules/.bin:$PATH
COPY package.json /usr/src/app/package.json
RUN npm install
RUN npm install -g @angular/cli@1.7.1 --unsafe
COPY . /usr/src/app

RUN ng build --configuration=production

FROM nginx:1.13.9-alpine
RUN rm -rf /usr/share/nginx/html/
COPY --from=builder /usr/src/app/dist/angular-client/ /usr/share/nginx/html/
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
