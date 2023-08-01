<div align="center">

# Track Player

Audio streaming service

[Installation](#installation) •
[Built With](#built-with) •
[License](#license)


![screenshot](.github/thumbnail.png)

</div>

## Installation

1. Install **[Docker](https://www.docker.com/)**
2. Clone this repository
    ```bash
    git clone https://github.com/nell-shark/spring-boot-react-track-player
    ```
3. Set your environment variables in ```docker-compose.yml```:
   ```yaml
   environment:
      - YOUR_GITHUB_CLIENT_ID=
      - YOUR_GITHUB_CLIENT_SECRET=
      - YOUR_AWS_S3_BUCKETS_TRACKS=
      - YOUR_AWS_CREDENTIALS_ACCESS_KEY=
      - YOUR_AWS_CREDENTIALS_SECRET_KEY=
    ```
4. Run all containers with:
   ```bash
    docker-compose up
    ```
5. Go to **[localhost:3000](http://localhost:3000)**

## Built With

<p align="center">
  <a href="https://www.docker.com/">
    <img src="https://skillicons.dev/icons?i=docker"  alt='docker'/>
  </a>
  <a href="https://spring.io/">
    <img src="https://skillicons.dev/icons?i=spring"  alt='spring'/>
  </a>
  <a href="https://aws.amazon.com/">
    <img src="https://skillicons.dev/icons?i=aws"  alt='aws'/>
  </a>
  <a href="https://getbootstrap.com/">
    <img src="https://skillicons.dev/icons?i=bootstrap"  alt='bootstrap'/>
  </a>
  <a href="https://java.com/">
    <img src="https://skillicons.dev/icons?i=java"  alt='java'/>
  </a>
  <a href="https://www.mysql.com/">
    <img src="https://skillicons.dev/icons?i=mysql"  alt='mysql'/>
  </a>
  <a href="https://react.dev/">
    <img src="https://skillicons.dev/icons?i=react"  alt='react'/>
  </a>
  <a href="https://www.mysql.com/">
    <img src="https://skillicons.dev/icons?i=mysql"  alt='mysql'/>
  </a>
  <a href="https://redux.js.org/">
    <img src="https://skillicons.dev/icons?i=redux"  alt='redux'/>
  </a>
  <a href="https://www.typescriptlang.org/">
    <img src="https://skillicons.dev/icons?i=ts"  alt='ts'/>
  </a>
  <a href="https://gradle.org/">
    <img src="https://skillicons.dev/icons?i=gradle"  alt='gradle'/>
  </a>
  <a href="https://github.com/nell-shark/spring-boot-react-track-player/actions">
    <img src="https://skillicons.dev/icons?i=githubactions"  alt='githubactions'/>
  </a>
</p>

## License

Code released under the [MIT](https://choosealicense.com/licenses/mit/) license

