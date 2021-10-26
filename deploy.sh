#!/Users/oncek/.ssh bash

rm -rf target/
echo "Deleted target/ folder"

mvn clean package -Dmaven.test.skip=true
echo "Generating jar file"

#Copy execute_commands_on_ec2.sh file which has commands to be executed on server... Here we are copying this file
# every time to automate this process through 'deploy.sh' so that whenever that file changes, it's taken care of
scp -i "/Users/onceknownriley/Documents/GreenWashProject/API/greenwash.pem" execute_commands_on_ec2.sh root@ec2-3-8-192-95.eu-west-2.compute.amazonaws.com:~
echo "Copied latest 'execute_commands_on_ec2.sh' file from local machine to ec2 instance"

scp -i "/Users/onceknownriley/Documents/GreenWashProject/API/greenwash.pem" target/greenwash-0.0.1-SNAPSHOT.jar root@ec2-3-8-192-95.eu-west-2.compute.amazonaws.com:~
echo "Copied jar file from local machine to ec2 instance"

echo "Connecting to ec2 instance and starting server using java -jar command"
ssh -i "/Users/onceknownriley/Documents/GreenWashProject/API/greenwash.pem" root@ec2-3-8-192-95.eu-west-2.compute.amazonaws.com ./execute_commands_on_ec2.sh