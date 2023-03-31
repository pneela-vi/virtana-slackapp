Steps to setup Slack App locally

* Clone the app from git (https://github.com/pneela-vi/virtana-slackapp)
* Install OpenJDK 19 using brew install (brew install openjdk@19)
* Install ngrok server (brew install ngrok/ngrok/ngrok)
* Install maven (brew install maven@3.5)
* Navigate to project directory
* Run mvn clean install
* mvn exec:java -Dexec.mainClass=“com.virtana.slackapp.SlackApp”
* Run ngrok http 3000
* Update Request_URL value of slash command with the public url provided by ngrok
